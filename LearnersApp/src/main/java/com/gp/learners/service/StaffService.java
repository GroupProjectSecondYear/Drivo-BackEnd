package com.gp.learners.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Admin;
import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.LeaveSetting;
import com.gp.learners.entities.Salary;
import com.gp.learners.entities.SalaryInformation;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.WorkTime;
import com.gp.learners.entities.mapObject.StaffWorkDaysDataMap;
import com.gp.learners.repositories.AdminRepository;
import com.gp.learners.repositories.AttendanceRepository;
import com.gp.learners.repositories.LeaveSettingRepository;
import com.gp.learners.repositories.StaffLeaveRepository;
import com.gp.learners.repositories.SalaryInformationRepository;
import com.gp.learners.repositories.SalaryRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.repositories.WorkTimeRepository;

import ch.qos.logback.core.util.Duration;

@Service
public class StaffService {
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	@Autowired 
	AttendanceRepository attendanceRepository;
	
	@Autowired
	WorkTimeRepository workTimeRepository;
	
	@Autowired
	TimeTableService timeTableService;
	
	@Autowired
	SalaryInformationRepository salaryInformationRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	StaffLeaveRepository staffLeaveRepository;
	
	@Autowired
	SalaryRepository salaryRepository;
	
	@Autowired
	LeaveSettingRepository leaveSettingRepository;
	
	
	//Get Staff Details
//	public Object getStaff() {
//		return userRepository.findUserIdStaffIdNameRoleStatusByRoleNot(5);
//	}
	
	public Integer markStaffAttendance(Integer staffId) {
		if(staffRepository.existsById(staffId)) {
			Staff staffMemeber = staffRepository.findByStaffId(staffId);
			LocalDate currentDate = timeTableService.getLocalCurrentDate();
			LocalTime currentTime = timeTableService.getLocalCurrentTime();
				
			Attendance object = attendanceRepository.findByStaffId(staffId,currentDate);
			if(object != null) {
				if(object.getLeaveTime() == null) {//Only 2 Finger print per day.otherwise return 0
					//Long timeGap = ChronoUnit.HOURS.between(object.getFrom(),currentTime);
					
					object.setLeaveTime(currentTime);
					attendanceRepository.save(object);
					return 1;//success
					
				}else {
					return 0;
				}
			}else {
				Attendance attendance = new Attendance();
				attendance.setDate(currentDate);
				attendance.setComeTime(currentTime);
				attendance.setStaffId(staffMemeber);
				
				attendanceRepository.save(attendance);
				
				return 1;
			}
		}
		return null;
	}
	
	public List<SalaryInformation> getStaffSalaryInforamtion(){
		return salaryInformationRepository.findAll();
	}
	
	public SalaryInformation getStaffSalaryInformation(Integer salaryInformationId) {
		if(salaryInformationRepository.existsById(salaryInformationId)) {
			return salaryInformationRepository.findBySalaryInformationId(salaryInformationId);
		}
		return null;
	}
	
	public String addStaffSalaryInformation(SalaryInformation salaryInformation) {
		
		if(salaryInformation!=null) {
			Integer userId= salaryInformation.getAdminId().getUserId().getUserId();
			if(userId!=null) {
				Admin admin = adminRepository.findByUserId(userRepository.findByUserId(userId));
				if(admin!=null) {
					salaryInformation.setAdminId(admin);
					
					//isNotExist SalaryInformation 
					SalaryInformation object = salaryInformationRepository.findByStaffType(salaryInformation.getStaffType());
					if(object==null) {
						salaryInformationRepository.save(salaryInformation);
						return "success";
					}
				}
			}
		}
		return null;
	}
	
	public String updateStaffSalaryInformation(SalaryInformation salaryInformation) {
		if(salaryInformation!=null && salaryInformation.getSalaryInformationId()!=null && salaryInformationRepository.existsById(salaryInformation.getSalaryInformationId())) {
			salaryInformationRepository.save(salaryInformation);
			return "success";
		}
		return null;
	}
	
	public String deleteStaffSalaryInformation(Integer salaryInformationId) {
		if(salaryInformationRepository.existsById(salaryInformationId)) {
			SalaryInformation object = salaryInformationRepository.findBySalaryInformationId(salaryInformationId);
			salaryInformationRepository.delete(object);
			return "success";
		}
		return null;
	}
	
	public WorkTime getStaffWorkTime() {
		List<WorkTime> workTimeList = workTimeRepository.findAll();
		if(workTimeList!=null && workTimeList.size()>0) {
			return workTimeList.get(0);
		}
		return null;
	}
	
	public String updateStaffWorkTime(Integer fullDay,Integer halfDay) {
		if(fullDay!=null && halfDay!=null) {
		    WorkTime object = workTimeRepository.findByWorkTimeId(1);
		    object.setFullDay(fullDay);
		    object.setHalfDay(halfDay);
		    
		    workTimeRepository.save(object);
			return "success";
		}
		return null;
	}
	
	
	////////////////////Staff Salary//////////////////////////////////
	/*
	 * month index [1-12]-->[Jan,Dec]
	 */
	public List<Salary> getStaffSalaryList(Integer month){
		List<Salary> salaryList = new ArrayList<Salary>();
		salaryList = salaryRepository.findSalaryListByMonth(month);
		
		if(month>0 && month<13) {
			if(salaryList==null || salaryList.size()==0) {//calculate staff salary(Insert)
				Boolean complete = setStaffSalary(month,1);
				if(complete) {
					salaryList = salaryRepository.findSalaryListByMonth(month);
				}
			}else {//update staff salary
				Boolean complete = setStaffSalary(month,2);
				if(complete) {
					salaryList = salaryRepository.findSalaryListByMonth(month);
				}
			}
		}
		return salaryList;
	}
	
	/*
	 * type 1-->Add ,2-->Update
	 */
	public Boolean setStaffSalary(Integer month,Integer type){
		List<WorkTime> workTimeList = workTimeRepository.findAll();
		if(workTimeList!=null) {
			WorkTime workTime = workTimeList.get(0);
			for(int i =2 ; i<5 ; i++) {
				List<Staff> staffList = staffRepository.findStaffListByRole(i);
				if(staffList!=null) {
					SalaryInformation salaryInformation = salaryInformationRepository.findByStaffType(i);
					calaculateSalary(staffList, salaryInformation, month, workTime,type);
				}
			}
			return true;
		}
		return false;
	}
	
	public void calaculateSalary(List<Staff> staffList,SalaryInformation salaryInformation,Integer month,WorkTime workTime,Integer type){
		for (Staff staff : staffList) {
			
			List<LeaveSetting> recommendedLeaveList = leaveSettingRepository.findAll();
			Integer recommendedLeave=0;//recommended leave for year
			if(recommendedLeave!=null && recommendedLeaveList.size()>0) {
				recommendedLeave = recommendedLeaveList.get(0).getNumLeave();
			}
			
			
			Integer totalNumLeaves = staffLeaveRepository.findByStaffId(staff);//total leave calculate including this month leaves
			Integer numLeaves = staffLeaveRepository.findByStaffIdAndMonth(staff,month);//get number of leave in this month
			Integer numFullDays = attendanceRepository.findFullDaysByStaffId(workTime.getFullDay(), staff, month);
			Integer numHalfDays = attendanceRepository.findHalfDaysByStaffId(workTime.getHalfDay(),workTime.getFullDay(),staff, month);
			
			Double totalPayment = numFullDays*salaryInformation.getFullDaySalary() + numHalfDays*salaryInformation.getHalfDaySalary();
			Integer noPayDays = 0;
			Double nopay=0D;
			
			//calculate leave and nopay payemnts
			if(totalNumLeaves>recommendedLeave) {//calculate nopay
				Integer diffLeave = totalNumLeaves-recommendedLeave;
				if(diffLeave >= numLeaves) {
					noPayDays=numLeaves;
				}else {
					noPayDays=diffLeave;
					totalPayment+=(numLeaves-diffLeave)*salaryInformation.getFullDaySalary();
				}
				nopay = noPayDays*salaryInformation.getNopay();
			}else {
				totalPayment +=numLeaves*salaryInformation.getFullDaySalary(); 
			}
			
			
			Salary object = new Salary();
			object.setMonth(month);
			object.setTotalPayment(totalPayment);
			object.setNopay(nopay);
			object.setPayed(0d);
			object.setComplete(0);
			object.setStaffId(staff);
			
			if(type==1) {//insert
				salaryRepository.save(object);
			}else {//update
				Salary salaryExistObject = salaryRepository.findByStaffIdAndMonth(staff,month);
				salaryExistObject.setTotalPayment(totalPayment);
				salaryExistObject.setNopay(nopay);
				salaryRepository.save(salaryExistObject);
			}
				
		}
	}
	
	public Salary getStaffSalaryData(Integer staffId,Integer month) {
		if(staffRepository.existsById(staffId) && month>0 && month<13) {
			Staff staff = staffRepository.findByStaffId(staffId);
			return salaryRepository.findByStaffIdAndMonth(staff, month);
		}
		return null;
	}
	
	public String payStaffSalary(Salary staffSalary) {
		if(staffSalary!=null && staffRepository.existsById(staffSalary.getStaffId().getStaffId()) ) {
			Double netSalary = staffSalary.getTotalPayment()-staffSalary.getNopay()-staffSalary.getPayed();
			if(netSalary==0) {
				staffSalary.setComplete(1);
				salaryRepository.save(staffSalary);
				return "success";
			}else if(netSalary>0) {
				salaryRepository.save(staffSalary);
				return "success";
			}else {
				return null;
			}
		}
		return null;
	}
	////////////////////Staff Salary Finish//////////////////////////////////
	
	public StaffWorkDaysDataMap getStaffWorkDays(Integer staffId,Integer month) {
		if(staffRepository.existsById(staffId)) {
			Staff staff = staffRepository.findByStaffId(staffId);	
			List<WorkTime> workTimeList = workTimeRepository.findAll();
			if(workTimeList!=null) {
				WorkTime workTime = workTimeList.get(0);
				
				//calculate noPayDays and Leave Days
				List<LeaveSetting> recommendedLeaveList = leaveSettingRepository.findAll();
				Integer recommendedLeave=0;//recommended leave for year
				if(recommendedLeave!=null && recommendedLeaveList.size()>0) {
					recommendedLeave = recommendedLeaveList.get(0).getNumLeave();
				}
				Integer totalNumLeaves = staffLeaveRepository.findByStaffId(staff);//total leave calculate including this month leaves
				Integer numLeaves = staffLeaveRepository.findByStaffIdAndMonth(staff,month);//get number of leave in this month
				Integer noPayDays=0;
				if(totalNumLeaves>recommendedLeave) {//calculate nopayDays
					Integer diffLeave = totalNumLeaves-recommendedLeave;
					if(diffLeave >= numLeaves) {
						noPayDays=numLeaves;
						numLeaves=0;
					}else {
						noPayDays=diffLeave;
						numLeaves=numLeaves-diffLeave;
					}
				}
				
				Integer numFullDays = attendanceRepository.findFullDaysByStaffId(workTime.getFullDay(), staff, month);
				Integer numHalfDays = attendanceRepository.findHalfDaysByStaffId(workTime.getHalfDay(),workTime.getFullDay(),staff, month);
				Integer notCompleteDays = attendanceRepository.findNotCompleteDaysByStaffId(workTime.getHalfDay(),staff,month);
				
				StaffWorkDaysDataMap object = new StaffWorkDaysDataMap();
				object.setFullDays(numFullDays);
				object.setHalfDays(numHalfDays);
				object.setLeaveDays(numLeaves);
				object.setNotCompleteDays(notCompleteDays);
				object.setNoPayDays(noPayDays);
				
				return object;
			}
			
		}
		return null;
	}
	
	public SalaryInformation getStaffRoleSalaryInformation(Integer staffId) {
		if(staffId!=null && staffRepository.existsById(staffId)) {
			Staff staff = staffRepository.findByStaffId(staffId);
			SalaryInformation salaryInformation = salaryInformationRepository.findByStaffType(staff.getUserId().getRole());
			return salaryInformation;
		}
		return null;
	}
	
	public Staff getStaffData(Integer userId) {
		if(userId!=null && userRepository.existsById(userId)) {
			Staff staff = staffRepository.findByUserId(userId);
			return staff;
		}
		return null;
	}
	
	public List<Attendance> getStaffAttendance(Integer staffId,Integer month){
		if(staffId!=null && staffRepository.existsById(staffId) && month!=null) {
			Staff staff = staffRepository.findByStaffId(staffId);
			List<Attendance> attendanceList = attendanceRepository.findByStaffIdAndMonth(staff, month);
			return attendanceList;
		}
		return null;
	}
	
}

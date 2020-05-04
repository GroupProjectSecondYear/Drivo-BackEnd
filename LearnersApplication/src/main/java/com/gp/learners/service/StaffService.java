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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gp.learners.config.security.JwtInMemoryUserDetailsService;
import com.gp.learners.entities.Admin;
import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.LeaveSetting;
import com.gp.learners.entities.Salary;
import com.gp.learners.entities.SalaryInformation;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.StaffLeave;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.User;
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

	@Autowired
	UserService userService;

	@Autowired
	StaffService staffService;

	@Autowired
	JwtInMemoryUserDetailsService jwtInMemoryUserDetailsService;

	// Get Staff Details
//	public Object getStaff() {
//		return userRepository.findUserIdStaffIdNameRoleStatusByRoleNot(5);
//	}

	public Integer markStaffAttendance(Integer staffId) {
		if (staffRepository.existsById(staffId)) {
			Staff staffMemeber = staffRepository.findByStaffId(staffId);
			LocalDate currentDate = timeTableService.getLocalCurrentDate();
			LocalTime currentTime = timeTableService.getLocalCurrentTime();

			Attendance object = attendanceRepository.findByStaffId(staffId, currentDate);
			if (object != null) {
				if (object.getLeaveTime() == null) {// Only 2 Finger print per day.otherwise return 0
					// Long timeGap = ChronoUnit.HOURS.between(object.getFrom(),currentTime);

					object.setLeaveTime(currentTime);
					attendanceRepository.save(object);
					return 1;// success

				} else {
					return 0;
				}
			} else {
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

	public List<SalaryInformation> getStaffSalaryInforamtion() {
		List<SalaryInformation> salaryInformationList = new ArrayList<SalaryInformation>();
		for (int i = 2; i <= 4; i++) {
			SalaryInformation object = salaryInformationRepository.findLastUpdateRecordByStaffType(i);
			if (object != null) {
				salaryInformationList.add(object);
			}
		}
		return salaryInformationList;
	}

	public SalaryInformation getStaffSalaryInformation(Integer salaryInformationId) {
		if (salaryInformationRepository.existsById(salaryInformationId)) {
			return salaryInformationRepository.findBySalaryInformationId(salaryInformationId);
		}
		return null;
	}

	// This method not use
//	public String addStaffSalaryInformation(SalaryInformation salaryInformation) {
//		
//		if(salaryInformation!=null) {
//			Integer userId= salaryInformation.getAdminId().getUserId().getUserId();
//			if(userId!=null) {
//				Admin admin = adminRepository.findByUserId(userRepository.findByUserId(userId));
//				if(admin!=null) {
//					salaryInformation.setAdminId(admin);
//					
//					//isNotExist SalaryInformation 
//					//SalaryInformation object = salaryInformationRepository.findByStaffType(salaryInformation.getStaffType());
//					if(object==null) {
//						salaryInformationRepository.save(salaryInformation);
//						return "success";
//					}
//				}
//			}
//		}
//		return null;
//	}

	public String updateStaffSalaryInformation(SalaryInformation salaryInformation) {
		SalaryInformation object = salaryInformationRepository.findByYearAndMonth(
				salaryInformation.getUpdateDate().getYear(), salaryInformation.getApplyMonth(),
				salaryInformation.getStaffType());
		if (object == null) {// There is no record in this year this month yet
			object = new SalaryInformation();
			object.setAdminId(salaryInformation.getAdminId());
			object.setApplyMonth(salaryInformation.getApplyMonth());
			object.setStaffType(salaryInformation.getStaffType());
		}
		object.setFullDaySalary(salaryInformation.getFullDaySalary());
		object.setHalfDaySalary(salaryInformation.getHalfDaySalary());
		object.setNopay(salaryInformation.getNopay());
		object.setUpdateDate(salaryInformation.getUpdateDate());

		salaryInformationRepository.save(object);

		return "success";
	}

	// This method not use
	public String deleteStaffSalaryInformation(Integer salaryInformationId) {
		if (salaryInformationRepository.existsById(salaryInformationId)) {
			SalaryInformation object = salaryInformationRepository.findBySalaryInformationId(salaryInformationId);
			salaryInformationRepository.delete(object);
			return "success";
		}
		return null;
	}

	public WorkTime getStaffWorkTime() {
		WorkTime workTime = workTimeRepository.findLastUpdateRecord();
		if (workTime != null) {
			return workTime;
		}
		return null;
	}

	public String updateStaffWorkTime(WorkTime workTime) {

		WorkTime object = workTimeRepository.findByYearAndMonth(workTime.getUpdateDate().getYear(),
				workTime.getApplyMonth());
		if (object == null) {
			object = new WorkTime();
			object.setAdminId(workTime.getAdminId());
			object.setApplyMonth(workTime.getApplyMonth());
		}
		object.setFullDay(workTime.getFullDay());
		object.setHalfDay(workTime.getHalfDay());
		object.setUpdateDate(workTime.getUpdateDate());

		workTimeRepository.save(object);

		return "success";

	}

	//////////////////// Staff Salary//////////////////////////////////
	/*
	 * month index [1-12]-->[Jan,Dec]
	 */
	public List<Salary> getStaffSalaryList(Integer month, Integer year) {
		List<Salary> salaryList = new ArrayList<Salary>();

		if (month > 0 && month < 13) {
			salaryList = salaryRepository.findSalaryListByMonth(month, year);
			if (salaryList == null || salaryList.size() == 0) {// calculate staff salary(Insert)
				Boolean complete = setStaffSalary(month, year, 1);
				if (complete) {
					salaryList = salaryRepository.findSalaryListByMonth(month, year);
				}
			} else {// update staff salary
				Boolean complete = setStaffSalary(month, year, 2);
				if (complete) {
					salaryList = salaryRepository.findSalaryListByMonth(month, year);
				}
			}
		}
		return salaryList;
	}

	/*
	 * type 1-->Add ,2-->Update
	 */
	public Boolean setStaffSalary(Integer month, Integer year, Integer type) {
		WorkTime workTime = workTimeRepository.findRelevantRecord(year, month);
		if (workTime != null) {
			for (int i = 2; i < 5; i++) {
				List<Staff> staffList = staffRepository.findStaffListByRole(i);
				if (staffList != null) {
					SalaryInformation salaryInformation = salaryInformationRepository.findRelevantRecord(year, month,
							i);
					calaculateSalary(staffList, salaryInformation, month, year, workTime, type);
				}
			}
			return true;
		}
		return false;
	}

	public void calaculateSalary(List<Staff> staffList, SalaryInformation salaryInformation, Integer month,
			Integer year, WorkTime workTime, Integer type) {

		for (Staff staff : staffList) {

			Integer recommendedLeave = leaveSettingRepository.findRelevantRecord(year, month);
			if (recommendedLeave == null) {
				recommendedLeave = 0;
			}

			Integer totalNumLeaves = staffLeaveRepository.findByStaffId(staff, year);// total leave calculate including
																						// this month leaves
			Integer numLeaves = staffLeaveRepository.findByStaffIdAndMonth(staff, month, year);// get number of leave in
																								// this month
			Integer numFullDays = attendanceRepository.findFullDaysByStaffId(workTime.getFullDay(), staff, month, year);
			Integer numHalfDays = attendanceRepository.findHalfDaysByStaffId(workTime.getHalfDay(),
					workTime.getFullDay(), staff, month, year);

			Double totalPayment = numFullDays * salaryInformation.getFullDaySalary()
					+ numHalfDays * salaryInformation.getHalfDaySalary();
			Integer noPayDays = 0;
			Double nopay = 0D;

			// calculate leave and nopay payemnts
			if (totalNumLeaves > recommendedLeave) {// calculate nopay
				Integer diffLeave = totalNumLeaves - recommendedLeave;
				if (diffLeave >= numLeaves) {
					noPayDays = numLeaves;
				} else {
					noPayDays = diffLeave;
					totalPayment += (numLeaves - diffLeave) * salaryInformation.getFullDaySalary();
				}
				nopay = noPayDays * salaryInformation.getNopay();
			} else {
				totalPayment += numLeaves * salaryInformation.getFullDaySalary();
			}

			Salary object = new Salary();
			object.setMonth(month);
			object.setYear(year);
			object.setTotalPayment(totalPayment);
			object.setNopay(nopay);
			object.setPayed(0d);
			object.setComplete(0);
			object.setStaffId(staff);

			if (type == 1) {// insert
				salaryRepository.save(object);
			} else {// update
				Salary salaryExistObject = salaryRepository.findByStaffIdAndMonth(staff, month, year);
				if (salaryExistObject != null) {
					salaryExistObject.setTotalPayment(totalPayment);
					salaryExistObject.setNopay(nopay);
					salaryRepository.save(salaryExistObject);
				} else {
					System.out.println("-----------------Salary Problem count line 309----------------------");
				}
			}

		}
	}

	public Salary getStaffSalaryData(Integer staffId, Integer month, Integer year) {
		if (staffRepository.existsById(staffId) && month > 0 && month < 13) {
			Staff staff = staffRepository.findByStaffId(staffId);
			return salaryRepository.findByStaffIdAndMonth(staff, month, year);
		}
		return null;
	}

	public String payStaffSalary(Salary staffSalary) {
		if (staffSalary != null && staffRepository.existsById(staffSalary.getStaffId().getStaffId())) {
			Double netSalary = staffSalary.getTotalPayment() - staffSalary.getNopay() - staffSalary.getPayed();
			if (netSalary == 0) {
				staffSalary.setComplete(1);
				staffSalary.setDate(timeTableService.getLocalCurrentDate());
				salaryRepository.save(staffSalary);
				return "success";
			} else if (netSalary > 0) {
				staffSalary.setDate(timeTableService.getLocalCurrentDate());
				salaryRepository.save(staffSalary);
				return "success";
			} else {
				return null;
			}
		}
		return null;
	}
	//////////////////// Staff Salary Finish//////////////////////////////////

	public StaffWorkDaysDataMap getStaffWorkDays(Integer staffId, Integer month, Integer year) {
		if (staffRepository.existsById(staffId)) {
			Staff staff = staffRepository.findByStaffId(staffId);
			WorkTime workTime = workTimeRepository.findRelevantRecord(year, month);
			if (workTime != null) {

				// calculate noPayDays and Leave Days
				Integer recommendedLeave = leaveSettingRepository.findRelevantRecord(year, month);
				if (recommendedLeave == null) {
					recommendedLeave = 0;
				}

				Integer totalNumLeaves = staffLeaveRepository.findByStaffId(staff, year);// total leave calculate
																							// including this month
																							// leaves
				Integer numLeaves = staffLeaveRepository.findByStaffIdAndMonth(staff, month, year);// get number of
																									// leave in this
																									// month
				Integer noPayDays = 0;
				if (totalNumLeaves > recommendedLeave) {// calculate nopayDays
					Integer diffLeave = totalNumLeaves - recommendedLeave;
					if (diffLeave >= numLeaves) {
						noPayDays = numLeaves;
						numLeaves = 0;
					} else {
						noPayDays = diffLeave;
						numLeaves = numLeaves - diffLeave;
					}
				}

				Integer numFullDays = attendanceRepository.findFullDaysByStaffId(workTime.getFullDay(), staff, month,
						year);
				Integer numHalfDays = attendanceRepository.findHalfDaysByStaffId(workTime.getHalfDay(),
						workTime.getFullDay(), staff, month, year);
				Integer notCompleteDays = attendanceRepository.findNotCompleteDaysByStaffId(workTime.getHalfDay(),
						staff, month, year);

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

	public SalaryInformation getStaffRoleSalaryInformation(Integer staffId, Integer year, Integer month) {
		if (staffId != null && staffRepository.existsById(staffId)) {
			Staff staff = staffRepository.findByStaffId(staffId);
			SalaryInformation salaryInformation = salaryInformationRepository.findRelevantRecord(year, month,
					staff.getUserId().getRole());
			return salaryInformation;
		}
		return null;
	}

	public Staff getStaffData(Integer userId) {
		if (userId != null && userRepository.existsById(userId)) {
			Staff staff = staffRepository.findByUserId(userId);
			return staff;
		}
		return null;
	}

	public List<Attendance> getStaffAttendance(Integer staffId, Integer month, Integer year) {
		if (staffId != null && staffRepository.existsById(staffId) && month != null && year != null) {
			Staff staff = staffRepository.findByStaffId(staffId);
			List<Attendance> attendanceList = attendanceRepository.findByStaffIdAndMonth(staff, month, year);
			return attendanceList;
		}
		return null;
	}

	public LeaveSetting getStaffLeave() {
		LeaveSetting leaveSettign = leaveSettingRepository.findLastRecord();
		if (leaveSettign != null) {
			return leaveSettign;
		}
		return null;
	}

	public Integer updateStaffLeave(LeaveSetting leaveSetting) {
		LeaveSetting object = leaveSettingRepository.findByYearAndMonth(leaveSetting.getUpdateDate().getYear(),
				leaveSetting.getApplyMonth());
		if (object == null) {
			object = new LeaveSetting();
			object.setAdminId(leaveSetting.getAdminId());
			object.setApplyMonth(leaveSetting.getApplyMonth());
		}
		object.setNumLeave(leaveSetting.getNumLeave());
		object.setUpdateDate(leaveSetting.getUpdateDate());

		leaveSettingRepository.save(object);
		return 1;
	}

	public Staff staffRegister(Staff staff) { // save a staff member//save user relavant data and then save the staff
		if (!userService.isExistUserByEmail(staff.getUserId().getEmail())) {
			System.out.println("-------------------------------User Exists");
		}
		if (!userService.isExistUserByEmail(staff.getUserId().getEmail())) {
			User user = userService.userRegister(staff.getUserId());
			staff.setUserId(user);
			System.out.println("USer ID :" + user.getUserId());
			return staffRepository.save(staff);
		}
		return null;
	}

	public Staff getStaffDetailsUsingStaffId(Integer staffId) {
		if (staffId != null && staffRepository.existsById(staffId)) {
			Staff staff = staffRepository.findByStaffId(staffId);
			return staff;
		}
		return null;
	}

	// delete Staff
	public String deleteStaff(Integer staffId) {
		if (staffId != null) {
			Staff staff = getStaffDetailsUsingStaffId(staffId);
			// delete user relavant data
			staffRepository.deleteById(staff.getStaffId()); // delete staff relavant data
			userService.deleteUser(staff.getUserId().getUserId()); // delete user relavant data
			return "success";
		}
		return "error";
	}

	public List<StaffLeave> getLeaveList(Integer staffId) {
		List<StaffLeave> list = new ArrayList<StaffLeave>();
		if (staffId != null && staffRepository.existsById(staffId)) {
			list = staffLeaveRepository.findByStaffId(staffId, timeTableService.getLocalCurrentDate().getYear());
		}
		return list;
	}

	public Admin getAdminData(Integer userId) {
		if (userId != null && userRepository.existsById(userId)) {
			return adminRepository.findByUserId(userRepository.findByUserId(userId));
		}
		return null;
	}

	// update Admin Details
	public Integer adminUpdate(Admin admin) {
		
		System.out.println("################");
		System.out.println(admin);

		Boolean isPasswordChanged = false;
		Boolean isEmailChanged = false;
		if (userRepository.existsById(admin.getUserId().getUserId())
				&& adminRepository.existsById(admin.getAdminId())) {
			Integer userId = admin.getUserId().getUserId();
			Integer adminId = admin.getAdminId();
			User newUser = admin.getUserId();
			User currentUser = userRepository.findByUserId(userId);

			// check password change or not.If password change change then encode the
			// password.
			String newPassword = newUser.getPassword();
			String currentPassword = currentUser.getPassword();
			if (!currentPassword.equals(newPassword)) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				newPassword = encoder.encode(newPassword);
				newUser.setPassword(newPassword);
				isPasswordChanged = true;
			}

			// check update email is unique
			String newEmail = newUser.getEmail();
			User user1 = userRepository.findByEmail(newEmail);
			if (user1 != null && !user1.getUserId().equals(userId)) {
				return 2;// Same Email has another person.Save unsuccessful
			} else {

				String currentEmail = currentUser.getEmail();
				if (!currentEmail.equals(newEmail)) {
					isEmailChanged = true;
				}

				// check update nic has another person
				String nic = admin.getUserId().getNic();
				User user2 = userRepository.findByNic(nic);
				if (user2 != null && !user2.getUserId().equals(userId)) {
					return 3;// same nic has another person.Save unsuccessful
				} else {
					Admin currentAdmin = adminRepository.findByUserId(currentUser);
					currentAdmin.setName(admin.getName());
					currentAdmin.setUserId(admin.getUserId());
					adminRepository.save(currentAdmin);
					if (isPasswordChanged || isEmailChanged) {
						jwtInMemoryUserDetailsService.setUserInMemory();
					}
					return 1;// save successful
				}
			}

		}
		return null;
	}

}

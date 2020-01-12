package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.AdminStaff;
import com.gp.learners.entities.FuelPayment;
import com.gp.learners.entities.InsurancePayment;
import com.gp.learners.entities.LessonDayFeedback;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.TimeSlot;
//import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.User;
import com.gp.learners.entities.Vehicle;
import com.gp.learners.entities.VehicleCategory;
import com.gp.learners.repositories.AdministrativeStaffRepository;
import com.gp.learners.repositories.FuelPaymentRepository;
import com.gp.learners.repositories.InsurancePaymentRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.TimeSlotRepositroy;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.repositories.VehicleRepository;
import com.gp.learners.repositories.VehicleCategoryRepository;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	InsurancePaymentRepository insurancePaymentRepository;
	
	@Autowired
	FuelPaymentRepository fuelPaymentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	@Autowired
	AdministrativeStaffRepository administrativeStaffRepository;
	
	@Autowired
	VehicleCategoryRepository vehicleCategoryRepository;

	
//	public Integer addVehicle(Vehicle vehicle) {
//		if(isNotExistVehicle(vehicle.getVehicleId())) {
//			vehicleRepository.save(vehicle);
//			System.out.println("In Vehicle service Add");
//			return 1;
//		}
//		return 0;
//	}
	
	public Integer vehicleAdd(Vehicle vehicle) {
		if(isNotExistVehicle(vehicle.getVehicleId())) {
			vehicleRepository.save(vehicle);
			System.out.println("In Vehicle service Add");
			return 1;
		}
		return 0;
	}

	
	
	private boolean isNotExistVehicle(Integer vehicleId) {
		
		Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId);
		if(vehicle != null ) {
			return false;
		}
		return true;
	}


	// getVehicleList
		public List<Vehicle> getVehicleList() {
			System.out.println("In Vehicle service");
			List<Vehicle> vehicleList = vehicleRepository.findAll();
//			Video video = videoList.get(0);
//			System.out.println(video.getDescription());
			return vehicleList;
		}

		// get Vehicle Details
		public Vehicle getVehicleList(Integer vehicleId) {
			if (vehicleId != null) {
				if (vehicleRepository.existsById(vehicleId)) {
					System.out.println("In Vehicle service Add");
					return vehicleRepository.findByVehicleId(vehicleId);
				}
			}
			return new Vehicle();
		}

	// Add Vehicle
//		public String addVehicle(Vehicle vehicle) {
//
//		Vehicle result = vehicleRepository.save(vehicle);
//		if (result != null)
//			return "success";
//		else
//			return "notsuccess";
//	}
	
	
	// delete vehicle
	public String deleteVehicle(Integer vehicleId) {
		if (vehicleId != null) {
			if (vehicleRepository.existsById(vehicleId)) {
				vehicleRepository.deleteById(vehicleId);
				return "success";
			}
		}
		return "error";
	}

	// update Vehicle Details
	public Vehicle updateVehicle(Vehicle vehicle) {
		if (vehicleRepository.existsById(vehicle.getVehicleId())) {
			return vehicleRepository.save(vehicle);
		}

		return new Vehicle();
	}
	
	public List<InsurancePayment> getVehicleInsurancePaymentDetails(Integer vehicleId){
		if(vehicleRepository.existsById(vehicleId)) {
			Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);
			List<InsurancePayment> insurancePayemntList = insurancePaymentRepository.findByVehicleId(vehicleId);
			return insurancePayemntList;
		}
		return null;
	}
	
	public Integer addInsurancePayment(Integer vehicleId,InsurancePayment insurancePayment) {
		if(vehicleRepository.existsById(vehicleId)) {
			Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);
			InsurancePayment object  = insurancePaymentRepository.findByVehicleIdAndYear(vehicleId, insurancePayment.getYear());
			if(object==null) {//there is no any record this year of this vehicle(No duplicate record)
				InsurancePayment newObject = new InsurancePayment();
				newObject.setYear(insurancePayment.getYear());
				newObject.setAmount(insurancePayment.getAmount());
				newObject.setDate(insurancePayment.getDate());
				newObject.setStartDate(insurancePayment.getStartDate());
				newObject.setEndDate(insurancePayment.getEndDate());
				newObject.setVehicleId(vehicle);
				insurancePaymentRepository.save(newObject);
				return 1;
			}
		}
		return null;
	}
	
	public List<FuelPayment> getVehicleFuelData(){
		return fuelPaymentRepository.findAll(new Sort(Sort.Direction.ASC, "month"));
	}
	
	public Integer addVehicleFuelData(FuelPayment fuelPayment,Integer userId) {
		FuelPayment object = fuelPaymentRepository.findByMonth(fuelPayment.getMonth());
		if(object==null && userId!=null) {
			if(userRepository.existsById(userId)) {
				User user = userRepository.findByUserId(userId);
				if(user.getRole()==3) {
					Staff staff = staffRepository.findByUserId(userId);
					AdminStaff adminStaff = administrativeStaffRepository.getAdminStaffByStaffId(staff.getStaffId());
					
					FuelPayment newObject = new FuelPayment();
					newObject.setAmount(fuelPayment.getAmount());
					newObject.setMonth(fuelPayment.getMonth());
					newObject.setAdminStaffId(adminStaff);
					fuelPaymentRepository.save(newObject);
					return 1;
				}
				
			}
		}
		return null;
	}
	
//	private String getTransmission(int i) {
//		if (i == 1) {
//			return "Manual";
//		}
//		return "Auto";
//	}
	
	// vehicleCategory functions
		public List<VehicleCategory> getVehicleCategoryList() {
			System.out.println("In Vehicle service vehicleCategory");
//			List<VehicleCategory> vehicleCategoryList = vehicleCategoryRepository.findAll();
			List<VehicleCategory> vehicleCategoryList = vehicleCategoryRepository.findAll();
			if (vehicleCategoryList != null) {
				return vehicleCategoryList;
			}
			return new ArrayList<VehicleCategory>();
		}


		
		public String updateVehicleCategory(VehicleCategory vehicleCategory) {
			if (vehicleCategory.getVehicleCategoryId() != null && vehicleCategory.getVehicleCategoryId() > 0) {
				if (vehicleCategoryRepository.existsById(vehicleCategory.getVehicleCategoryId())) {
					vehicleCategoryRepository.save(vehicleCategory);
					return "success";
				}
			}
			return "notsuccess";
		}
//
//		public String addTimeSlot(TimeSlot timeSlot) {
//			if (notExistFromAndTo(timeSlot)) {
//				timeSlotRepository.save(timeSlot);
//				return "success";
//			}
//			return "notsuccess";
//		}
//
//		public Integer deleteTimeSlot(Integer timeSlotId) {
//			if (timeSlotId != null && timeSlotId > 0) {
//				if (timeSlotRepository.existsById(timeSlotId)) {
//
//					try {
//						timeSlotRepository.deleteById(timeSlotId);
//
//						// Erase the Id in the lessonDayfeedBack table
//						List<LessonDayFeedback> feedbackList = lessonDayFeedbackRepository.findByTimeSlot(timeSlotId);
//						if (feedbackList.size() > 0) {
//							for (LessonDayFeedback lessonDayFeedback : feedbackList) {
//								if (lessonDayFeedback.getTime1() == timeSlotId) {
//									lessonDayFeedback.setTime1(-1);
//								}
//								if (lessonDayFeedback.getTime2() == timeSlotId) {
//									lessonDayFeedback.setTime2(-1);
//								}
//								lessonDayFeedbackRepository.save(lessonDayFeedback);
//							}
//						}
//
//						return 1;// delete success
//					} catch (Exception e) {
//						return 0;// Because of the foreign keyConstrain
//					}
//				}
//			}
//			return null;
//		}



		
}

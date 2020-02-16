package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.AdminStaff;
import com.gp.learners.entities.FuelPayment;
import com.gp.learners.entities.InsurancePayment;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.User;
import com.gp.learners.entities.Vehicle;
import com.gp.learners.entities.VehicleCategory;
import com.gp.learners.repositories.AdministrativeStaffRepository;
import com.gp.learners.repositories.FuelPaymentRepository;
import com.gp.learners.repositories.InsurancePaymentRepository;
import com.gp.learners.repositories.StaffRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.repositories.VehicleCategoryRepository;
import com.gp.learners.repositories.VehicleRepository;

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

	public Vehicle getVehicle(Integer vehicleId) {
		if (vehicleRepository.existsById(vehicleId)) {
			return vehicleRepository.getVehicle(vehicleId);
		}
		return null;
	}

	public List<Vehicle> getVehicleList(Integer status) {
		if (status >= 0 && status <= 1) {
			return vehicleRepository.getVehicleList(status);
		}
		return null;
	}

	public List<InsurancePayment> getVehicleInsurancePaymentDetails(Integer vehicleId) {
		if (vehicleRepository.existsById(vehicleId)) {
			Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);
			List<InsurancePayment> insurancePayemntList = insurancePaymentRepository.findByVehicleId(vehicleId);
			return insurancePayemntList;
		}
		return null;
	}

	public Integer addInsurancePayment(Integer vehicleId, InsurancePayment insurancePayment) {
		if (vehicleRepository.existsById(vehicleId)) {
			Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);
			InsurancePayment object = insurancePaymentRepository.findByVehicleIdAndYear(vehicleId,
					insurancePayment.getYear());
			if (object == null) {// there is no any record this year of this vehicle(No duplicate record)
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

	public List<FuelPayment> getVehicleFuelData(Integer year) {
		return fuelPaymentRepository.findByYear(year);
	}

	public Integer addVehicleFuelData(FuelPayment fuelPayment, Integer userId) {
		FuelPayment object = fuelPaymentRepository.findByMonth(fuelPayment.getMonth(), fuelPayment.getYear());
		if (object == null && userId != null) {
			if (userRepository.existsById(userId)) {
				User user = userRepository.findByUserId(userId);
				if (user.getRole() == 3) {
					Staff staff = staffRepository.findByUserId(userId);
					AdminStaff adminStaff = administrativeStaffRepository.getAdminStaffByStaffId(staff.getStaffId());

					FuelPayment newObject = new FuelPayment();
					newObject.setAmount(fuelPayment.getAmount());
					newObject.setMonth(fuelPayment.getMonth());
					newObject.setAdminStaffId(adminStaff);
					newObject.setDate(fuelPayment.getDate());
					newObject.setYear(fuelPayment.getYear());
					fuelPaymentRepository.save(newObject);
					return 1;
				}

			}
		}
		return null;
	}

	/////////////////////////////////////////////////////////////////
	public String VehicleAdd(Vehicle vehicle) {

		Vehicle result = vehicleRepository.save(vehicle);
		System.out.println(vehicle);

		if (result != null)

			return "success";
		else
			return "notsuccess";
	}

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

	// getVehicleList
	public List<Vehicle> getVehicleList() {
		System.out.println("In Vehicle service");
		List<Vehicle> vehicleList = vehicleRepository.findAll();
//		Video video = videoList.get(0);
//		System.out.println(video.getDescription());
		return vehicleList;
	}

	// vehicleCategory functions
	public List<VehicleCategory> getVehicleCategoryList() {
		System.out.println("In Vehicle service vehicleCategory");
//				List<VehicleCategory> vehicleCategoryList = vehicleCategoryRepository.findAll();
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
//			public String addTimeSlot(TimeSlot timeSlot) {
//				if (notExistFromAndTo(timeSlot)) {
//					timeSlotRepository.save(timeSlot);
//					return "success";
//				}
//				return "notsuccess";
//			}
	//
	public Integer deleteVehicleCategory(Integer vehicleCategoryId) {
		if (vehicleCategoryId != null && vehicleCategoryId > 0) {
			if (vehicleCategoryRepository.existsById(vehicleCategoryId)) {

				try {
					vehicleCategoryRepository.deleteById(vehicleCategoryId);

					// Erase the Id in the lessonDayfeedBack table
//							List<LessonDayFeedback> feedbackList = lessonDayFeedbackRepository.findByTimeSlot(timeSlotId);
//							if (feedbackList.size() > 0) {
//								for (LessonDayFeedback lessonDayFeedback : feedbackList) {
//									if (lessonDayFeedback.getTime1() == vehicleCategoryId) {
//										lessonDayFeedback.setTime1(-1);
//									}
//									if (lessonDayFeedback.getTime2() == vehicleCategoryId) {
//										lessonDayFeedback.setTime2(-1);
//									}
//									lessonDayFeedbackRepository.save(lessonDayFeedback);
//								}
//							}

					return 1;// delete success
				} catch (Exception e) {
					return 0;// Because of the foreign keyConstrain
				}
			}
		}
		return null;
	}

}

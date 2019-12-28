package com.gp.learners.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Package;
import com.gp.learners.entities.YearUpdate;
import com.gp.learners.entities.mapObject.PackagePaymentDataMap;
import com.gp.learners.repositories.CourseFeeRepository;
import com.gp.learners.repositories.FuelPaymentRepository;
import com.gp.learners.repositories.InsurancePaymentRepository;
import com.gp.learners.repositories.MaintainanceRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.SalaryRepository;
import com.gp.learners.repositories.YearUpdateRepository;


@Service
public class ReportService {
	
	@Autowired
	CourseFeeRepository courseFeeRepository;
	
	@Autowired
	FuelPaymentRepository fuelPaymentRepository;
	
	@Autowired
	SalaryRepository salaryRepository;
	
	@Autowired
	InsurancePaymentRepository insurancePaymentRepository;
	
	@Autowired
	YearUpdateRepository yearUpdateRepository;
	
	@Autowired
	MaintainanceRepository maintainanceRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	TimeTableService timeTableService;
	
	public List<Double> studentMonthlyPayment(Integer year) {	
		
		List<Double> studentMonthlyPaymentList = new ArrayList<Double>(12);
		for(int i=1 ; i<=12 ; i++) {
			Double result = courseFeeRepository.findPaymentByMonthAndYear(year,i);
			if(result==null) {
				result=0D;
			}
			studentMonthlyPaymentList.add(result);
		}
		return studentMonthlyPaymentList;
	}
	
	public List<Double> fuelMonthlyExpenses(Integer year){
		List<Double> fuelMonthlyPayment = new ArrayList<Double>(12);
		for(int i=1 ; i<=12 ; i++) {
			Double result = fuelPaymentRepository.findPaymentByMonth(i,year);
			if(result==null) {
				result = 0D;
			}
			fuelMonthlyPayment.add(result);
		}
		return fuelMonthlyPayment;
	}
	
	public List<Double> salaryMonthlyExpenses(Integer year){
		List<Double> salaryMonthlyExpenses = new ArrayList<Double>(12);
		for(int i=1 ; i<=12 ; i++) {
			Double result = salaryRepository.findPaymentByMonth(i,year);
			if(result==null) {
				result=0D;
			}
			salaryMonthlyExpenses.add(result);
		}
		return salaryMonthlyExpenses;
	}
	

	public List<Double> insuranceMonthlyExpenses(Integer year){
		 
		 List<Double> insuranceMonthlyExpenses = new ArrayList<Double>(12);
		 for(int i=1 ; i<=12 ; i++) {
			 Double result = insurancePaymentRepository.findPaymentByMonthAndYear(year, i);
			 if(result==null) {
				 result=0D;
			 }
			 insuranceMonthlyExpenses.add(result);
		 }
		 return insuranceMonthlyExpenses;
	}
	
	public List<Double> vehicleMaintainanceMonthlyExpenses(Integer year){
		 
		 List<Double> vehicleMaintainanceMonthlyExpenses = new ArrayList<Double>(12);
		 for(int i=1 ; i<=12 ; i++) {
			 Double result = maintainanceRepository.findPaymentByMonthAndYear(i,year);
			 if(result==null) {
				 result=0D;
			 }
			 vehicleMaintainanceMonthlyExpenses.add(result);
		 }
		 return vehicleMaintainanceMonthlyExpenses;
	}
	
	public List<Double> monthlyProfit(Integer year){
		List<Double> profitMonthly = new ArrayList<Double>(12);
		
		List<Double> studentPayment = studentMonthlyPayment(year);//income
		List<Double> fuelExpenses = fuelMonthlyExpenses(year);//outcome
		List<Double> salaryExpenses = salaryMonthlyExpenses(year);//outcome
		List<Double> insuranceExpenses = insuranceMonthlyExpenses(year);//outcome
		List<Double> vehicleMaintainanceExpenses = vehicleMaintainanceMonthlyExpenses(year);
		
		for(int i =0 ; i<12 ; i++) {
			Double result = studentPayment.get(i)-fuelExpenses.get(i)-salaryExpenses.get(i)-insuranceExpenses.get(i)-vehicleMaintainanceExpenses.get(i);
			profitMonthly.add(result);
		}	
		return profitMonthly;
	}
	
	public List<List<PackagePaymentDataMap>> getPackagePaymentMonthly(Integer year){
		List<List<PackagePaymentDataMap>> packagePaymentMonthlyList = new ArrayList<List<PackagePaymentDataMap>>(12);
		List<Package> packageList = packageRepository.findPackages(1);//get Active packages
		
		if(packageList!=null && packageList.size()>0) {
			for(int i=1 ; i<=getCurrentMonth() ; i++) {
				List<PackagePaymentDataMap> packagePaymentList = new ArrayList<PackagePaymentDataMap>(packageList.size());
				for (Package packageData : packageList) {
					Double result = courseFeeRepository.findPaymentByPackageIdAndYearAndMonth(packageData,year,i);
					if(result==null) {
						result=0D;
					}
					PackagePaymentDataMap object = new PackagePaymentDataMap();
					object.setPackageName(packageData.getTitle());
					object.setPayment(result);
					packagePaymentList.add(object);
				}
				packagePaymentMonthlyList.add(packagePaymentList);
			}
		}
		
		return packagePaymentMonthlyList;
	}
	
	public Integer getYear() {
		YearUpdate object =yearUpdateRepository.getLastRecord();
		return object.getUpdateYear();
	}
	
	public Integer getCurrentMonth() {
		LocalDate date = timeTableService.getLocalCurrentDate();
		return date.getMonthValue();
	}
	
//	public List<Integer> getYears(){
//		
//	}
}

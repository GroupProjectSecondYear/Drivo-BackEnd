package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.YearUpdate;
import com.gp.learners.entities.mapObject.PackagePaymentDataMap;
import com.gp.learners.service.ReportService;
import com.gp.learners.service.SystemUpdateService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	SystemUpdateService systemUpdateService;
	
	@GetMapping("student/month/payment/{year}")
	public ResponseEntity<List<Double>> studentMonthlyPayment(@PathVariable("year") Integer year) {
		return ResponseEntity.ok(reportService.studentMonthlyPayment(year));
	}
	
	@GetMapping("fuel/month/payment/{year}")
	public ResponseEntity<List<Double>> fuelMonthlyExpenses(@PathVariable("year") Integer year){
		return ResponseEntity.ok(reportService.fuelMonthlyExpenses(year));
	}
	
	@GetMapping("salary/month/payment/{year}")
	public ResponseEntity<List<Double>> salaryMonthlyExpenses(@PathVariable("year") Integer year){
		return ResponseEntity.ok(reportService.salaryMonthlyExpenses(year));
	}
	
	@GetMapping("insurance/month/payment/{year}")
	public ResponseEntity<List<Double>> insuranceMonthlyExpenses(@PathVariable("year") Integer year){
		return ResponseEntity.ok(reportService.insuranceMonthlyExpenses(year));
	}
	
	@GetMapping("vehicle/maintainance/month/payment/{year}")
	public ResponseEntity<List<Double>> vehicleMaintainanceMonthlyExpenses(@PathVariable("year") Integer year){
		return ResponseEntity.ok(reportService.vehicleMaintainanceMonthlyExpenses(year));
	}
	
	@GetMapping("month/profit/{year}")
	public List<Double> monthlyProfit(@PathVariable("year") Integer year){
		return reportService.monthlyProfit(year);
	}
	
	@GetMapping("report/package/month/payment/{year}")
	public ResponseEntity<List<List<PackagePaymentDataMap>>> getPackagePaymentMonthly(@PathVariable("year") Integer year){
		return ResponseEntity.ok(reportService.getPackagePaymentMonthly(year));
	}
	
//	public ResponseEntity<List<Integer>> getYears(){
//		
//	}
	
	//system update 
	@GetMapping("system/update")
	public ResponseEntity<YearUpdate> systemUpdate(){
		return ResponseEntity.ok(systemUpdateService.getUpdateMessage());
	}
}

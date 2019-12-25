package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.service.ReportService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("student/month/payment")
	public ResponseEntity<List<Double>> studentMonthlyPayment() {
		return ResponseEntity.ok(reportService.studentMonthlyPayment());
	}
	
	@GetMapping("fuel/month/payment")
	public ResponseEntity<List<Double>> fuelMonthlyExpenses(){
		return ResponseEntity.ok(reportService.fuelMonthlyExpenses());
	}
	
	@GetMapping("salary/month/payment")
	public ResponseEntity<List<Double>> salaryMonthlyExpenses(){
		return ResponseEntity.ok(reportService.salaryMonthlyExpenses());
	}
	
	@GetMapping("insurance/month/payment")
	public ResponseEntity<List<Double>> insuranceMonthlyExpenses(){
		return ResponseEntity.ok(reportService.insuranceMonthlyExpenses());
	}
	
	@GetMapping("month/profit")
	public List<Double> monthlyProfit(){
		return reportService.monthlyProfit();
	}
}

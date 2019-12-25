package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.repositories.CourseFeeRepository;
import com.gp.learners.repositories.FuelPaymentRepository;
import com.gp.learners.repositories.InsurancePaymentRepository;
import com.gp.learners.repositories.SalaryRepository;


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
	
	public List<Double> studentMonthlyPayment() {	
		Integer year = getYear();
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
	
	public List<Double> fuelMonthlyExpenses(){
		List<Double> fuelMonthlyPayment = new ArrayList<Double>(12);
		for(int i=1 ; i<=12 ; i++) {
			Double result = fuelPaymentRepository.findPaymentByMonth(i);
			if(result==null) {
				result = 0D;
			}
			fuelMonthlyPayment.add(result);
		}
		return fuelMonthlyPayment;
	}
	
	public List<Double> salaryMonthlyExpenses(){
		List<Double> salaryMonthlyExpenses = new ArrayList<Double>(12);
		for(int i=1 ; i<=12 ; i++) {
			Double result = salaryRepository.findPaymentByMonth(i);
			if(result==null) {
				result=0D;
			}
			salaryMonthlyExpenses.add(result);
		}
		return salaryMonthlyExpenses;
	}
	

	public List<Double> insuranceMonthlyExpenses(){
		 Integer year = getYear();
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
	
	public List<Double> monthlyProfit(){
		List<Double> profitMonthly = new ArrayList<Double>(12);
		
		List<Double> studentPayment = studentMonthlyPayment();//income
		List<Double> fuelExpenses = fuelMonthlyExpenses();//outcome
		List<Double> salaryExpenses = salaryMonthlyExpenses();//outcome
		List<Double> insuranceExpenses = insuranceMonthlyExpenses();//outcome
		
		for(int i =0 ; i<12 ; i++) {
			Double result = studentPayment.get(i)-fuelExpenses.get(i)-salaryExpenses.get(i)-insuranceExpenses.get(i);
			profitMonthly.add(result);
		}	
		return profitMonthly;
	}
	
	public Integer getYear() {
		return 2019;
	}
}

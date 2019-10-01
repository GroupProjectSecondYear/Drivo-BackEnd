package com.gp.learners.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.CourseFee;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.repositories.CourseFeeRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.UserRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PayPalClientService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	StudentRepository studentRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired 
	StudentPackageRepository studentPackageRepository;
	
	@Autowired
	CourseFeeRepository courseFeeRepository;
	
	@Autowired 
	StudentService studentService;
	
	
	public PayPalClientService() {
		
	}
	
	String clientId = "AcHjBWKGO_0_CZPcrFg1G9xynSWJ8LK1YsO2qKMgF1qd8vvmbS0oUk76iiyfPl3tMV91E_yMSYgdg61V";
	String clientSecret = "EHRoMMwUIaEF5-2MRCD6tIgfsNUTFMYbZE2aC_SALaNmTR1JSR_oo3wC3rDhNquoA26TtRjk7eDJ7dqB";
	
	public Map<String, Object> createPayment(String sum,Integer userId){
		Map<String, Object> response = new HashMap<String, Object>();
	    Amount amount = new Amount();
	    amount.setCurrency("USD");
	    amount.setTotal(sum);
	    Transaction transaction = new Transaction();
	    transaction.setAmount(amount);
	    List<Transaction> transactions = new ArrayList<Transaction>();
	    transactions.add(transaction);

	    Payer payer = new Payer();
	    payer.setPaymentMethod("paypal");

	    Payment payment = new Payment();
	    payment.setIntent("sale");
	    payment.setPayer(payer);
	    payment.setTransactions(transactions);

	    RedirectUrls redirectUrls = new RedirectUrls();
	    redirectUrls.setCancelUrl("http://localhost:4200/");
	    redirectUrls.setReturnUrl("http://localhost:4200/student-payment/"+userId);
	    payment.setRedirectUrls(redirectUrls);
	    Payment createdPayment;
	    try {
	        String redirectUrl = "";
	        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
	        createdPayment = payment.create(context);
	        if(createdPayment!=null){
	            List<Links> links = createdPayment.getLinks();
	            for (Links link:links) {
	                if(link.getRel().equals("approval_url")){
	                    redirectUrl = link.getHref();
	                    break;
	                }
	            }
	            response.put("status", "success");
	            response.put("redirect_url", redirectUrl);
	        }
	    } catch (PayPalRESTException e) {
	        System.out.println("Error happened during payment creation!");
	    }
	    return response;
	}
	
	
	public Integer completePayment(String paymentId,String payerId,Integer userId,Integer packageId,Float amount){
	   
	   if(validateInformation(userId, packageId, amount)) {
		    Payment payment = new Payment();
		    payment.setId(paymentId);

		    PaymentExecution paymentExecution = new PaymentExecution();
		    paymentExecution.setPayerId(payerId);
		    try {
		        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
		        Payment createdPayment = payment.execute(context, paymentExecution);
		        if(createdPayment!=null){
		           
		           //payment save to db
		        	return savePayment(userId, packageId, amount);
		        	
		        }
		    } catch (PayPalRESTException e) {
		        System.err.println(e.getDetails());
		    }
	   }
	    return -1;
	    
	}
	
	private Boolean validateInformation(Integer userId,Integer packageId,Float amount) {
		System.out.println("Hello");
		System.out.println("userId:"+userId+" packageId:"+packageId+" amount"+amount);
		if(userRepository.existsById(userId) && packageRepository.existsById(packageId) && amount>0) {
			Integer studentId = studentRepository.findByUserId(userRepository.findByUserId(userId));
			if(studentId != null) {
				
				//get Course Fee
				Package packageObject=packageRepository.findByPackageId(packageId);
				Float courseFee=packageObject.getPrice();
				
				StudentPackage studentPackage=studentPackageRepository.findByStudentIdAndPackageId(studentRepository.getStudentId(studentId), packageObject);//get StudentPAckage object
				Float payment=courseFeeRepository.getTotalFee(studentPackage);//Already done payments
				
				Float balance;
				if(payment != null) {
					balance=courseFee-payment;
				}else {
					balance=courseFee;
				}
				
				if(balance>0) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Integer savePayment(Integer userId,Integer packageId,Float amount) {
		CourseFee object = new CourseFee();
		
		Integer studentId=studentRepository.findByUserId(userRepository.findByUserId(userId));
		object.setAmount(amount);
		object.setDate(new Date());
		object.setMethod(2);
		
		return studentService.courseFeeAdd(studentId, packageId, object);
	}
	
}

package com.gp.learners.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.PayPalClient;
import com.gp.learners.service.PayPalClientService;


@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class PayPalController {

	@Autowired
    private final PayPalClientService payPalClient = new PayPalClientService();
	

    @PostMapping(value = "paypal/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum,@RequestParam("userId") Integer userId){
    	System.out.println(sum);
        return payPalClient.createPayment(sum,userId);
    }
    
  
	@PostMapping(value = "paypal/complete/payment/{paymentId}/{payerId}/{userId}/{packageId}/{amount}")
    public ResponseEntity<Void> completePayment(@PathVariable("paymentId") String paymentId,@PathVariable("payerId") String payerId,
    											@PathVariable("userId") Integer userId,@PathVariable Integer packageId,
    											@PathVariable("amount") Float amount){
        String reply=payPalClient.completePayment(paymentId,payerId,userId,packageId,amount);
        System.out.println(reply);
        if(reply.equals("success")) {
        	return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
	
}


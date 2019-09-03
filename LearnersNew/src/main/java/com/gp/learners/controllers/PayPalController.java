//package com.gp.learners.controllers;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.gp.learners.entities.PayPalClient;
//
//
//@RestController
//@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
//public class PayPalController {
//
//	
//    private final PayPalClient payPalClient=new PayPalClient();
//	
//
//    @PostMapping(value = "paypal/make/payment")
//    public Map<String, Object> makePayment(@RequestParam("sum") String sum){
//        return payPalClient.createPayment(sum);
//    }
//    
//  
//	@PostMapping(value = "/complete/payment")
//    public Map<String, Object> completePayment(HttpServletRequest request){
//        return payPalClient.completePayment(request);
//    }
//}
//

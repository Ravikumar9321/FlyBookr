package com.flight_booking_system.Controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.PaymentMode;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Service.Payment_Service;
import com.flight_booking_system.Service.Payment_Service;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class Payment_Contoller {
	@Autowired
	private Payment_Service service;
	//save Payment
	@PostMapping
	public ResponseEntity<ResponseStructure<Payment>> savePayment( @RequestBody Payment payment) {
		return service.savePayment(payment);
	}

	
    //ii)fetch all Payment details
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Payment>>> fetchallPaymentDetails() {
		return service.fetchallPayment();
	}
	
    //iii)fetch  Payment details by Id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Payment>> fetchPaymentDetailsById(@PathVariable int id) {
		return service.fetchPaymentById(id);
	}
	//iv)update Payment details
	@PutMapping
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentDetails(@RequestBody Payment payment){
		return service.updatePayment(payment);
	}
	//v)delete Payment detail
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deletePaymentDetails(@PathVariable int id){
		return service.deletePayment(id);
	}
	
	//vi)Payment details in pagination and sort format
	   @GetMapping("/{pageNumber}/{pageSize}/{field}")
	   public  ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentDetailsByPagenation_Sort(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String field){
		   return service.getPaymentByPagination_Sort(pageNumber,pageSize,field);
	   }
	  //7)get Payment details by status
	   @GetMapping("/status/{status}")
	 		public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByStatus(@PathVariable Bookingstatus status) {
	 			return service.getPaymentDetailsByStatus(status);
	 		}
   //8)get Payment details price greater than
	   
	   @GetMapping("/price/{price}")
		public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByPrice(@PathVariable Double price) {
			return service.getPaymentDetailsByPrice(price);
		}
	 //9)get Payment details by paymentMode
	   @GetMapping("/paymentMode/{paymentMode}")
	 		public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByPaymentMode(@PathVariable PaymentMode paymentMode) {
	 			return service.getPaymentDetailsByPaymentMode(paymentMode);
	 		}
	
	
}

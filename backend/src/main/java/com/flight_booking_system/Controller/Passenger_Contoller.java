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

import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Service.Passenger_Service;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/passenger")
public class Passenger_Contoller {
	@Autowired
	private Passenger_Service service;
	//save passenger
	@PostMapping
	public ResponseEntity<ResponseStructure<Passenger>> savePassenger( @RequestBody Passenger passenger) {
		return service.savePassenger(passenger);
	}

	
    //ii)fetch all Passenger details
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Passenger>>> fetchallPassengerDetails() {
		return service.fetchallPassenger();
	}
	
    //iii)fetch  Passenger details by Id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Passenger>> fetchPassengerDetailsById(@PathVariable int id) {
		return service.fetchPassengerById(id);
	}
	//iv)update Passenger details
	@PutMapping
	public ResponseEntity<ResponseStructure<Passenger>> updatePassengerDetails(@RequestBody Passenger passenger){
		return service.updatePassenger(passenger);
	}
	//v)delete Passenger detail
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deletePassengerDetails(@PathVariable int id){
		return service.deletePassenger(id);
	}
	
	//vi)Passenger details in pagination and sort format
	   @GetMapping("/{pageNumber}/{pageSize}/{field}")
	   public  ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerDetailsByPagenation_Sort(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String field){
		   return service.getPassengerByPagination_Sort(pageNumber,pageSize,field);
	   }
	  //vii)get Passenger by contact 
	   @GetMapping("/contact/{contact}")
	   public ResponseEntity<ResponseStructure<Passenger>> getPassengerDetailsByContactNumber(@RequestBody String contact){
			return service.getPassengerDetailsByContactNumber(contact);
		}

	
	
}

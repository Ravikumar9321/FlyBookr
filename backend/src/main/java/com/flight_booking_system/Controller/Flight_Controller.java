package com.flight_booking_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Repository.Flight_Repository;
import com.flight_booking_system.Service.Flight_Service;
@RestController
@RequestMapping("/api/flight")
@CrossOrigin(origins = "http://localhost:3000")
public class Flight_Controller {
	
	@Autowired
	private Flight_Service service;
	
	//i) save details
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> saveFlightDetails(@RequestBody Flight flight) {
		return service.saveFlight(flight);
	}
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<Flight>>> saveAllFlightDetails(@RequestBody List<Flight> flight) {
		return service.saveAllFlightDetails(flight);
	}
	
	
    //ii)fetch all Flight details
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Flight>>> fetchallFlightDetails() {
		return service.fetchallFlight();
	}
	
    //iii)fetch  Flight details by Id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> fetchFlightDetailsById(@PathVariable int id) {
		return service.fetchFlightById(id);
	}
	//iv)update Flight details
	@PutMapping
	public ResponseEntity<ResponseStructure<Flight>> updateFlightDetails(@RequestBody Flight flight){
		return service.updateFlight(flight);
	}
	//v)delete Flight detail
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteFlightDetails(@PathVariable int id){
		return service.deleteFlight(id);
	}
	
	//vi)Flight details in pagination and sort format
	   @GetMapping("/{pageNumber}/{pageSize}/{field}")
	   public  ResponseEntity<ResponseStructure<Page<Flight>>> getFlightDetailsByPagenation_Sort(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String field){
		   return service.getFlightByPagination_Sort(pageNumber,pageSize,field);
	   }
	  //7)flight get by airlineName
	   @GetMapping("airline/{airlineName}")
	   public  ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsByAirlineName(@PathVariable String airlineName){
		   return service.getFlightDetailsByAirlineName(airlineName);
	   }	   
	 //8)flight get by source and destination
	   @GetMapping("/{source}/{destination}")
	   public  ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsBysourceAndDestionation(@PathVariable String source,@PathVariable String destination){
		   return service.getFlightDetailsBysourceAndDestionation(source,destination);
	   }
	   //9)flight get by Cost
	   @GetMapping("price/{price}")
	   public  ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsByPrice(@PathVariable String price){
		   return service.getFlightDetailsByPrice(price);
	   }	

}

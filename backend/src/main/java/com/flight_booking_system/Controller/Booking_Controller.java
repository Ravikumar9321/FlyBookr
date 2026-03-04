package com.flight_booking_system.Controller;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Bookings;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Service.Booking_Service;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/booking")
public class Booking_Controller {
 @Autowired
 private Booking_Service service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Bookings>> createBooking(@RequestBody Bookings booking){
		return service.createBooking(booking);
	}
	
	
	
    //ii)fetch all Bookings details
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Bookings>>> fetchallBookingsDetails() {
		return service.fetchallBookings();
	}
	
    //iii)fetch  Bookings details by Id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bookings>> fetchBookingsDetailsById(@PathVariable int id) {
		return service.fetchBookingsById(id);
	}
	//iv)update Bookings details
	@PutMapping
	public ResponseEntity<ResponseStructure<Bookings>> updateBookingsDetails(@RequestBody Bookings booking){
		return service.updateBookings(booking);
	}
	//v)delete Bookings detail
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBookingsDetails(@PathVariable int id){
		return service.deleteBookings(id);
	}
	
	//vi)Bookings details in pagination and sort format
	   @GetMapping("/{pageNumber}/{pageSize}/{field}")
	   public  ResponseEntity<ResponseStructure<Page<Bookings>>> getBookingsDetailsByPagenation_Sort(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String field){
		   return service.getBookingsByPagination_Sort(pageNumber,pageSize,field);
	   }
	 //7getAll passsenger based on booking id
	   @GetMapping("/passenger/{bookingId}")
	   public ResponseEntity<ResponseStructure<List<Passenger>>>getallPassengersByBookingsId(@PathVariable Integer bookingId ) {
			return service.getallPassengersByBookingsId(bookingId);
		}
	 //8)get Bookings detail by Date
		@GetMapping("/date/{date}")
		public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingsDetailsByDate(@PathVariable LocalDate date){
			return service.getBookingsDetailsByDate(date);
		}
		
		 //9)get Bookings detail by status
		@GetMapping("/status/{status}")
		public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingsDetailsByStatus(@PathVariable Bookingstatus status){
			return service.getBookingsDetailsByStatus(status);
		}
		
		 //10)get payment detail by bookingId
			@GetMapping("/payment/{bookingId}")
			public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsbyBookingId(@PathVariable Integer bookingId){
				return service.getPaymentDetailsbyBookingId(bookingId);
			}
			
		 //11)get Booking detail by FlightId
				@GetMapping("/flight/{flightId}")
				public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingDetailsbyFlightId(@PathVariable Integer flightId){
					return service.getBookingDetailsbyFlightId(flightId);
				}
		//12)update Bookings details
		   @PutMapping("/{id}/{status}")
		   public ResponseEntity<ResponseStructure<Bookings>> updateBookingStatus(@PathVariable Integer id,@PathVariable Bookingstatus status){
			return service.updateBookingStatus(id,status);
				}

		
	
}

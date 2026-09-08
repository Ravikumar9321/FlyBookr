package com.flight_booking_system.Service;


import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flight_booking_system.DAO.*;
import com.flight_booking_system.DTO.*;
import com.flight_booking_system.Entity.*;
import com.flight_booking_system.Exception.*;

@Service
@Transactional

public class Booking_Service {
	@Autowired
	private Flight_Dao fdao;
	@Autowired
	private Booking_Dao bdao;
	@Autowired
	private Passenger_Dao pdao;
	
	
	public ResponseEntity<ResponseStructure<Bookings>> createBooking(Bookings booking) {
	    ResponseStructure<Bookings> rs = new ResponseStructure<>();

	    // ✅ Get flight by ID
	    Optional<Flight> opt = fdao.getFlightById(booking.getFlight().getId());
	    if (opt.isEmpty()) {
	        throw new NoRecordFoundException("Flight Details Not Found");
	    }

	    Flight flight = opt.get();

	    // ✅ Get all passengers already booked for this flight
	    List<Passenger> passengersForFlight = pdao.findByFlightId(flight.getId());

	    // ✅ Check seat availability
	    int alreadyBooked = passengersForFlight.size();
	    int requestedSeats = booking.getPassengers().size();

	    if (alreadyBooked + requestedSeats > flight.getTotalSeats()) {
	        throw new NoSeatAvailableException("No Seats are available");
	    }

	    // ✅ Validate seat numbers and link booking to passengers
	    for (Passenger newPassenger : booking.getPassengers()) {
	        for (Passenger existingPassenger : passengersForFlight) {
	            if (existingPassenger.getSeatNumber().equals(newPassenger.getSeatNumber())) {
	                throw new NoSeatAvailableException(
	                    "Seat " + newPassenger.getSeatNumber() + " is already booked"
	                );
	            }
	        }
	        newPassenger.setBooking(booking);
	    }

	    Payment payment = booking.getPayment();
	    payment.setBooking(booking);
	    payment.setAmount(flight.getPrice() * requestedSeats);
	    booking.setFlight(flight);

	  
	    Bookings savedBooking = bdao.createBooking(booking);

	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setData(savedBooking);
	    rs.setMessage("Booking successfully done");

	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}

 	        	  
		
	


//ii)fetch all Bookings details
public ResponseEntity<ResponseStructure<List<Bookings>>> fetchallBookings() {
	ResponseStructure<List<Bookings>> rs=new ResponseStructure<>();
	     List<Bookings> ls=bdao.getallBookings();
	     if(ls.size()>0) {
	    	 rs.setStatusCode(HttpStatus.OK.value());
	    	 rs.setMessage("Bookings details are retrieved");
	    	 rs.setData(ls);
	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	     }
	     else
	    	 throw new NoRecordFoundException("No Records Found");
	}

  //iii)fetch  Bookings details by id
public ResponseEntity<ResponseStructure<Bookings>> fetchBookingsById(int id) {
	ResponseStructure<Bookings> rs=new ResponseStructure<>();
     Optional<Bookings> Bookings =bdao.getBookingsById(id);
     if(Bookings.isPresent()) {
    	 rs.setStatusCode(HttpStatus.OK.value());
    	 rs.setMessage("Bookingss details are retrieved by BookingsId");
    	 rs.setData(Bookings.get());
    	 return new ResponseEntity<>(rs,HttpStatus.OK);
     }
     else
    	 throw new IdNotFoundException("Invalid,Bookings Id not Found");
}

//iv)update Bookings details
public ResponseEntity<ResponseStructure<Bookings>> updateBookings(Bookings booking) {
	ResponseStructure<Bookings> rs=new ResponseStructure<>();

        if(booking.getId()==null) 
        	throw new NullPointerException("Enter Valid Bookings Id");
      	 
     		 Optional<Bookings> opt=bdao.getBookingsById(booking.getId());

          if(opt.isPresent()) {
        	  rs.setStatusCode(HttpStatus.OK.value());
 	    	 rs.setMessage("Bookings details are updated");
 	    	 rs.setData(bdao.updateBooking(booking));
 	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
          }
          else
        	  throw new IdNotFoundException("Invalid ,Id is not Found");
}

//v)delete Bookings details
public ResponseEntity<ResponseStructure<String>> deleteBookings(int id) {
	ResponseStructure<String> rs=new ResponseStructure<>();
	    Optional<Bookings> opt=bdao.getBookingsById(id);
	    if(opt.isPresent()) {
	    	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Bookings details deleted");
     	    	 bdao.deleteBookings(opt.get());
     	    	 rs.setData("SUCCESS");
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	    }
	    else
      	  throw new IdNotFoundException("Invalid ,Id is not Found");
}

//vi)get Details in pagenation ,sort format
public ResponseEntity<ResponseStructure<Page<Bookings>>> getBookingsByPagination_Sort(int pageNumber, int pageSize,
		String field) {
	ResponseStructure<Page<Bookings>> rs=new ResponseStructure<>();
    Page<Bookings> l=bdao.getBookingsByPagenatio_Sort(pageNumber,pageSize,field);
	   if(!l.isEmpty()) {
		   rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Recordsfounded");
			rs.setData(l);
			return new ResponseEntity<>(rs,HttpStatus.OK);
	   }
	   else {
		  throw new NoRecordFoundException("No records Found");
	   }
	
}


public ResponseEntity<ResponseStructure<List<Passenger>>> getallPassengersByBookingsId(Integer id) {
	ResponseStructure<List<Passenger>> rs=new ResponseStructure<>();
    List<Passenger> passenger =bdao.getallPassengersByBookingsId(id);
    if(!passenger.isEmpty()) {
   	 rs.setStatusCode(HttpStatus.OK.value());
   	 rs.setMessage("Passenger details are retrieved by BookingsId");
   	 rs.setData(passenger);
   	 return new ResponseEntity<ResponseStructure<List<Passenger>>>(rs,HttpStatus.OK);
    }
    else
   	 throw new IdNotFoundException("Invalid,Bookings Id not Found");
}


public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingsDetailsByDate(LocalDate date) {
	
	ResponseStructure<List<Bookings>> rs=new ResponseStructure<>();
    List<Bookings> bookings =bdao.getBookingsDetailsByDate(date);
    if(!bookings.isEmpty()) {
   	 rs.setStatusCode(HttpStatus.OK.value());
   	 rs.setMessage("Booking details are retrieved by BookingsDate");
   	 rs.setData(bookings);
   	 return new ResponseEntity<ResponseStructure<List<Bookings>>>(rs,HttpStatus.OK);
    }
    else
   	 throw new IdNotFoundException("Invalid,Bookings Date not Found");
}


public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingsDetailsByStatus(Bookingstatus status) {
	ResponseStructure<List<Bookings>> rs=new ResponseStructure<>();
    List<Bookings> bookings =bdao.getBookingsDetailsByStatus(status);
    if(!bookings.isEmpty()) {
   	 rs.setStatusCode(HttpStatus.OK.value());
   	 rs.setMessage("Booking details are retrieved by BookingStatus");
   	 rs.setData(bookings);
   	 return new ResponseEntity<ResponseStructure<List<Bookings>>>(rs,HttpStatus.OK);
    }
    else
   	 throw new IdNotFoundException("Invalid,Bookings status not Found");
}


public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsbyBookingId(Integer bookingId) {

	ResponseStructure<Payment> rs=new ResponseStructure<>();
    Optional<Payment> payment =bdao.getPaymentDetailsbyBookingId(bookingId);
    if(payment.isPresent()) {
   	 rs.setStatusCode(HttpStatus.OK.value());
   	 rs.setMessage("Payment details are retrieved by BookingsId");
   	 rs.setData(payment.get());
   	 return new ResponseEntity<>(rs,HttpStatus.OK);
    }
    else
   	 throw new IdNotFoundException("Invalid,Bookings Id not Found");
}


public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingDetailsbyFlightId(Integer flightId) {
	ResponseStructure<List<Bookings>> rs=new ResponseStructure<>();
    List<Bookings> bookings =bdao.getBookingDetailsbyFlightId(flightId);
    if(!bookings.isEmpty()) {
   	 rs.setStatusCode(HttpStatus.OK.value());
   	 rs.setMessage("Booking details are retrieved by flightId");
   	 rs.setData(bookings);
   	 return new ResponseEntity<ResponseStructure<List<Bookings>>>(rs,HttpStatus.OK);
    }
    else
   	 throw new IdNotFoundException("Invalid,Flight Details not Found");
}






public ResponseEntity<ResponseStructure<Bookings>> updateBookingStatus(Integer id, Bookingstatus status) {
	         Optional<Bookings> opt = bdao.getBookingsById(id);
	     	ResponseStructure<Bookings> rs=new ResponseStructure<>();

	         if(opt.isPresent()) {
	        	   Bookings booking = opt.get();
	        	 booking.setStatus(status);
	        	 rs.setStatusCode(HttpStatus.OK.value());
	           	 rs.setMessage("Booking details are retrieved by flightId");
	           	 rs.setData(bdao.createBooking(booking));
	           	 return new ResponseEntity<ResponseStructure<Bookings>>(rs,HttpStatus.OK);
	            }
	            else
	           	 throw new IdNotFoundException("Invalid,Booking Details not Found");
}}


	
	



package com.flight_booking_system.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.flight_booking_system.Controller.Flight_Controller;
import com.flight_booking_system.DAO.Booking_Dao;
import com.flight_booking_system.DAO.Flight_Dao;
import com.flight_booking_system.DAO.Passenger_Dao;
import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Bookings;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Exception.IdNotFoundException;
import com.flight_booking_system.Exception.NoRecordFoundException;
import com.flight_booking_system.Exception.NoSeatAvailableException;

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
		ResponseStructure<Bookings> rs=new ResponseStructure<Bookings>();
		  List<Passenger> passengers=pdao.getallPassenger();
		
		          Optional<Flight> opt=fdao.getFlightById(booking.getFlight().getId());
		          if(opt.isPresent()) {
		        	  Flight flight=opt.get();
		        	     Integer size=0;
                            for(Passenger p:passengers) {
                            	if(p.getBooking().getFlight().getId().equals(flight.getId()))
                            		size++;
                            }
		        	  if(size+booking.getPassengers().size()<=flight.getTotalSeats()){
		        	   
		        	  Double totalPrice=flight.getPrice()*booking.getPassengers().size();
		        	  
		        	     for(Passenger existsPassenger:passengers) {
		        		for(Passenger p:booking.getPassengers()) {
		        			//to avoid seat exists
		        	    	if(! existsPassenger.getSeatNumber().equals(p.getSeatNumber()))
		        			   p.setBooking(booking);
		        			else
		        				throw new NoSeatAvailableException(p.getSeatNumber()+ "Seat are already booked");
		        	  }
		        	     }
				        Payment payment= booking.getPayment();
		        	    payment.setBooking(booking);
		        	    payment.setAmount(totalPrice);
	        		    booking.setFlight(flight);
		        	  rs.setStatusCode(HttpStatus.OK.value());
		        	  rs.setData(bdao.createBooking(booking));

		        	  rs.setMessage("Booking successfully done");
		        	  return new  ResponseEntity<ResponseStructure<Bookings>>(rs,HttpStatus.OK);
		          }
		        	  else
			        	  throw new NoSeatAvailableException("No Seats are available");
		          }
		          else
		        	  throw new NoRecordFoundException("Flight Details Not Found");


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
 	    	 rs.setData(bdao.createBooking(booking));
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
	// TODO Auto-generated method stub
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


	
	



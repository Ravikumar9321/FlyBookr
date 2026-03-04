package com.flight_booking_system.DAO;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.Entity.Bookings;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Exception.NoSeatAvailableException;
import com.flight_booking_system.Repository.Booking_Repository;



@Repository
public class Booking_Dao {
	@Autowired
	private Booking_Repository br;

	public Bookings createBooking(Bookings booking) {
		 if(booking.getPassengers()==null) 
   		  throw new NullPointerException("Passenger Details Not Found");
   	  if(booking.getPayment()==null)
   		  throw  new NullPointerException("Payment is Not Done");
		return br.save(booking);
	}
	
	public List<Bookings> getallBookings() {
		return br.findAll();
	}

	public Optional<Bookings> getBookingsById(int id) {
		// TODO Auto-generated method stub
		return br.findById(id);
	}
	
	public void deleteBookings(Bookings Bookings) {
	       br.delete(Bookings);
	}
	
	public Page<Bookings> getBookingsByPagenatio_Sort(int pageNumber, int pageSize, String field) {
		return br.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
	}

	public List<Passenger> getallPassengersByBookingsId(Integer id) {
		// TODO Auto-generated method stub
		return br.getAllPassengerByBookingId(id);
	}

	public List<Bookings> getBookingsDetailsByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return br.getAllBookingDeatilsByDate(date);
	}

	public List<Bookings> getBookingsDetailsByStatus(Bookingstatus status) {
		// TODO Auto-generated method stub
		return br.getAllBookingDeatilsByStatus(status);
	}

	public Optional<Payment> getPaymentDetailsbyBookingId(Integer bookingId) {
		// TODO Auto-generated method stub
		return br.getPaymentByBookingId(bookingId);
	}

	public List<Bookings> getBookingDetailsbyFlightId(Integer flightId) {
		// TODO Auto-generated method stub
		return br.getAllBookingDeatilsByFlightId(flightId);
	}

	

}

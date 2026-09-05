package com.flight_booking_system.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.Entity.Bookings;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Entity.Payment;

public interface Booking_Repository  extends JpaRepository<Bookings, Integer>{

	@Query("select b.passengers from Bookings b where b.id=?1")
	List<Passenger> getAllPassengerByBookingId(Integer id);
	
	@Query("select b from Bookings b where b.bookingDate=?1")
	List<Bookings> getAllBookingDeatilsByDate(LocalDate date);
	
	@Query("select b from Bookings b where b.status=?1")
	List<Bookings> getAllBookingDeatilsByStatus(Bookingstatus status);
	
	@Query("select f.bookings from Flight f where f.id=?1")
	List<Bookings> getAllBookingDeatilsByFlightId(Integer id);
	
	@Query("select b.payment from Bookings b where b.id=?1")
	Optional<Payment> getPaymentByBookingId(Integer id);
	
	
}

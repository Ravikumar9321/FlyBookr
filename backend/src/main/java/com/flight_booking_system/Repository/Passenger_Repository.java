package com.flight_booking_system.Repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flight_booking_system.Entity.Passenger;

public interface Passenger_Repository extends JpaRepository<Passenger, Integer> {
	
	@Query("select p from Passenger p where p.contactNumber=?1")
	Optional<Passenger> getPassengerDetailsByContactNumber(String contact);

	@Query("select p from Passenger p where p.booking.flight.id=?1")
	List<Passenger> findByFlightId(Integer flightId);
	
	
	

}

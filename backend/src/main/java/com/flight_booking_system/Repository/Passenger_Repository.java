package com.flight_booking_system.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Entity.Passenger;

public interface Passenger_Repository extends JpaRepository<Passenger, Integer> {
	
	@Query("select p from Passenger p where p.contactNumber=?1")
	Optional<Passenger> getPassengerDetailsByContactNumber(String contact);
	

}

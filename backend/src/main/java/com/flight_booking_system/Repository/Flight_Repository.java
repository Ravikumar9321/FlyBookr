package com.flight_booking_system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flight_booking_system.Entity.Flight;

public interface Flight_Repository extends JpaRepository<Flight, Integer> {
	@Query("select f from Flight f where f.airlineName=?1")
	List< Flight> getFlightDetailsByAirlineName(String airlineName);
	
	@Query("select f from Flight f where f.source=?1 and f.destination=?2")
	List< Flight> getFlightDetailsBysourceAndDestionation(String source,String destination);
	
	@Query("select f from Flight f where f.price>=?1")
	List< Flight> getFlightDetailsByPrice(String price);


}

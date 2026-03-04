package com.flight_booking_system.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Repository.Flight_Repository;

@Repository
public class Flight_Dao {
	@Autowired
	private Flight_Repository fr;

//i)save Flight
	public Flight saveFlight(Flight flight) {
		return fr.save(flight);
	}
	public List<Flight> saveAllFlightDetails(List<Flight> flight) {
		// TODO Auto-generated method stub
		return fr.saveAll(flight);
	}
	
//ii)fetch all Flight details
	public List<Flight> getallFlight() {
		return fr.findAll();
	}

	//iii)get Flight details by id
	public Optional<Flight> getFlightById(int id) {
		// TODO Auto-generated method stub
		return fr.findById(id);
	}
	
	//v)delete Flight details
	public void deleteFlight(Flight flight) {
	       fr.delete(flight);
	}
	
	//vi)get Details in pagenaton ,sort format
	public Page<Flight> getFlightByPagenatio_Sort(int pageNumber, int pageSize, String field) {
		return fr.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
	}
	public List<Flight> getFlightDetailsByAirlineName(String airlineName) {
		// TODO Auto-generated method stub
		return fr.getFlightDetailsByAirlineName(airlineName);
	}
	public List<Flight> getFlightDetailsBysourceAndDestionation(String source, String destination) {
		// TODO Auto-generated method stub
		return fr.getFlightDetailsBysourceAndDestionation(source, destination);
	}
	public List<Flight> getFlightDetailsByPrice(String price) {
		// TODO Auto-generated method stub
		return fr.getFlightDetailsByPrice(price);
	}
	
}

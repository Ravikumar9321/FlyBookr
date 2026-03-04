package com.flight_booking_system.DAO;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Repository.Passenger_Repository;


@Repository
public class Passenger_Dao {
	@Autowired
	private Passenger_Repository pr;

//i)save Passenger
	public Passenger savePassenger(Passenger Passenger) {
		return pr .save(Passenger);
	}
//ii)fetch all Passenger details
	public List<Passenger> getallPassenger() {
		return pr .findAll();
	}

	//iii)get Passenger details by id
	public Optional<Passenger> getPassengerById(int id) {
		// TODO Auto-generated method stub
		return pr .findById(id);
	}
	
	//v)delete Passenger details
	public void deletePassenger(Passenger Passenger) {
	       pr .delete(Passenger);
	}
	
	//vi)get Details in pagenaton ,sort format
	public Page<Passenger> getPassengerByPagenatio_Sort(int pageNumber, int pageSize, String field) {
		return pr .findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
	}
	public Optional<Passenger> getPassengerDetailsByContactNumber(String contact) {
		// TODO Auto-generated method stub
		return pr.getPassengerDetailsByContactNumber(contact);
	}
	
}

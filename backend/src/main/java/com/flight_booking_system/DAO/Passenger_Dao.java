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
  private	Passenger_Repository  passengerRepo;

//i)save Passenger
	public Passenger savePassenger(Passenger Passenger) {
		return passengerRepo .save(Passenger);
	}
//ii)fetch all Passenger details
	public List<Passenger> getallPassenger() {
		return passengerRepo .findAll();
	}

	//iii)get Passenger details by id
	public Optional<Passenger> getPassengerById(int id) {
		return passengerRepo .findById(id);
	}
	//iv)update Passenger
		public Passenger updatePassenger(Passenger Passenger) {
			return passengerRepo .save(Passenger);
		}
	//v)delete Passenger details
	public void deletePassenger(Passenger Passenger) {
	       passengerRepo .delete(Passenger);
	}
	
	//vi)get Details in pagenaton ,sort format
	public Page<Passenger> getPassengerByPagenatio_Sort(int pageNumber, int pageSize, String field) {
		return passengerRepo .findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
	}
	public Optional<Passenger> getPassengerDetailsByContactNumber(String contact) {
		return passengerRepo.getPassengerDetailsByContactNumber(contact);
	}
	public List<Passenger> findByFlightId(Integer flightId) {
		return passengerRepo.findByFlightId(flightId);
	}
	
}

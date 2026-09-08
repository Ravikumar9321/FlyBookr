package com.flight_booking_system.Entity;

import java.time.LocalDate
;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.flight_booking_system.DTO.Bookingstatus;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Bookings {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	private LocalDate  bookingDate;
	
	@Enumerated(EnumType.STRING)
	private Bookingstatus status;
	@JoinColumn
	@ManyToOne
	private Flight flight;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "booking")
	private List<Passenger> passengers;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "booking")
	private Payment payment;
	
	
	
}

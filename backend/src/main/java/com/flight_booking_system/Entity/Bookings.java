package com.flight_booking_system.Entity;

import java.time.LocalDate
;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flight_booking_system.DTO.Bookingstatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;

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

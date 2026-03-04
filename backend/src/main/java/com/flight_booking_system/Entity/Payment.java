package com.flight_booking_system.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.PaymentMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	private LocalDate paymentDate;
	private Double amount;
	@Enumerated(EnumType.STRING)
	private PaymentMode mode;
	@Enumerated(EnumType.STRING)
	private Bookingstatus status;
	
	
	@JsonIgnore
	@OneToOne
	@JoinColumn
	private Bookings booking;
}

package com.flight_booking_system.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flight_booking_system.DTO.*;

import jakarta.persistence.*;

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

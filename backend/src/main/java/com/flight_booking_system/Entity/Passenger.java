package com.flight_booking_system.Entity;

import java.awt.print.Book;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Passenger {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Integer age;
  private String gender;
  private String seatNumber;
  private String contactNumber;
  
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name="booking_id")
  private Bookings booking;
	 
}

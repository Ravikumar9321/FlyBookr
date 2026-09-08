package com.flight_booking_system.Entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;


import lombok.Data;

@Entity
@Data
public class Passenger {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    
	    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
	    private String name;

	    @Min(value = 1, message = "Age must be greater than 0")
	    @Max(value = 120, message = "Age must be less than 120")
	    private Integer age;

	    private String gender;
        
	    private String seatNumber;

	    @Pattern(regexp = "\\d{10}", message = "Contact number must be 10 digits")
	    private String contactNumber;
  
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name="booking_id")
  private Bookings booking;
	 
}

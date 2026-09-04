package com.flight_booking_system.Entity;

import java.awt.print.Book;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

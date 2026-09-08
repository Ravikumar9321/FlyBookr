package com.flight_booking_system.ControllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight_booking_system.Controller.Booking_Controller;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Bookings;
import com.flight_booking_system.Exception.*;
import com.flight_booking_system.Service.Booking_Service;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(Booking_Controller.class)
public class BookingControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private Booking_Service service;
    
    @Nested
   class CreateTests{
    	
    	@Test
    	public void shouldCreateBookingSuccessfully() throws Exception{
    		Bookings booking=new Bookings();
    		  booking.setId(1);
    		 
    		ResponseStructure<Bookings> response=new ResponseStructure<Bookings>();
    		response.setData(booking);
    		response.setMessage("Created Successfully");
    		response.setStatusCode(201);
    		
    		when(service.createBooking(booking))
    		      .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));
    		
    		mockMvc.perform(post("/api/booking")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(objectMapper.writeValueAsString(booking)))
    		      .andExpectAll
    		      (
    		    	   status().isCreated(),
    		           jsonPath("$.data.id").value(1),
    		          jsonPath("$.statusCode").value(201),
    		          jsonPath("$.message").value("Created Successfully")
    		      );
    	}
    	
    	@Test
    	public void shouldReturnBadRequestWhenBookingIsInvalid() throws Exception{
    		Bookings booking=new Bookings();
    		 when(service.createBooking(any(Bookings.class)))
    		        .thenThrow(new NullPointerException("Invalid input"));
    		 
    		 mockMvc.perform(post("/api/booking")
    				 .contentType(MediaType.APPLICATION_JSON)
    				 .content(objectMapper.writeValueAsString(booking)))
    		     .andExpectAll
    		     (
    		    		 status().isBadRequest(),
    		             jsonPath("$.statusCode").value(400),
    		             jsonPath("$.message").value("FAILURE"),
    		             jsonPath("$.data").value("Invalid input")
    		      );
    	}
    }
    
    @Nested
    class ReadTests
          {
    	  @Test
    	  public void  shouldFetchAllBookingsSuccessfully() throws Exception{
    		  List<Bookings> list = Arrays.asList(new Bookings(),new Bookings());
    		  ResponseStructure<List<Bookings>> response=new ResponseStructure<List<Bookings>>();
    		  response.setData(list);
    		  response.setMessage("Retrived Successfully");
    		  response.setStatusCode(200);
    		  
    		  when(service.fetchallBookings())
    		        .thenReturn(ResponseEntity.ok(response));
    		  mockMvc.perform(get("/api/booking")
    				  .contentType(MediaType.APPLICATION_JSON)
    				  .content(objectMapper.writeValueAsString(list)))
    		          .andExpectAll
    		          (
    		        		  status().isOk(),
    		        		  jsonPath("$.statusCode").value(200),
    		        		  jsonPath("$.data.length()").value(2)
    		          );
    		          
    	  }
    	  
    	  @Test
    	  public void shouldThrowNoRecordFoundExceptionWhenNoBookings() throws Exception{
    		  when(service.fetchallBookings())
              .thenThrow(new NoRecordFoundException("Booking not exist"));

          mockMvc.perform(get("/api/booking"))
              .andExpectAll
              (
            		  status().isNotFound(),
            		  jsonPath("$.statusCode").value(404),
            		  jsonPath("$.message").value("FAILURE"),
            		  jsonPath("$.data").value("Booking not exist")
              );
              
      }
    	  
    	  @Test
    	  public void shouldFetchBookingByIdSuccessfully() throws Exception{
    		  Bookings booking=new Bookings();
    		  booking.setId(1);
    		  
    		 ResponseStructure<Bookings> response=new ResponseStructure<Bookings>();
      		response.setData(booking);
      		response.setMessage("Booking Retrieved");
      		response.setStatusCode(200);
      		
      		when(service.fetchBookingsById(1))
      		    .thenReturn(ResponseEntity.ok(response));
      		
      		mockMvc.perform(get("/api/booking/1")
      				.contentType(MediaType.APPLICATION_JSON)
      				.content(objectMapper.writeValueAsString(booking)))
      		.andExpectAll
            (
          		  status().isOk(),
          		  jsonPath("$.statusCode").value(200),
          		  jsonPath("$.message").value("Booking Retrieved"),
          		  jsonPath("$.data.id").value(1)
            );
            
    	  }
    	  
    	  @Test
    	  public void shouldThrowIdNotFoundExceptionWhenBookingNotExist() throws Exception{
    		  when(service.fetchBookingsById(99))
    		            .thenThrow(new IdNotFoundException("Booking not found"));
    		  
    		  mockMvc.perform(get("/api/booking/99"))
    		      .andExpect(status().isNotFound())
    		      .andExpect(jsonPath("$.data").value("Booking not found"))
  		          .andExpect(jsonPath("$.statusCode").value(404));
    
    	  }
    }
    
    @Nested
    class UpdateTests{
    	
    	@Test
    	public void shouldUpdatebookingSuccessfully()  throws Exception
    	{
    		  Bookings booking=new Bookings();
    		  booking.setId(1);
    		  ResponseStructure<Bookings> response=new ResponseStructure<Bookings>();
    		  response.setData(booking);
    		  response.setStatusCode(200);
    		  response.setMessage("Updated successfully");
    		  when(service.updateBookings(booking))
    		        .thenReturn(ResponseEntity.ok(response));
    		  mockMvc.perform(put("/api/booking")
    				  .contentType(MediaType.APPLICATION_JSON)
    				  .content(objectMapper.writeValueAsString(booking)))
    		          .andExpectAll
    		          (
    		        		  status().isOk(),
    		        		  jsonPath("$.data.id").value(1),
    		        		  jsonPath("$.message").value("Updated successfully"),
    		        		  jsonPath("$.statusCode").value(200)
    		          );
    	}
    	
    	@Test
    	public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingBooking() throws Exception
    	{
    		Bookings booking=new Bookings();
    		booking.setId(1);
    	   when(service.updateBookings(booking))
    	                .thenThrow(new IdNotFoundException("Booking ID not found"));
    	   mockMvc.perform(put("/api/booking")
    			   .contentType(MediaType.APPLICATION_JSON)
    			   .content(objectMapper.writeValueAsString(booking)))
    	           .andExpect(status().isNotFound())
    	           .andExpect(jsonPath("$.data").value("Booking ID not found"))
       		       .andExpect(jsonPath("$.statusCode").value(404));

    	           
    	}
    }
    
    @Nested
    class DeleteTests 
    {
    	@Test
    	public void shouldDeleteBookingSuccessfully() throws Exception
    	{
    		Bookings booking=new Bookings();
    		booking.setId(1);
    		  ResponseStructure<String> response = new ResponseStructure<>();
              response.setMessage("Deleted Success");
              response.setStatusCode(200);
              response.setData("SUCCESS");
    		when(service.deleteBookings(1))
    		     .thenReturn(ResponseEntity.ok(response));
    		mockMvc.perform(delete("/api/booking/1"))
    		     .andExpect(status().isOk())
    		     .andExpect(jsonPath("$.data").value("SUCCESS"))
    		     .andExpect(jsonPath("$.statusCode").value(200));
    		     
    	}
    	@Test
    	public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingBooking() throws Exception
    	{
    		when(service.deleteBookings(99))
    		    .thenThrow(new IdNotFoundException("Booking ID not found"));
    		mockMvc.perform(delete("/api/booking/99"))
    		    .andExpect(status().isNotFound())
    		    .andExpect(jsonPath("$.message").value("FAILURE"))
    		    .andExpect(jsonPath("$.statusCode").value(404));
    	}
    }
    
    @Nested
    class PaginationTests
    {
    	@Test
    	public void shouldFetchBookingByPaginationSuccessfully() throws Exception
    	{
    		Bookings b1=new Bookings();
    		b1.setId(1);
    		Bookings b2=new Bookings();
    		b2.setId(2);
    		
    		Page<Bookings> page=new PageImpl<Bookings>(Arrays.asList(b1,b2));
    		ResponseStructure<Page<Bookings>> response=new ResponseStructure<Page<Bookings>>();
    		response.setData(page);
    		response.setMessage("Booking Retrieved by pagination");
    		response.setStatusCode(200);
    		
    		when(service.getBookingsByPagination_Sort(0, 2, "id"))
    		      .thenReturn(ResponseEntity.ok(response));
    		mockMvc.perform(get("/api/booking/0/2/id")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(objectMapper.writeValueAsString(page)))
    		       .andExpect(status().isOk())
    		       .andExpect(jsonPath("$.statusCode").value(200))
    		       .andExpect(jsonPath("$.message").value("Booking Retrieved by pagination"));
    		
    	}
    	@Test
    	public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty() throws Exception
    	{
    		when(service.getBookingsByPagination_Sort(0, 5, "id"))
    		    .thenThrow(new NoRecordFoundException("No records found"));
    		
    		mockMvc.perform(get("/api/booking/0/5/id"))
    		  .andExpect(status().isNotFound())
    		  .andExpect(jsonPath("$.statusCode").value(404))
    		  .andExpect(jsonPath("$.data").value("No records found"));
    	}
    }
    
}

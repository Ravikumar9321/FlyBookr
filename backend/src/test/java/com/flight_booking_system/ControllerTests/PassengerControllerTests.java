package com.flight_booking_system.ControllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight_booking_system.Controller.Passenger_Controller;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Exception.*;
import com.flight_booking_system.Service.Passenger_Service;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(Passenger_Controller.class)
public class PassengerControllerTests {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private Passenger_Service service;
	
	@Nested
	class CreateTests{
		@Test
		public void shouldSavePassengerSuccessfully() throws Exception{
			Passenger passenger=new Passenger();
			passenger.setId(1);
			passenger.setContactNumber("9047856541");
			
			ResponseStructure<Passenger> response=new ResponseStructure<Passenger>();
			response.setData(passenger);
			response.setMessage("Saved successfully");
			response.setStatusCode(201);
			when(service.savePassenger(any(Passenger.class)))
		    .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

			
			mockMvc.perform(post("/api/passenger")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(passenger)))
			       .andExpect(status().isCreated())
			       .andExpect(jsonPath("$.statusCode").value(201))
			       .andExpect(jsonPath("$.message").value("Saved successfully"))
                   .andExpect(jsonPath("$.data.id").value(1));       
		}
		
		@Test
		public void shouldReturnBadRequestWhenPassengerIsInvalid() throws Exception{
			Passenger passenger = new Passenger();
			
            when(service.savePassenger(any(Passenger.class)))
                .thenThrow(new NullPointerException("Invalid input"));

            mockMvc.perform(post("/api/passenger")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(passenger)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.message").value("FAILURE"))
                    .andExpect(jsonPath("$.data").value("Invalid input"));
        
		}
	}
	
	@Nested
	class ReadTests{
		@Test
		public void shouldFetchAllPassengerSuccessfully() throws Exception{
			  List<Passenger> list = Arrays.asList(new Passenger(),new Passenger());
			ResponseStructure<List<Passenger>> response=new ResponseStructure<>();
			response.setData(list);
			response.setMessage("Passenger Retrieved");
			response.setStatusCode(200);
			
			when(service.fetchallPassenger())
			       .thenReturn(ResponseEntity.ok(response));
			mockMvc.perform(get("/api/passenger")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(list)))
			       .andExpect(status().isOk())
			       .andExpect(jsonPath("$.statusCode").value(200))
			       .andExpect(jsonPath("$.data.length()").value(2));
		}
		@Test
		public void shouldThrowNoRecordFoundExceptionWhenPassengersNotExists() throws Exception{
			when(service.fetchallPassenger())
			      .thenThrow(new NoRecordFoundException("Passenger not found"));
			mockMvc.perform(get("/api/passenger"))
			       .andExpect(status().isNotFound())
			       .andExpect(jsonPath("$.data").value("Passenger not found"))
			       .andExpect(jsonPath("$.message").value("FAILURE"));
		}
		@Test
		public void shouldFetchPassengerByIdSuccesfully() throws Exception{
			Passenger passenger=new Passenger();
			passenger.setId(1);
			ResponseStructure<Passenger> response=new ResponseStructure<>();
			response.setData(passenger);
			response.setMessage("Passenger Retrieved");
			response.setStatusCode(200);
			
			when(service.fetchPassengerById(1))
			     .thenReturn(ResponseEntity.ok(response));
			
			mockMvc.perform(get("/api/passenger/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(passenger)))
			        .andExpectAll
			        (
			        		status().isOk(),
			        		jsonPath("$.data.id").value(1),
			        		jsonPath("$.message").value("Passenger Retrieved"),
			        		jsonPath("$.statusCode").value(200)
			        );
		}
		@Test
		public void shouldThrowIdNotFoundExceptionWhenPassengerNotExist() throws Exception
		{
			when(service.fetchPassengerById(99))
			       .thenThrow(new IdNotFoundException("Passenger not found"));
			
			mockMvc.perform(get("/api/passenger/99"))
			        .andExpect(status().isNotFound())
			        .andExpect(jsonPath("$.data").value("Passenger not found"))
			        .andExpect(jsonPath("$.message").value("FAILURE"));
		}
	}
	
	@Nested
	class UpdateTests{
		
		@Test
		public void shouldUpdatePassengerSuccessfully() throws Exception{
			Passenger passenger=new Passenger();
			passenger.setId(1);
			passenger.setName("Rocky");
			ResponseStructure<Passenger> response=new ResponseStructure<Passenger>();
			response.setData(passenger);
			response.setMessage("Passenger Updated");
			response.setStatusCode(200);
			
			when(service.updatePassenger(passenger))
			        .thenReturn(ResponseEntity.ok(response));
			mockMvc.perform(put("/api/passenger")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(passenger)))
			       .andExpectAll
			       (
			         status().isOk(),
			         jsonPath("$.data.id").value(1),
			         jsonPath("$.data.name").value("Rocky"),
			         jsonPath("$.statusCode").value(200)	   
			       );
			       
			}
		@Test
		public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingPassenger() throws Exception{
			   Passenger passenger=new Passenger();
			   passenger.setId(99);
			   when(service.updatePassenger(passenger))
			        .thenThrow(new IdNotFoundException("Passenger ID not found"));
			   mockMvc.perform(put("/api/passenger")
					   .contentType(MediaType.APPLICATION_JSON)
					   .content(objectMapper.writeValueAsString(passenger)))
			   .andExpectAll
               (
                  status().isNotFound(),
                  jsonPath("$.statusCode").value(404),
                  jsonPath("$.message").value("FAILURE"),
                  jsonPath("$.data").value("Passenger ID not found")
               );
		}
	}
	
	@Nested
	 class DeleteTests{
		
		@Test
		public void shouldDeletePassengerSuccessfully() throws Exception
		{
			Passenger passenger=new Passenger();
			passenger.setId(1);
			ResponseStructure<String> response=new ResponseStructure<String>();
			response.setData("SUCCESS");
			response.setMessage("Deleted Successfully");
			response.setStatusCode(200);
			
			when(service.deletePassenger(1))
			      .thenReturn(ResponseEntity.ok(response));
			
			mockMvc.perform(delete("/api/passenger/1"))
			       .andExpect(status().isOk())
			       .andExpect(jsonPath("$.data").value("SUCCESS"))
			       .andExpect(jsonPath("$.message").value("Deleted Successfully"));
		}
		@Test
		public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingPassenger()throws Exception{
			when(service.deletePassenger(99))
			     .thenThrow(new IdNotFoundException("Passsenger ID not found"));
			
			mockMvc.perform(delete("/api/passenger/99"))
			      .andExpect(status().isNotFound())
			      .andExpect(jsonPath("$.message").value("FAILURE"))
			      .andExpect(jsonPath("$.data").value("Passsenger ID not found"));
		}
		
	}
	@Nested
	class PaginationTests
	{
		@Test
		public void shouldFetchPassengerByPaginationSuccessfully() throws Exception
		{
			Passenger p1=new Passenger();
			p1.setId(1);
			Passenger p2=new Passenger();
			p2.setId(2);
			  Page<Passenger> page=new PageImpl<Passenger>(Arrays.asList(p1,p2));
			  ResponseStructure<Page<Passenger>> response=new ResponseStructure<Page<Passenger>>();
			  response.setData(page);
			  response.setMessage("Passenger Details Retrived By Pagination");
			  response.setStatusCode(200);
			  
			  when(service.getPassengerByPagination_Sort(0, 2, "id"))
			       .thenReturn(ResponseEntity.ok(response));
			  
			  mockMvc.perform(get("/api/passenger/0/2/id")
					  .contentType(MediaType.APPLICATION_JSON)
					  .content(objectMapper.writeValueAsString(page)))
			          .andExpect(status().isOk())
			          .andExpect(jsonPath("$.statusCode").value(200))
			          .andExpect(jsonPath("$.message").value("Passenger Details Retrived By Pagination"));
			  
		}
		@Test
		public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty() throws Exception
		{
			when(service.getPassengerByPagination_Sort(0, 5,"id"))
			    .thenThrow(new NoRecordFoundException("No records found"));
			
			mockMvc.perform(get("/api/passenger/0/5/id"))
			     .andExpect(status().isNotFound())
			     .andExpect(jsonPath("$.statusCode").value(404))
			     .andExpect(jsonPath("$.data").value("No records found"));
		}
	}
}

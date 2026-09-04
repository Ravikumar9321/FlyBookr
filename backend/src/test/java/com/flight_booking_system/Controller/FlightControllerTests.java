package com.flight_booking_system.Controller;

import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Exception.*;
import com.flight_booking_system.Service.Flight_Service;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(Flight_Controller.class)
public class FlightControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private Flight_Service service;

   
    @Nested
    class CreateTests {
        @Test
        public void shouldSaveFlightSuccessfully() throws Exception {
            Flight flight = new Flight(); 
            flight.setId(1);
            flight.setPrice(100.0);
            flight.setTotalSeats(10);
            flight.setAirlineName("Indigo");
            flight.setSource("Chennai");
            flight.setDestination("Delhi");
            ResponseStructure<Flight> response = new ResponseStructure<>();
            response.setData(flight);
            response.setMessage("Saved successfully");
            response.setStatusCode(201);

            when(service.saveFlight(any(Flight.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

            mockMvc.perform(post("/api/flight")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(flight)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.message").value("Saved successfully"));
        }

        @Test
        public void shouldReturnBadRequestWhenFlightIsInvalid() throws Exception {
            Flight flight = new Flight();
            flight.setId(1);
            flight.setPrice(100.0);
            flight.setTotalSeats(10);
            flight.setAirlineName("Indigo");
            flight.setSource("Chennai");
            flight.setDestination("Delhi");
            when(service.saveFlight(any(Flight.class)))
                .thenThrow(new NullPointerException("Invalid input"));

            mockMvc.perform(post("/api/flight")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(flight)))
                    .andExpect(status().isBadRequest())
                  .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.message").value("FAILURE"))
                    .andExpect(jsonPath("$.data").value("Invalid input"));
        }
    }

  
    @Nested
    class ReadTests {
        @Test
        public void shouldFetchFlightByIdSuccessfully() throws Exception {
            Flight flight = new Flight(); flight.setId(1);
            ResponseStructure<Flight> response = new ResponseStructure<>();
            response.setData(flight);
            response.setMessage("Flight Retrieved");
            response.setStatusCode(200);

            when(service.fetchFlightById(1)).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(get("/api/flight/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.message").value("Flight Retrieved"));
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenFlightNotExists() throws Exception {
            when(service.fetchFlightById(99))
                .thenThrow(new IdNotFoundException("Flight ID not found"));

            mockMvc.perform(get("/api/flight/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("FAILURE"))
                .andExpect(jsonPath("$.data").value("Flight ID not found"));
        }

        @Test
        public void shouldFetchAllFlightsSuccessfully() throws Exception {
            List<Flight> list = Arrays.asList(new Flight(), new Flight());
            ResponseStructure<List<Flight>> response = new ResponseStructure<>();
            response.setData(list);
            response.setMessage("Flight Details Retrieved");
            response.setStatusCode(200);

            when(service.fetchallFlight()).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(get("/api/flight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.message").value("Flight Details Retrieved"));
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenFlightsNotExists() throws Exception {
            when(service.fetchallFlight())
                .thenThrow(new NoRecordFoundException("Flight not exist"));

            mockMvc.perform(get("/api/flight"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("FAILURE"))
                .andExpect(jsonPath("$.data").value("Flight not exist"));
        }
    }

   
    @Nested
    class UpdateTests {
        @Test
        public void shouldUpdateFlightSuccessfully() throws Exception {
            Flight flight = new Flight(); flight.setId(1);
            ResponseStructure<Flight> response = new ResponseStructure<>();
            response.setStatusCode(200);
            response.setMessage("Flight Updated");
            response.setData(flight);

            when(service.updateFlight(any(Flight.class))).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(put("/api/flight")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(flight)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(1));
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingFlight() throws Exception {
            Flight flight = new Flight();
            flight.setId(99);
            when(service.updateFlight(any(Flight.class)))
                .thenThrow(new IdNotFoundException("Flight ID not found"));

            mockMvc.perform(put("/api/flight")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(flight)))
                    .andExpectAll
                    (
                       status().isNotFound(),
                       jsonPath("$.statusCode").value(404),
                       jsonPath("$.message").value("FAILURE"),
                       jsonPath("$.data").value("Flight ID not found")
                    );
        }
    }

    @Nested
    class DeleteTests {
        @Test
        public void shouldDeleteFlightSuccessfully() throws Exception {
            ResponseStructure<String> response = new ResponseStructure<>();
            response.setMessage("Deleted Success");
            response.setStatusCode(200);
            response.setData("SUCCESS");

            when(service.deleteFlight(1)).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(delete("/api/flight/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("Deleted Success"));
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingFlight() throws Exception {
            when(service.deleteFlight(99))
                .thenThrow(new IdNotFoundException("Flight ID not found"));

            mockMvc.perform(delete("/api/flight/99"))
            .andExpectAll
            (
               status().isNotFound(),
               jsonPath("$.statusCode").value(404),
               jsonPath("$.message").value("FAILURE"),
               jsonPath("$.data").value("Flight ID not found")
            );
        }
    }
    
    @Nested
    class PaginationTests
    {
    
    	  @Test
    	  public void shouldFetchFlightsByPaginationSuccessfully() throws Exception
    	  {
    		  Flight f1=new Flight();
    		  f1.setId(1);
    		  Flight f2=new Flight();
    		  f2.setId(2);
    		    Page<Flight> page =new  PageImpl<>(Arrays.asList(f1,f2));
    		    ResponseStructure<Page<Flight>> response=new ResponseStructure<Page<Flight>>();
    		    response.setData(page);
    		    response.setMessage("Flights Retrieved by Pagination");
    		    response.setStatusCode(200);
    		    when(service.getFlightByPagination_Sort(0, 2, "id"))
    		            .thenReturn(ResponseEntity.ok(response));
    		    mockMvc.perform(get("/api/flight/0/2/id")
    		    		.contentType(MediaType.APPLICATION_JSON)
    		    		.content(objectMapper.writeValueAsString(page)))
    		         .andExpect(status().isOk())
    		         .andExpect(jsonPath("$.statusCode").value(200))
    		         .andExpect(jsonPath("$.data.content.length()").value(2))
    		         .andExpect(jsonPath("$.message").value("Flights Retrieved by Pagination"));
    	  }
    	  
    	  @Test
    	  public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty()throws Exception
    	  {
    		  when(service.getFlightByPagination_Sort(0, 4, "id"))
    		        .thenThrow(new NoRecordFoundException("No records found"));
    		  mockMvc.perform(get("/api/flight/0/4/id"))
    		    .andExpect(status().isNotFound())
    		    .andExpect(jsonPath("$.statusCode").value(404))
    		    .andExpect(jsonPath("$.data").value("No records found"));
    		  
    	  }
    }
    
}
package com.flight_booking_system.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.flight_booking_system.DAO.Flight_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Exception.*;

public class FlightServiceTests {

    @InjectMocks
    private Flight_Service service;

    @Mock
    private Flight_Dao flightdao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------- CREATE --------------------
    @Nested
    class CreateTests {
        @Test
        public void shouldSaveFlight() {
            Flight flight = new Flight();
            flight.setId(1);
            flight.setDestination("Delhi");

            when(flightdao.saveFlight(flight)).thenReturn(flight);

            ResponseEntity<ResponseStructure<Flight>> responseEntity = service.saveFlight(flight);
            Flight savedFlight = responseEntity.getBody().getData();

            assertEquals("Delhi", savedFlight.getDestination());
            verify(flightdao, times(1)).saveFlight(flight);
        }

        @Test
        public void shouldReturnNullDataWhenSaveFails() {
            Flight flight = new Flight();
            when(flightdao.saveFlight(flight)).thenReturn(null);

            ResponseEntity<ResponseStructure<Flight>> responseEntity = service.saveFlight(flight);

            assertNull(responseEntity.getBody().getData());
            verify(flightdao, times(1)).saveFlight(flight);
        }
    }

    // -------------------- READ --------------------
    @Nested
    class ReadTests {
        @Test
        public void shouldFindFlightById() {
            Flight flight = new Flight(); flight.setId(1);
            when(flightdao.getFlightById(1)).thenReturn(Optional.of(flight));

            ResponseEntity<ResponseStructure<Flight>> responseEntity = service.fetchFlightById(1);
            Flight founded = responseEntity.getBody().getData();

            assertEquals(1, founded.getId());
            verify(flightdao, times(1)).getFlightById(1);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenFlightNotFound() {
            when(flightdao.getFlightById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.fetchFlightById(99));
            verify(flightdao, times(1)).getFlightById(99);
        }

        @Test
        public void shouldReturnAllFlightDetails() {
            List<Flight> flights = Arrays.asList(new Flight(), new Flight());
            when(flightdao.getallFlight()).thenReturn(flights);

            ResponseEntity<ResponseStructure<List<Flight>>> responseEntity = service.fetchallFlight();
            List<Flight> data = responseEntity.getBody().getData();

            assertEquals(2, data.size());
            verify(flightdao, times(1)).getallFlight();
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenNoFlights() {
            when(flightdao.getallFlight()).thenReturn(Collections.emptyList());

            assertThrows(NoRecordFoundException.class, () -> service.fetchallFlight());
            verify(flightdao, times(1)).getallFlight();
        }
    }

    // -------------------- UPDATE --------------------
    @Nested
    class UpdateTests {
        @Test
        public void shouldUpdateFlight() {
            Flight existingFlight = new Flight(); existingFlight.setId(1);
            Flight updatedFlight = new Flight(); updatedFlight.setId(1); updatedFlight.setDestination("Mumbai");

            when(flightdao.saveFlight(updatedFlight)).thenReturn(updatedFlight);
            when(flightdao.getFlightById(1)).thenReturn(Optional.of(existingFlight));

            ResponseEntity<ResponseStructure<Flight>> responseEntity = service.updateFlight(updatedFlight);
            Flight data = responseEntity.getBody().getData();

            assertEquals("Mumbai", data.getDestination());
            verify(flightdao, times(1)).getFlightById(1);
            verify(flightdao, times(1)).saveFlight(updatedFlight);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingFlight() {
            Flight updatedFlight = new Flight(); updatedFlight.setId(99);
            when(flightdao.getFlightById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.updateFlight(updatedFlight));
            verify(flightdao, times(1)).getFlightById(99);
            verify(flightdao, times(0)).saveFlight(updatedFlight);
        }
    }

    // -------------------- DELETE --------------------
    @Nested
    class DeleteTests {
        @Test
        public void shouldDeleteFlight() {
            Flight flight = new Flight(); flight.setId(1);
            when(flightdao.getFlightById(1)).thenReturn(Optional.of(flight));

            ResponseEntity<ResponseStructure<String>> responseEntity = service.deleteFlight(1);

            assertEquals("Flight details deleted", responseEntity.getBody().getMessage());
            verify(flightdao, times(1)).getFlightById(1);
            verify(flightdao, times(1)).deleteFlight(flight);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingFlight() {
            when(flightdao.getFlightById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.deleteFlight(99));
            verify(flightdao, times(1)).getFlightById(99);
            verify(flightdao, times(0)).deleteFlight(any());
        }
    }
    
    // -------------------- PAGINATION --------------------
    @Nested
    class PaginationTests
    {
    	@Test
    	public void shouldFetchFlightByPaginationSuccessfully() throws Exception
    	{
    		Flight f1=new Flight();
    		f1.setId(1);
    		Flight f2=new Flight();
    		f2.setId(2);
    		Page<Flight> page=new PageImpl<Flight>(Arrays.asList(f1,f2));
    		when(flightdao.getFlightByPagenatio_Sort(0, 2,"id"))
    		    .thenReturn(page);
    		ResponseEntity<ResponseStructure<Page<Flight>>> responseEntity = service.getFlightByPagination_Sort(0, 2,"id");
    		 assertEquals(2, responseEntity.getBody().getData().getSize());
    		 verify(flightdao,times(1)).getFlightByPagenatio_Sort(0, 2, "id");
    	}
    	@Test
    	public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty() throws Exception
    	{
    		when(flightdao.getFlightByPagenatio_Sort(0, 5,"id"))
    		     .thenThrow(new NoRecordFoundException("No records found"));
    		assertThrows(NoRecordFoundException.class, ()->service.getFlightByPagination_Sort(0, 5, "id"));
    		verify(flightdao,times(1)).getFlightByPagenatio_Sort(0, 5, "id");
    		
    	}
    }
    
}

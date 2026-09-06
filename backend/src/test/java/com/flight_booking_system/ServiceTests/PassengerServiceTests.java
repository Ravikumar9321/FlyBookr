package com.flight_booking_system.ServiceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.flight_booking_system.DAO.Passenger_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Exception.*;
import com.flight_booking_system.Service.Passenger_Service;

public class PassengerServiceTests {

    @InjectMocks
    private Passenger_Service service;

    @Mock
    private Passenger_Dao passengerdao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------- CREATE --------------------
    @Nested
    class CreateTests {
        @Test
        public void shouldSavePassengerSuccessfully() {
            Passenger passenger = new Passenger();
            passenger.setId(1);

            when(passengerdao.savePassenger(passenger)).thenReturn(passenger);

            ResponseEntity<ResponseStructure<Passenger>> responseEntity = service.savePassenger(passenger);

            assertEquals(1, responseEntity.getBody().getData().getId());
            verify(passengerdao, times(1)).savePassenger(passenger);
        }
    }

    // -------------------- READ --------------------
    @Nested
    class ReadTests {
        @Test
        public void shouldFetchAllPassengers() {
            List<Passenger> list = Arrays.asList(new Passenger(), new Passenger(), new Passenger());
            when(passengerdao.getallPassenger()).thenReturn(list);

            ResponseEntity<ResponseStructure<List<Passenger>>> responseEntity = service.fetchallPassenger();

            assertEquals(3, responseEntity.getBody().getData().size());
            verify(passengerdao, times(1)).getallPassenger();
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenNoPassengers() {
            when(passengerdao.getallPassenger()).thenReturn(Collections.emptyList());

            assertThrows(NoRecordFoundException.class, () -> service.fetchallPassenger());
            verify(passengerdao, times(1)).getallPassenger();
        }

        @Test
        public void shouldFetchPassengerById() {
            Passenger passenger = new Passenger();
            passenger.setId(1);
            passenger.setName("Rocky");

            when(passengerdao.getPassengerById(1)).thenReturn(Optional.of(passenger));

            ResponseEntity<ResponseStructure<Passenger>> responseEntity = service.fetchPassengerById(1);
            Passenger data = responseEntity.getBody().getData();

            assertEquals(1, data.getId());
            assertEquals("Rocky", data.getName());
            verify(passengerdao, times(1)).getPassengerById(1);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenPassengerNotFound() {
            when(passengerdao.getPassengerById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.fetchPassengerById(99));
            verify(passengerdao, times(1)).getPassengerById(99);
        }

        @Test
        public void shouldFetchPassengerByContactNumber() {
            Passenger passenger = new Passenger();
            passenger.setName("Praveen");
            passenger.setContactNumber("9047862134");

            when(passengerdao.getPassengerDetailsByContactNumber("9047862134"))
                .thenReturn(Optional.of(passenger));

            ResponseEntity<ResponseStructure<Passenger>> responseEntity =
                service.getPassengerDetailsByContactNumber("9047862134");

            Passenger data = responseEntity.getBody().getData();
            assertEquals("9047862134", data.getContactNumber());
            verify(passengerdao, times(1)).getPassengerDetailsByContactNumber("9047862134");
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenContactNotFound() {
            when(passengerdao.getPassengerDetailsByContactNumber("8854862134"))
                .thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class,
                () -> service.getPassengerDetailsByContactNumber("8854862134"));

            verify(passengerdao, times(1)).getPassengerDetailsByContactNumber("8854862134");
        }
    }

    // -------------------- UPDATE --------------------
    @Nested
    class UpdateTests {
        @Test
        public void shouldUpdatePassengerSuccessfully() {
            Passenger existed = new Passenger();
            existed.setId(1);
            existed.setName("Rocky");

            Passenger updated = new Passenger();
            updated.setId(1);
            updated.setName("Pughazh");

            when(passengerdao.getPassengerById(1)).thenReturn(Optional.of(existed));
            when(passengerdao.updatePassenger(updated)).thenReturn(updated);

            ResponseEntity<ResponseStructure<Passenger>> responseEntity = service.updatePassenger(updated);
            Passenger data = responseEntity.getBody().getData();

            assertEquals(1, data.getId());
            assertEquals("Pughazh", data.getName());
            verify(passengerdao, times(1)).getPassengerById(1);
            verify(passengerdao, times(1)).updatePassenger(data);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingPassenger() {
            Passenger updated = new Passenger();
            updated.setId(99);

            when(passengerdao.getPassengerById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.updatePassenger(updated));
            verify(passengerdao, times(1)).getPassengerById(99);
        }
    }

    // -------------------- DELETE --------------------
    @Nested
    class DeleteTests {
        @Test
        public void shouldDeletePassengerSuccessfully() {
            Passenger passenger = new Passenger();
            passenger.setId(1);

            when(passengerdao.getPassengerById(1)).thenReturn(Optional.of(passenger));

            ResponseEntity<ResponseStructure<String>> responseEntity = service.deletePassenger(1);

            assertEquals("Passenger details deleted", responseEntity.getBody().getMessage());
            verify(passengerdao, times(1)).getPassengerById(1);
            verify(passengerdao, times(1)).deletePassenger(passenger);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingPassenger() {
            when(passengerdao.getPassengerById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.deletePassenger(99));
            verify(passengerdao, times(1)).getPassengerById(99);
        }
    }
    
    // -------------------- PAGINATION --------------------
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
		  when(passengerdao.getPassengerByPagenatio_Sort(0, 2, "id"))
		           .thenReturn(page);
		  ResponseEntity<ResponseStructure<Page<Passenger>>> responseEntity = service.getPassengerByPagination_Sort(0, 2,"id");
		     assertEquals(2, responseEntity.getBody().getData().getSize());
		     verify(passengerdao,times(1)).getPassengerByPagenatio_Sort(0, 2, "id");
	  }
	  @Test
	  public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty() throws Exception
	  {
		  when(passengerdao.getPassengerByPagenatio_Sort(0, 4, "id"))
		       .thenThrow(new NoRecordFoundException("No records found"));
		  assertThrows(NoRecordFoundException.class,()->service.getPassengerByPagination_Sort(0, 4,"id"));
		  verify(passengerdao,times(1)).getPassengerByPagenatio_Sort(0, 4, "id");
	  }
  }
}

package com.flight_booking_system.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.flight_booking_system.DAO.Booking_Dao;
import com.flight_booking_system.DAO.Flight_Dao;
import com.flight_booking_system.DAO.Passenger_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.*;
import com.flight_booking_system.Exception.*;

public class BookingServiceTests {

    @InjectMocks
    private Booking_Service service;

    @Mock
    private Flight_Dao flightdao;
    @Mock
    private Booking_Dao bookingdao;
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
        public void shouldCreateBookingSuccessfully() {
            Flight flight = new Flight();
            flight.setId(1);
            flight.setTotalSeats(12);
            flight.setSource("Chennai");
            flight.setPrice(200.0);

            Passenger p1 = new Passenger(); p1.setSeatNumber("11A");
            Passenger p2 = new Passenger(); p2.setSeatNumber("11B");
            List<Passenger> passengers = Arrays.asList(p1, p2);

            Bookings booking = new Bookings();
            booking.setId(1);
            booking.setFlight(flight);
            booking.setPassengers(passengers);
            booking.setPayment(new Payment());

            when(passengerdao.getallPassenger()).thenReturn(Collections.emptyList());
            when(flightdao.getFlightById(1)).thenReturn(Optional.of(flight));
            when(bookingdao.createBooking(booking)).thenReturn(booking);

            ResponseEntity<ResponseStructure<Bookings>> responseEntity = service.createBooking(booking);
            Bookings savedBooking = responseEntity.getBody().getData();

            assertEquals(1, savedBooking.getId());
            assertEquals("Chennai", savedBooking.getFlight().getSource());
            assertEquals(2, savedBooking.getPassengers().size());
            assertEquals("Booking successfully done", responseEntity.getBody().getMessage());

            verify(flightdao, times(1)).getFlightById(1);
            verify(passengerdao, times(1)).getallPassenger();
            verify(bookingdao, times(1)).createBooking(booking);
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenFlightNotFound() {
            Bookings booking = new Bookings();
            Flight flight = new Flight(); flight.setId(99);
            booking.setFlight(flight);

            when(flightdao.getFlightById(99)).thenReturn(Optional.empty());

            assertThrows(NoRecordFoundException.class, () -> service.createBooking(booking));
            verify(flightdao, times(1)).getFlightById(99);
        }

        @Test
        public void shouldThrowNoSeatAvailableExceptionWhenOverbooking() {
            Flight flight = new Flight(); flight.setId(1); flight.setTotalSeats(1);
            Passenger p1 = new Passenger(); Passenger p2 = new Passenger();
            List<Passenger> list = Arrays.asList(p1, p2);

            Bookings booking = new Bookings();
            booking.setId(1);
            booking.setFlight(flight);
            booking.setPassengers(list);

            when(passengerdao.getallPassenger()).thenReturn(Collections.emptyList());
            when(flightdao.getFlightById(1)).thenReturn(Optional.of(flight));

            assertThrows(NoSeatAvailableException.class, () -> service.createBooking(booking));
        }
    }

    // -------------------- READ --------------------
    @Nested
    class ReadTests {
        @Test
        public void shouldFetchBookingById() {
            Bookings booking = new Bookings(); booking.setId(1);
            when(bookingdao.getBookingsById(1)).thenReturn(Optional.of(booking));

            ResponseEntity<ResponseStructure<Bookings>> responseEntity = service.fetchBookingsById(1);
            Bookings savedData = responseEntity.getBody().getData();

            assertEquals(1, savedData.getId());
            verify(bookingdao, times(1)).getBookingsById(1);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenBookingNotFound() {
            when(bookingdao.getBookingsById(1)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.fetchBookingsById(1));
            verify(bookingdao, times(1)).getBookingsById(1);
        }

        @Test
        public void shouldFetchAllBookings() {
            List<Bookings> bookings = Arrays.asList(new Bookings(), new Bookings());
            when(bookingdao.getallBookings()).thenReturn(bookings);

            ResponseEntity<ResponseStructure<List<Bookings>>> responseEntity = service.fetchallBookings();
            List<Bookings> savedData = responseEntity.getBody().getData();

            assertEquals(2, savedData.size());
            verify(bookingdao, times(1)).getallBookings();
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenNoBookings() {
            when(bookingdao.getallBookings()).thenReturn(Collections.emptyList());

            assertThrows(NoRecordFoundException.class, () -> service.fetchallBookings());
            verify(bookingdao, times(1)).getallBookings();
        }
    }

    // -------------------- UPDATE --------------------
    @Nested
    class UpdateTests {
        @Test
        public void shouldUpdateBookingSuccessfully() {
            Bookings existed = new Bookings(); existed.setId(1);
            Bookings updated = new Bookings(); updated.setId(1);

            when(bookingdao.getBookingsById(1)).thenReturn(Optional.of(existed));
            when(bookingdao.updateBooking(updated)).thenReturn(updated);

            ResponseEntity<ResponseStructure<Bookings>> responseEntity = service.updateBookings(updated);
            Bookings data = responseEntity.getBody().getData();

            assertEquals(1, data.getId());
            verify(bookingdao, times(1)).updateBooking(data);
            verify(bookingdao, times(1)).getBookingsById(1);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingBooking() {
            Bookings updated = new Bookings(); updated.setId(1);
            when(bookingdao.getBookingsById(1)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.updateBookings(updated));
            verify(bookingdao, times(1)).getBookingsById(1);
        }
    }

    // -------------------- DELETE --------------------
    @Nested
    class DeleteTests {
        @Test
        public void shouldDeleteBookingSuccessfully() {
            Bookings updated = new Bookings(); updated.setId(1);

            when(bookingdao.getBookingsById(1)).thenReturn(Optional.of(updated));

            ResponseEntity<ResponseStructure<String>> responseEntity = service.deleteBookings(1);

            assertEquals("Bookings details deleted", responseEntity.getBody().getMessage());
            verify(bookingdao, times(1)).getBookingsById(1);
            verify(bookingdao, times(1)).deleteBookings(updated);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingBooking() {
            when(bookingdao.getBookingsById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.deleteBookings(99));
        }
    }
    
    // -------------------- PAGINATION --------------------
    @Nested
    class PaginationTests
    {
    	@Test
    	public void shouldFetchBookingByPaginationSuccessfully( ) throws Exception
    	{
    		Bookings b1=new Bookings();
    		b1.setId(1);
    		Bookings b2=new Bookings();
    		b2.setId(2);
    		Page<Bookings> page=new PageImpl<Bookings>(Arrays.asList(b1,b2));
    		when(bookingdao.getBookingsByPagenatio_Sort(0, 2, "id"))
    		   .thenReturn(page);
    		  ResponseEntity<ResponseStructure<Page<Bookings>>> responseEntity = service.getBookingsByPagination_Sort(0, 2,"id");
    		  Page<Bookings> data = responseEntity.getBody().getData();
    		  
    		  assertEquals(2, data.toList().size());
    		  verify(bookingdao,times(1)).getBookingsByPagenatio_Sort(0, 2, "id");
    	}
    	@Test
    	public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty()throws Exception
    	{
    		when(bookingdao.getBookingsByPagenatio_Sort(0, 5, "id"))
    		      .thenReturn(Page.empty());
    		assertThrows(NoRecordFoundException.class, ()->service.getBookingsByPagination_Sort(0, 5, "id"));
    		verify(bookingdao,times(1)).getBookingsByPagenatio_Sort(0, 5, "id");
    		  
    	}
    }
}

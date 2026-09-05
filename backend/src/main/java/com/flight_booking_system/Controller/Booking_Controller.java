package com.flight_booking_system.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Bookings;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Service.Booking_Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Booking", description = "Booking related APIs")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/booking")
public class Booking_Controller {

    @Autowired
    private Booking_Service service;

    // ---------------CREATE------------------
    @Operation(summary = "Create a new booking", description = "Adds a booking record with passenger and payment details")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @PostMapping
    public ResponseEntity<ResponseStructure<Bookings>> createBooking(@RequestBody Bookings booking) {
        return service.createBooking(booking);
    }

    // ---------------READ------------------
    @Operation(summary = "Get all bookings", description = "Fetches all booking records from the database")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Bookings>>> fetchallBookingsDetails() {
        return service.fetchallBookings();
    }

    @Operation(summary = "Get booking by ID", description = "Fetches a single booking record using its unique ID")
    @ApiResponse(responseCode = "200", description = "Booking retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Bookings>> fetchBookingsDetailsById(@PathVariable int id) {
        return service.fetchBookingsById(id);
    }

    @Operation(summary = "Get bookings with pagination and sorting", description = "Fetches booking records with pagination and sorting by a given field")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    @GetMapping("/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<ResponseStructure<Page<Bookings>>> getBookingsDetailsByPagenation_Sort(
            @PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String field) {
        return service.getBookingsByPagination_Sort(pageNumber, pageSize, field);
    }

    @Operation(summary = "Get passengers by booking ID", description = "Fetches all passengers linked to a specific booking")
    @ApiResponse(responseCode = "200", description = "Passengers retrieved successfully")
    @GetMapping("/passenger/{bookingId}")
    public ResponseEntity<ResponseStructure<List<Passenger>>> getallPassengersByBookingsId(@PathVariable Integer bookingId) {
        return service.getallPassengersByBookingsId(bookingId);
    }

    @Operation(summary = "Get bookings by date", description = "Fetches booking records filtered by booking date")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    @GetMapping("/date/{date}")
    public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingsDetailsByDate(@PathVariable LocalDate date) {
        return service.getBookingsDetailsByDate(date);
    }

    @Operation(summary = "Get bookings by status", description = "Fetches booking records filtered by booking status")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingsDetailsByStatus(@PathVariable Bookingstatus status) {
        return service.getBookingsDetailsByStatus(status);
    }

    @Operation(summary = "Get payment by booking ID", description = "Fetches payment details linked to a specific booking")
    @ApiResponse(responseCode = "200", description = "Payment retrieved successfully")
    @GetMapping("/payment/{bookingId}")
    public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsbyBookingId(@PathVariable Integer bookingId) {
        return service.getPaymentDetailsbyBookingId(bookingId);
    }

    @Operation(summary = "Get bookings by flight ID", description = "Fetches booking records linked to a specific flight")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<ResponseStructure<List<Bookings>>> getBookingDetailsbyFlightId(@PathVariable Integer flightId) {
        return service.getBookingDetailsbyFlightId(flightId);
    }

    // ---------------UPDATE------------------
    
    @Operation(summary = "Update booking", description = "Updates an existing booking record")
    @ApiResponse(responseCode = "200", description = "Booking updated successfully")
    @PutMapping
    public ResponseEntity<ResponseStructure<Bookings>> updateBookingsDetails(@RequestBody Bookings booking) {
        return service.updateBookings(booking);
    }

    @Operation(summary = "Update booking status", description = "Updates the status of a booking by ID")
    @ApiResponse(responseCode = "200", description = "Booking status updated successfully")
    @PutMapping("/{id}/{status}")
    public ResponseEntity<ResponseStructure<Bookings>> updateBookingStatus(@PathVariable Integer id, @PathVariable Bookingstatus status) {
        return service.updateBookingStatus(id, status);
    }

    // ---------------DELETE------------------
    
    @Operation(summary = "Delete booking", description = "Deletes a booking record by its ID")
    @ApiResponse(responseCode = "200", description = "Booking deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteBookingsDetails(@PathVariable int id) {
        return service.deleteBookings(id);
    }
}

package com.flight_booking_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Service.Passenger_Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Passenger", description = "Passenger related APIs")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/passenger")
public class Passenger_Controller {

    @Autowired
    private Passenger_Service service;

    // ---------------CREATE------------------

    @Operation(summary = "Create a new passenger", description = "Adds a new passenger record")
    @ApiResponse(responseCode = "201", description = "Passenger created successfully")
    @PostMapping
    public ResponseEntity<ResponseStructure<Passenger>> savePassenger(@Valid @RequestBody Passenger passenger) {
        return service.savePassenger(passenger);
    }
    
    // ---------------READ------------------

    @Operation(summary = "Get all passengers", description = "Fetches all passenger records from the database")
    @ApiResponse(responseCode = "200", description = "Passengers retrieved successfully")
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Passenger>>> fetchallPassengerDetails() {
        return service.fetchallPassenger();
    }

    @Operation(summary = "Get passenger by ID", description = "Fetches a single passenger record using its unique ID")
    @ApiResponse(responseCode = "200", description = "Passenger retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Passenger>> fetchPassengerDetailsById(@PathVariable int id) {
        return service.fetchPassengerById(id);
    }

    @Operation(summary = "Get passengers with pagination and sorting", description = "Fetches passenger records with pagination and sorting by a given field")
    @ApiResponse(responseCode = "200", description = "Passengers retrieved successfully")
    @GetMapping("/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerDetailsByPagenation_Sort(
            @PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String field) {
        return service.getPassengerByPagination_Sort(pageNumber, pageSize, field);
    }

    @Operation(summary = "Get passenger by contact number", description = "Fetches a passenger record using their contact number")
    @ApiResponse(responseCode = "200", description = "Passenger retrieved successfully")
    @GetMapping("/contact/{contact}")
    public ResponseEntity<ResponseStructure<Passenger>> getPassengerDetailsByContactNumber(@PathVariable String contact) {
        return service.getPassengerDetailsByContactNumber(contact);
    }
    // ---------------UPDATE------------------

    @Operation(summary = "Update passenger", description = "Updates an existing passenger record")
    @ApiResponse(responseCode = "200", description = "Passenger updated successfully")
    @PutMapping
    public ResponseEntity<ResponseStructure<Passenger>> updatePassengerDetails(@RequestBody Passenger passenger) {
        return service.updatePassenger(passenger);
    }
    
    // ---------------DELETE------------------
    
    @Operation(summary = "Delete passenger", description = "Deletes a passenger record by its ID")
    @ApiResponse(responseCode = "200", description = "Passenger deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deletePassengerDetails(@PathVariable int id) {
        return service.deletePassenger(id);
    }
}

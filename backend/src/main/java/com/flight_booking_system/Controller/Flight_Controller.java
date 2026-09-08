package com.flight_booking_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Service.Flight_Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Flight", description = "Flight related APIs")
@RestController
@RequestMapping("/api/flight")
@CrossOrigin(origins = "http://localhost:3000")
public class Flight_Controller {

    @Autowired
    private Flight_Service service;

    // ---------------CREATE------------------
    @Operation(summary = "Create a new flight", description = "Adds a new flight record")
    @ApiResponse(responseCode = "201", description = "Flight created successfully")
    @PostMapping
    public ResponseEntity<ResponseStructure<Flight>> saveFlightDetails(@Valid @RequestBody Flight flight) {
        return service.saveFlight(flight);
    }

    @Operation(summary = "Create multiple flights", description = "Adds multiple flight records at once")
    @ApiResponse(responseCode = "201", description = "Flights created successfully")
    @PostMapping("/all")
    public ResponseEntity<ResponseStructure<List<Flight>>> saveAllFlightDetails(@RequestBody List<Flight> flight) {
        return service.saveAllFlightDetails(flight);
    }

    // ---------------READ------------------
    @Operation(summary = "Get all flights", description = "Fetches all flight records from the database")
    @ApiResponse(responseCode = "200", description = "Flights retrieved successfully")
    @GetMapping()
    public ResponseEntity<ResponseStructure<List<Flight>>> fetchallFlightDetails() {
        return service.fetchallFlight();
    }

    @Operation(summary = "Get flight by ID", description = "Fetches a single flight record using its unique ID")
    @ApiResponse(responseCode = "200", description = "Flight retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Flight>> fetchFlightDetailsById(@PathVariable int id) {
        return service.fetchFlightById(id);
    }

    @Operation(summary = "Get flights with pagination and sorting", description = "Fetches flight records with pagination and sorting by a given field")
    @ApiResponse(responseCode = "200", description = "Flights retrieved successfully")
    @GetMapping("/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightDetailsByPagenation_Sort(
            @PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String field) {
        return service.getFlightByPagination_Sort(pageNumber, pageSize, field);
    }

    @Operation(summary = "Get flights by airline name", description = "Fetches flight records filtered by airline name")
    @ApiResponse(responseCode = "200", description = "Flights retrieved successfully")
    @GetMapping("airline/{airlineName}")
    public ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsByAirlineName(@PathVariable String airlineName) {
        return service.getFlightDetailsByAirlineName(airlineName);
    }

    @Operation(summary = "Get flights by source and destination", description = "Fetches flight records filtered by source and destination")
    @ApiResponse(responseCode = "200", description = "Flights retrieved successfully")
    @GetMapping("/{source}/{destination}")
    public ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsBysourceAndDestionation(
            @PathVariable String source, @PathVariable String destination) {
        return service.getFlightDetailsBysourceAndDestionation(source, destination);
    }

    @Operation(summary = "Get flights by price", description = "Fetches flight records filtered by price")
    @ApiResponse(responseCode = "200", description = "Flights retrieved successfully")
    @GetMapping("price/{price}")
    public ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsByPrice(@PathVariable String price) {
        return service.getFlightDetailsByPrice(price);
    }

    // ---------------UPDATE------------------
    @Operation(summary = "Update flight", description = "Updates an existing flight record")
    @ApiResponse(responseCode = "200", description = "Flight updated successfully")
    @PutMapping
    public ResponseEntity<ResponseStructure<Flight>> updateFlightDetails(@RequestBody Flight flight) {
        return service.updateFlight(flight);
    }

    // ---------------DELETE------------------
    @Operation(summary = "Delete flight", description = "Deletes a flight record by its ID")
    @ApiResponse(responseCode = "200", description = "Flight deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteFlightDetails(@PathVariable int id) {
        return service.deleteFlight(id);
    }
}

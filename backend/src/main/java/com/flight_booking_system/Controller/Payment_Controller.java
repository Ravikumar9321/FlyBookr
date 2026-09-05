package com.flight_booking_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.PaymentMode;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Service.Payment_Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment", description = "Payment related APIs")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class Payment_Controller {

    @Autowired
    private Payment_Service service;

    // ---------------CREATE------------------
    
    @Operation(summary = "Create a new payment", description = "Saves a new payment record linked to a booking")
    @ApiResponse(responseCode = "201", description = "Payment created successfully")
    @PostMapping
    public ResponseEntity<ResponseStructure<Payment>> savePayment(@RequestBody Payment payment) {
        return service.savePayment(payment);
    }

    // ---------------READ------------------
    
    @Operation(summary = "Get all payments", description = "Fetches all payment records from the database")
    @ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Payment>>> fetchallPaymentDetails() {
        return service.fetchallPayment();
    }

    @Operation(summary = "Get payment by ID", description = "Fetches a single payment record using its unique ID")
    @ApiResponse(responseCode = "200", description = "Payment retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Payment>> fetchPaymentDetailsById(@PathVariable int id) {
        return service.fetchPaymentById(id);
    }

    @Operation(summary = "Get payments with pagination and sorting", description = "Fetches payment records with pagination and sorting by a given field")
    @ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    @GetMapping("/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentDetailsByPagenation_Sort(
            @PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String field) {
        return service.getPaymentByPagination_Sort(pageNumber, pageSize, field);
    }

    @Operation(summary = "Get payments by status", description = "Fetches payment records filtered by booking status")
    @ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByStatus(@PathVariable Bookingstatus status) {
        return service.getPaymentDetailsByStatus(status);
    }

    @Operation(summary = "Get payments by price", description = "Fetches payment records with price greater than the given value")
    @ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    @GetMapping("/price/{price}")
    public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByPrice(@PathVariable Double price) {
        return service.getPaymentDetailsByPrice(price);
    }

    @Operation(summary = "Get payments by mode", description = "Fetches payment records filtered by payment mode (e.g., CARD, UPI, CASH)")
    @ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    @GetMapping("/paymentMode/{paymentMode}")
    public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByPaymentMode(@PathVariable PaymentMode paymentMode) {
        return service.getPaymentDetailsByPaymentMode(paymentMode);
    }

               // ---------------UPDATE------------------
    
    @Operation(summary = "Update payment", description = "Updates an existing payment record")
    @ApiResponse(responseCode = "200", description = "Payment updated successfully")
    @PutMapping
    public ResponseEntity<ResponseStructure<Payment>> updatePaymentDetails(@RequestBody Payment payment) {
        return service.updatePayment(payment);
    }

             // ---------------DELETE------------------
    @Operation(summary = "Delete payment", description = "Deletes a payment record by its ID")
    @ApiResponse(responseCode = "200", description = "Payment deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deletePaymentDetails(@PathVariable int id) {
        return service.deletePayment(id);
    }
}

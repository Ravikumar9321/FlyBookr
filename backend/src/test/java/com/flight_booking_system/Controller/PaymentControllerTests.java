package com.flight_booking_system.Controller;

import static org.mockito.ArgumentMatchers.any;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Exception.*;
import com.flight_booking_system.Service.Payment_Service;
@WebMvcTest(Payment_Controller.class)
public class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private Payment_Service service;

   
    @Nested
    class CreateTests {
        @Test
        public void shouldSavePaymentSuccessfully() throws Exception {
            Payment payment = new Payment(); payment.setId(1); payment.setAmount(5000.0);

            ResponseStructure<Payment> response = new ResponseStructure<>();
            response.setStatusCode(201);
            response.setMessage("Payment Details Saved");
            response.setData(payment);

            when(service.savePayment(any(Payment.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

            mockMvc.perform(post("/api/payment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payment)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.message").value("Payment Details Saved"));
        }

        @Test
        public void shouldReturnBadRequestWhenPaymentIsInvalid() throws Exception {
            Payment payment = new Payment();
            when(service.savePayment(any(Payment.class)))
                .thenThrow(new NullPointerException("Invalid input"));

            mockMvc.perform(post("/api/payment") 
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payment)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.message").value("FAILURE"))
                    .andExpect(jsonPath("$.data").value("Invalid input"));
        }
    }

   
    @Nested
    class ReadTests {
        @Test
        public void shouldFetchPaymentByIdSuccessfully() throws Exception {
            Payment payment = new Payment(); payment.setId(1); payment.setAmount(400.0);

            ResponseStructure<Payment> response = new ResponseStructure<>();
            response.setData(payment);
            response.setMessage("Payments details are retrieved by PaymentId");
            response.setStatusCode(200);

            when(service.fetchPaymentById(1)).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(get("/api/payment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.message").value("Payments details are retrieved by PaymentId"));
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenPaymentNotExists() throws Exception {
            when(service.fetchPaymentById(99))
                .thenThrow(new IdNotFoundException("Payment ID not found"));

            mockMvc.perform(get("/api/payment/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("FAILURE"))
                .andExpect(jsonPath("$.data").value("Payment ID not found"));
        }

        @Test
        public void shouldFetchAllPaymentsSuccessfully() throws Exception {
            List<Payment> payments = Arrays.asList(new Payment(), new Payment());
            ResponseStructure<List<Payment>> response = new ResponseStructure<>();
            response.setData(payments);
            response.setMessage("Payments details are retrieved");
            response.setStatusCode(200);

            when(service.fetchallPayment()).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(get("/api/payment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.message").value("Payments details are retrieved"));
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenNoPayments() throws Exception {
            when(service.fetchallPayment())
                .thenThrow(new NoRecordFoundException("No Records Found"));

            mockMvc.perform(get("/api/payment"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("FAILURE"))
                .andExpect(jsonPath("$.data").value("No Records Found"));
        }
    }

    
    @Nested
    class UpdateTests {
        @Test
        public void shouldUpdatePaymentSuccessfully() throws Exception {
            Payment payment = new Payment(); payment.setId(1); payment.setAmount(400.0);

            ResponseStructure<Payment> response = new ResponseStructure<>();
            response.setStatusCode(200);
            response.setMessage("Updated Success");
            response.setData(payment);

            when(service.updatePayment(payment)).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(put("/api/payment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payment)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.message").value("Updated Success"))
                    .andExpect(jsonPath("$.data.amount").value(400.0));
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingPayment() throws Exception {
            Payment payment = new Payment(); payment.setId(1);

            when(service.updatePayment(payment))
                .thenThrow(new IdNotFoundException("Payment ID not found"));

            mockMvc.perform(put("/api/payment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payment)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("FAILURE"))
                    .andExpect(jsonPath("$.data").value("Payment ID not found"));
        }
    }

    
    @Nested
    class DeleteTests {
        @Test
        public void shouldDeletePaymentSuccessfully() throws Exception {
            ResponseStructure<String> response = new ResponseStructure<>();
            response.setMessage("Deleted Success");
            response.setStatusCode(200);
            response.setData("SUCCESS");

            when(service.deletePayment(1)).thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(delete("/api/payment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("Deleted Success"));
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingPayment() throws Exception {
            when(service.deletePayment(99))
                .thenThrow(new IdNotFoundException("Payment ID not found"));

            mockMvc.perform(delete("/api/payment/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("FAILURE"))
                .andExpect(jsonPath("$.data").value("Payment ID not found"));
        }
    }
    
    @Nested
    class PaginationTests {

        @Test
        public void shouldFetchPaymentsByPaginationSuccessfully() throws Exception {

            Page<Payment> page = new PageImpl<>(Arrays.asList(new Payment(),new Payment()));

            ResponseStructure<Page<Payment>> response = new ResponseStructure<>();
            response.setStatusCode(200);
            response.setMessage("Payments Retrieved by Pagination");
            response.setData(page);

            when(service.getPaymentByPagination_Sort(0, 2, "id"))
                .thenReturn(ResponseEntity.ok(response));

            mockMvc.perform(get("/api/payment/0/2/id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.message").value("Payments Retrieved by Pagination"));
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty() throws Exception {
            when(service.getPaymentByPagination_Sort(0, 5, "id"))
                .thenThrow(new NoRecordFoundException("No Records Found"));

            mockMvc.perform(get("/api/payment/0/5/id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("FAILURE"))
                .andExpect(jsonPath("$.data").value("No Records Found"));
        }
    }

    
}

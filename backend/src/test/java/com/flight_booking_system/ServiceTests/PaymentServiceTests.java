package com.flight_booking_system.ServiceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.flight_booking_system.DAO.Payment_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Exception.*;
import com.flight_booking_system.Service.Payment_Service;

public class PaymentServiceTests {

    @InjectMocks
    private Payment_Service service;

    @Mock
    private Payment_Dao paymentdao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------- CREATE --------------------
    @Nested
    class CreateTests {
        @Test
        public void shouldSavePaymentSuccessfully() {
            Payment payment = new Payment();
            payment.setId(1);

            when(paymentdao.savePayment(payment)).thenReturn(payment);

            ResponseEntity<ResponseStructure<Payment>> responseEntity = service.savePayment(payment);

            assertEquals(1, responseEntity.getBody().getData().getId());
            verify(paymentdao, times(1)).savePayment(payment);
        }
    }

    // -------------------- READ --------------------
    @Nested
    class ReadTests {
        @Test
        public void shouldFetchAllPayments() {
            List<Payment> list = Arrays.asList(new Payment(), new Payment());
            when(paymentdao.getallPayment()).thenReturn(list);

            ResponseEntity<ResponseStructure<List<Payment>>> responseEntity = service.fetchallPayment();

            assertEquals(2, responseEntity.getBody().getData().size());
            verify(paymentdao, times(1)).getallPayment();
        }

        @Test
        public void shouldThrowNoRecordFoundExceptionWhenNoPayments() {
            when(paymentdao.getallPayment()).thenReturn(Collections.emptyList());

            assertThrows(NoRecordFoundException.class, () -> service.fetchallPayment());
            verify(paymentdao, times(1)).getallPayment();
        }

        @Test
        public void shouldFetchPaymentById() {
            Payment payment = new Payment(); payment.setId(1);
            when(paymentdao.getPaymentById(1)).thenReturn(Optional.of(payment));

            ResponseEntity<ResponseStructure<Payment>> responseEntity = service.fetchPaymentById(1);

            assertEquals(1, responseEntity.getBody().getData().getId());
            verify(paymentdao, times(1)).getPaymentById(1);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenPaymentNotFound() {
            when(paymentdao.getPaymentById(1)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.fetchPaymentById(1));
            verify(paymentdao, times(1)).getPaymentById(1);
        }
    }

    // -------------------- UPDATE --------------------
    @Nested
    class UpdateTests {
        @Test
        public void shouldUpdatePaymentSuccessfully() {
            Payment existed = new Payment(); existed.setId(1);
            Payment updated = new Payment(); updated.setId(1);

            when(paymentdao.getPaymentById(1)).thenReturn(Optional.of(existed));
            when(paymentdao.updatePayment(updated)).thenReturn(updated);

            ResponseEntity<ResponseStructure<Payment>> responseEntity = service.updatePayment(updated);

            assertEquals(1, responseEntity.getBody().getData().getId());
            verify(paymentdao, times(1)).getPaymentById(1);
            verify(paymentdao, times(1)).updatePayment(updated);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenUpdatingNonExistingPayment() {
            Payment updated = new Payment(); updated.setId(12);

            when(paymentdao.getPaymentById(12)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.updatePayment(updated));
            verify(paymentdao, times(1)).getPaymentById(12);
            verify(paymentdao, times(0)).updatePayment(updated);
        }
    }

    // -------------------- DELETE --------------------
    @Nested
    class DeleteTests {
        @Test
        public void shouldDeletePaymentSuccessfully() {
            Payment payment = new Payment(); payment.setId(1);

            when(paymentdao.getPaymentById(1)).thenReturn(Optional.of(payment));

            ResponseEntity<ResponseStructure<String>> responseEntity = service.deletePayment(1);

            assertEquals("Payment details deleted", responseEntity.getBody().getMessage());
            verify(paymentdao, times(1)).getPaymentById(1);
            verify(paymentdao, times(1)).deletePayment(payment);
        }

        @Test
        public void shouldThrowIdNotFoundExceptionWhenDeletingNonExistingPayment() {
            when(paymentdao.getPaymentById(99)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> service.deletePayment(99));
            verify(paymentdao, times(1)).getPaymentById(99);
            verify(paymentdao, times(0)).deletePayment(any());
        }
    }
    
    // -------------------- PAGINATION --------------------
    @Nested
    class PaginationTests
    {
    	@Test
    	public void shouldFetchPaymentByPaginationSuccessfully() throws Exception
    	{
    		Payment p1=new Payment();
    		p1.setId(1);
    		Payment p2=new  Payment();
    		p2.setId(2);
    		Page<Payment> page=new PageImpl<Payment>(Arrays.asList(p1,p2));
    		when(paymentdao.getPaymentByPagenation_Sort(0, 2, "id"))
    		     .thenReturn(page);
    		ResponseEntity<ResponseStructure<Page<Payment>>> responseEntity = service.getPaymentByPagination_Sort(0, 2,"id");
    		assertEquals(2, responseEntity.getBody().getData().getSize());
    		verify(paymentdao,times(1)).getPaymentByPagenation_Sort(0, 2,"id");
    				
    	}
    	@Test
    	public void shouldThrowNoRecordFoundExceptionWhenPaginationEmpty() throws Exception
    	{
    		when(paymentdao.getPaymentByPagenation_Sort(0, 2, "id"))
    		  .thenThrow(new NoRecordFoundException("No record found"));
    		assertThrows(NoRecordFoundException.class, ()->service.getPaymentByPagination_Sort(0, 2,"id"));
    		verify(paymentdao,times(1)).getPaymentByPagenation_Sort(0, 2, "id");
    	}
    }
}

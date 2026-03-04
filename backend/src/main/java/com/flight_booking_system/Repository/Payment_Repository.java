package com.flight_booking_system.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.PaymentMode;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Entity.Payment;

public interface Payment_Repository extends JpaRepository<Payment, Integer> {
	
	

	@Query("select p from Payment p where p.status=?1")
	List<Payment> getPaymentDetailsByStatus(Bookingstatus status);
	
	@Query("select p from Payment p where p.amount>:price")
	List<Payment> getPaymentDetailsByPriceGreater(Double price);
	
	@Query("select p from Payment p where p.mode=?1")
	List<Payment> getPaymentDetailsByPaymentMode(PaymentMode paymentMode);
	

}

package com.flight_booking_system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import com.flight_booking_system.DTO.*;
import com.flight_booking_system.Entity.*;


public interface Payment_Repository extends JpaRepository<Payment, Integer> {
	
	

	@Query("select p from Payment p where p.status=?1")
	List<Payment> getPaymentDetailsByStatus(Bookingstatus status);
	
	@Query("select p from Payment p where p.amount>:price")
	List<Payment> getPaymentDetailsByPriceGreater(Double price);
	
	@Query("select p from Payment p where p.mode=?1")
	List<Payment> getPaymentDetailsByPaymentMode(PaymentMode paymentMode);
	

}

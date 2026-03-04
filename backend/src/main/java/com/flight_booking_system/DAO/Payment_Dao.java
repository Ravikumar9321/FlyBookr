package com.flight_booking_system.DAO;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.PaymentMode;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Repository.Payment_Repository;
import com.flight_booking_system.Repository.Payment_Repository;


@Repository
public class Payment_Dao {
	@Autowired
	private Payment_Repository pr;

//i)save Payment
	public Payment savePayment(Payment payment) {
		return pr .save(payment);
	}
//ii)fetch all Payment details
	public List<Payment> getallPayment() {
		return pr .findAll();
	}

	//iii)get Payment details by id
	public Optional<Payment> getPaymentById(int id) {
		// TODO Auto-generated method stub
		return pr .findById(id);
	}
	
	//v)delete Payment details
	public void deletePayment(Payment payment) {
	       pr .delete(payment);
	}
	
	//vi)get Details in pagenaton ,sort format
	public Page<Payment> getPaymentByPagenatio_Sort(int pageNumber, int pageSize, String field) {
		return pr .findAll(PageRequest.of(pageNumber, pageSize,Sort.by(field).ascending()));
	}
	public List<Payment> getPaymentDetailsByStatus(Bookingstatus status) {
		// TODO Auto-generated method stub
		return pr.getPaymentDetailsByStatus(status);
	}
	public List<Payment> getPaymentDetailsByPrice(Double price) {
		// TODO Auto-generated method stub
		return pr.getPaymentDetailsByPriceGreater(price);
	}
	public List<Payment> getPaymentDetailsByPaymentMode(PaymentMode paymentMode) {
		// TODO Auto-generated method stub
		return pr.getPaymentDetailsByPaymentMode(paymentMode);
	}
}

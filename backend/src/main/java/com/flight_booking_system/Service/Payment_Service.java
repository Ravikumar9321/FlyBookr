package com.flight_booking_system.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight_booking_system.DAO.Payment_Dao;
import com.flight_booking_system.DAO.Payment_Dao;
import com.flight_booking_system.DTO.Bookingstatus;
import com.flight_booking_system.DTO.PaymentMode;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Payment;
import com.flight_booking_system.Exception.IdNotFoundException;
import com.flight_booking_system.Exception.NoRecordFoundException;


@Service
public class Payment_Service {
	

   @Autowired
	private Payment_Dao paymentdao;
	

///i)save Payment details
	public  ResponseEntity<ResponseStructure<Payment>> savePayment(Payment payment) {
		ResponseStructure<Payment> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Payment Details Saved");
		rs.setData(paymentdao.savePayment(payment));
		return new ResponseEntity<>(rs,HttpStatus.CREATED);

	}
  //ii)fetch all Payment details
	public ResponseEntity<ResponseStructure<List<Payment>>> fetchallPayment() {
		ResponseStructure<List<Payment>> rs=new ResponseStructure<>();
		     List<Payment> ls=paymentdao.getallPayment();
		     if(ls.size()>0) {
		    	 rs.setStatusCode(HttpStatus.OK.value());
		    	 rs.setMessage("Payments details are retrieved");
		    	 rs.setData(ls);
		    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		     }
		     else
		    	 throw new NoRecordFoundException("No Records Found");
		}
	
	  //iii)fetch  Payment details by id
	public ResponseEntity<ResponseStructure<Payment>> fetchPaymentById(int id) {
		ResponseStructure<Payment> rs=new ResponseStructure<>();
	     Optional<Payment> Payment =paymentdao.getPaymentById(id);
	     if(Payment.isPresent()) {
	    	 rs.setStatusCode(HttpStatus.OK.value());
	    	 rs.setMessage("Payments details are retrieved by PaymentId");
	    	 rs.setData(Payment.get());
	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	     }
	     else
	    	 throw new IdNotFoundException("Invalid,Payment Id not Found");
	}
	
	//iv)update Payment details
	public ResponseEntity<ResponseStructure<Payment>> updatePayment(Payment payment) {
		ResponseStructure<Payment> rs=new ResponseStructure<>();

            if(payment.getId()==null) 
            	throw new NullPointerException("Enter Valid Payment Id");
          	 
	     		 Optional<Payment> opt=paymentdao.getPaymentById(payment.getId());

              if(opt.isPresent()) {
            	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Payment details are updated");
     	    	 rs.setData(paymentdao.savePayment(payment));
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
              }
              else
            	  throw new IdNotFoundException("Invalid ,Id is not Found");
}
	
	//v)delete Payment details
	public ResponseEntity<ResponseStructure<String>> deletePayment(int id) {
		ResponseStructure<String> rs=new ResponseStructure<>();
		    Optional<Payment> opt=paymentdao.getPaymentById(id);
		    if(opt.isPresent()) {
		    	  rs.setStatusCode(HttpStatus.OK.value());
	     	    	 rs.setMessage("Payment details deleted");
	     	    	 paymentdao.deletePayment(opt.get());
	     	    	 rs.setData("SUCCESS");
	     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		    }
		    else
          	  throw new IdNotFoundException("Invalid ,Id is not Found");
	}
	
	//vi)get Details in pagenaton ,sort format
	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentByPagination_Sort(int pageNumber, int pageSize,
			String field) {
		ResponseStructure<Page<Payment>> rs=new ResponseStructure<>();
	    Page<Payment> l=paymentdao.getPaymentByPagenatio_Sort(pageNumber,pageSize,field);
		   if(!l.isEmpty()) {
			   rs.setStatusCode(HttpStatus.OK.value());
				rs.setMessage("Recordsfounded");
				rs.setData(l);
				return new ResponseEntity<>(rs,HttpStatus.OK);
		   }
		   else {
			  throw new NoRecordFoundException("No records Found");
		   }
	}
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByStatus(Bookingstatus status) {
		ResponseStructure<List<Payment>> rs=new ResponseStructure<>();
	    List<Payment> payments=paymentdao.getPaymentDetailsByStatus(status);
	    if(!payments.isEmpty()) {
	    	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Payment details retrieved by status");
     	    	 rs.setData(payments);
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	    }
	    else
      	  throw new IdNotFoundException("Invalid ,Payment details is not Found");
	
	}
	
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByPrice(Double price) {
		ResponseStructure<List<Payment>> rs=new ResponseStructure<>();

		 List<Payment> payments=paymentdao.getPaymentDetailsByPrice(price);
		    if(!payments.isEmpty()) {
		    	  rs.setStatusCode(HttpStatus.OK.value());
	     	    	 rs.setMessage("Payment details retrieved based on price greater than a value ");
	     	    	 rs.setData(payments);
	     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		    }
		    else
	      	  throw new IdNotFoundException("Invalid ,Payment details is not Found");
	}
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentDetailsByPaymentMode(PaymentMode paymentMode) {
		ResponseStructure<List<Payment>> rs=new ResponseStructure<>();
	    List<Payment> payments=paymentdao.getPaymentDetailsByPaymentMode(paymentMode);
	    if(!payments.isEmpty()) {
	    	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Payment details retrieved by paymentMode");
     	    	 rs.setData(payments);
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	    }
	    else
      	  throw new IdNotFoundException("Invalid ,Payment details is not Found");
	}
	
	


}

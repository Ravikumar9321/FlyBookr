package com.flight_booking_system.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight_booking_system.DAO.Passenger_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Passenger;
import com.flight_booking_system.Exception.IdNotFoundException;
import com.flight_booking_system.Exception.NoRecordFoundException;


@Service
public class Passenger_Service {
	

   @Autowired
	private Passenger_Dao pdao;
	

///i)save Passenger details
	public  ResponseEntity<ResponseStructure<Passenger>> savePassenger(Passenger passenger) {
		ResponseStructure<Passenger> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Passenger Details Saved");
		rs.setData(pdao.savePassenger(passenger));
		return new ResponseEntity<>(rs,HttpStatus.CREATED);

	}
  //ii)fetch all Passenger details
	public ResponseEntity<ResponseStructure<List<Passenger>>> fetchallPassenger() {
		ResponseStructure<List<Passenger>> rs=new ResponseStructure<>();
		     List<Passenger> ls=pdao.getallPassenger();
		     if(ls.size()>0) {
		    	 rs.setStatusCode(HttpStatus.OK.value());
		    	 rs.setMessage("Passengers details are retrieved");
		    	 rs.setData(ls);
		    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		     }
		     else
		    	 throw new NoRecordFoundException("No Records Found");
		}
	
	  //iii)fetch  Passenger details by id
	public ResponseEntity<ResponseStructure<Passenger>> fetchPassengerById(int id) {
		ResponseStructure<Passenger> rs=new ResponseStructure<>();
	     Optional<Passenger> Passenger =pdao.getPassengerById(id);
	     if(Passenger.isPresent()) {
	    	 rs.setStatusCode(HttpStatus.OK.value());
	    	 rs.setMessage("Passengers details are retrieved by PassengerId");
	    	 rs.setData(Passenger.get());
	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	     }
	     else
	    	 throw new IdNotFoundException("Invalid,Passenger Id not Found");
	}
	
	//iv)update Passenger details
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Passenger passenger) {
		ResponseStructure<Passenger> rs=new ResponseStructure<>();

            if(passenger.getId()==null) 
            	throw new NullPointerException("Enter Valid Passenger Id");
          	 
	     		 Optional<Passenger> opt=pdao.getPassengerById(passenger.getId());
         List<Passenger> passengers=pdao.getallPassenger();
              if(opt.isPresent()) {
            	  for(Passenger p:passengers) {
            		  if(p.getId()==passenger.getId()) {
            			  passenger.setBooking(p.getBooking());
            		  }
            	  }
            	  
            	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Passenger details are updated");
     	    	 rs.setData(pdao.savePassenger(passenger));
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
              }
              else
            	  throw new IdNotFoundException("Invalid ,Id is not Found");
}
	
	//v)delete Passenger details
	public ResponseEntity<ResponseStructure<String>> deletePassenger(int id) {
		ResponseStructure<String> rs=new ResponseStructure<>();
		    Optional<Passenger> opt=pdao.getPassengerById(id);
		    if(opt.isPresent()) {
		    	  rs.setStatusCode(HttpStatus.OK.value());
	     	    	 rs.setMessage("Passenger details deleted");
	     	    	 pdao.deletePassenger(opt.get());
	     	    	 rs.setData("SUCCESS");
	     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		    }
		    else
          	  throw new IdNotFoundException("Invalid ,Id is not Found");
	}
	
	//vi)get Details in pagenaton ,sort format
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPagination_Sort(int pageNumber, int pageSize,
			String field) {
		ResponseStructure<Page<Passenger>> rs=new ResponseStructure<>();
	    Page<Passenger> l=pdao.getPassengerByPagenatio_Sort(pageNumber,pageSize,field);
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
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerDetailsByContactNumber(String contact) {
		ResponseStructure<Passenger> rs=new ResponseStructure<>();
	    Optional<Passenger> opt=pdao.getPassengerDetailsByContactNumber(contact);
	    if(opt.isPresent()) {
	    	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Passenger details retrieved by contactNumber");
     	    	 rs.setData(opt.get());
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	    }
	    else
      	  throw new IdNotFoundException("Invalid ,contact is not Found");
	
	}
	
	


}

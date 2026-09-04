package com.flight_booking_system.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight_booking_system.DAO.Flight_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Exception.IdNotFoundException;
import com.flight_booking_system.Exception.NoRecordFoundException;

@Service
public class Flight_Service {
	

   @Autowired
	private Flight_Dao fdao;
	

///i)save Flight details
	public  ResponseEntity<ResponseStructure<Flight>> saveFlight(Flight flight) {
		ResponseStructure<Flight> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Flight Details Saved");
		rs.setData(fdao.saveFlight(flight));
		return new ResponseEntity<>(rs,HttpStatus.CREATED);

	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> saveAllFlightDetails(List<Flight> flight) {
		ResponseStructure<List<Flight>> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Flight Details Saved");
		rs.setData(fdao.saveAllFlightDetails(flight));
		return new ResponseEntity<>(rs,HttpStatus.CREATED);
	}
  //ii)fetch all Flight details
	public ResponseEntity<ResponseStructure<List<Flight>>> fetchallFlight() {
		ResponseStructure<List<Flight>> rs=new ResponseStructure<>();
		     List<Flight> ls=fdao.getallFlight();
		     if(ls.size()>0) {
		    	 rs.setStatusCode(HttpStatus.OK.value());
		    	 rs.setMessage("Flights details are retrieved");
		    	 rs.setData(ls);
		    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		     }
		     else
		    	 throw new NoRecordFoundException("No Records Found");
		}
	
	  //iii)fetch  Flight details by id
	public ResponseEntity<ResponseStructure<Flight>> fetchFlightById(int id) {
		ResponseStructure<Flight> rs=new ResponseStructure<>();
	     Optional<Flight> Flight =fdao.getFlightById(id);
	     if(Flight.isPresent()) {
	    	 rs.setStatusCode(HttpStatus.OK.value());
	    	 rs.setMessage("Flights details are retrieved by FlightId");
	    	 rs.setData(Flight.get());
	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	     }
	     else
	    	 throw new IdNotFoundException("Invalid,Flight Id not Found");
	}
	
	//iv)update Flight details
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(Flight flight) {
		ResponseStructure<Flight> rs=new ResponseStructure<>();

            if(flight.getId()==null) 
            	throw new NullPointerException("Enter Valid Flight Id");
          	 
	     		 Optional<Flight> opt=fdao.getFlightById(flight.getId());

              if(opt.isPresent()) {
            	  rs.setStatusCode(HttpStatus.OK.value());
     	    	 rs.setMessage("Flight details are updated");
     	    	 rs.setData(fdao.saveFlight(flight));
     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
              }
              else
            	  throw new IdNotFoundException("Invalid ,Id is not Found");
}
	
	//v)delete Flight details
	public ResponseEntity<ResponseStructure<String>> deleteFlight(int id) {
		ResponseStructure<String> rs=new ResponseStructure<>();
		    Optional<Flight> opt=fdao.getFlightById(id);
		    if(opt.isPresent()) {
		    	  rs.setStatusCode(HttpStatus.OK.value());
	     	    	 rs.setMessage("Flight details deleted");
	     	    	 fdao.deleteFlight(opt.get());
	     	    	 rs.setData("SUCCESS");
	     	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
		    }
		    else
          	  throw new IdNotFoundException("Invalid ,Id is not Found");
	}
	
	//vi)get Details in pagenaton ,sort format
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPagination_Sort(int pageNumber, int pageSize,
			String field) {
		ResponseStructure<Page<Flight>> rs=new ResponseStructure<>();
	    Page<Flight> l=fdao.getFlightByPagenatio_Sort(pageNumber,pageSize,field);
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
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsByAirlineName(String airlineName) {
		List<Flight> ls=fdao.getFlightDetailsByAirlineName(airlineName);
		ResponseStructure<List<Flight>> rs=new ResponseStructure<List<Flight>>();
		if(!ls.isEmpty()) {
			 rs.setStatusCode(HttpStatus.OK.value());
				rs.setMessage("Flight Informations founded");
				rs.setData(ls);
				return new ResponseEntity<>(rs,HttpStatus.OK);
		   }
		   else {
			  throw new NoRecordFoundException("No Information Founded");
		   }
		
	
	}
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsBysourceAndDestionation(String source,
			String destination) {
		List<Flight> ls=fdao.getFlightDetailsBysourceAndDestionation(source,destination);
		ResponseStructure<List<Flight>> rs=new ResponseStructure<List<Flight>>();
		if(!ls.isEmpty()) {
			 rs.setStatusCode(HttpStatus.OK.value());
				rs.setMessage("Flight Informations founded");
				rs.setData(ls);
				return new ResponseEntity<>(rs,HttpStatus.OK);
		   }
		   else {
			  throw new NoRecordFoundException("No Information Founded");
		   }
	}
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightDetailsByPrice(String price) {
		List<Flight> ls=fdao.getFlightDetailsByPrice(price);
		ResponseStructure<List<Flight>> rs=new ResponseStructure<List<Flight>>();
		if(!ls.isEmpty()) {
			 rs.setStatusCode(HttpStatus.OK.value());
				rs.setMessage("Flight Informations founded");
				rs.setData(ls);
				return new ResponseEntity<>(rs,HttpStatus.OK);
		   }
		   else {
			  throw new NoRecordFoundException("No Information Founded");
		   }
	}
	
	


}

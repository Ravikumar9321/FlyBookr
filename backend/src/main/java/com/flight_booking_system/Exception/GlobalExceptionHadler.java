package com.flight_booking_system.Exception;


import org.springframework.http.*;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flight_booking_system.DTO.ResponseStructure;



@ControllerAdvice
public class GlobalExceptionHadler extends ResponseEntityExceptionHandler  {
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.NOT_FOUND.value());
		b.setMessage("Failure");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRFE(NoRecordFoundException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.NOT_FOUND.value());
		b.setMessage("Failure");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ResponseStructure<String>> handleNPE(NullPointerException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.NOT_FOUND.value());
		b.setMessage("Failure");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSeatAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNSAE(NoSeatAvailableException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.NOT_FOUND.value());
		b.setMessage("FAILURE");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.NOT_FOUND);
	}

}
 
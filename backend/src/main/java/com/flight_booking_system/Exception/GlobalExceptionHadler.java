package com.flight_booking_system.Exception;


import org.springframework.http.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flight_booking_system.DTO.ResponseStructure;



@ControllerAdvice
public class GlobalExceptionHadler extends ResponseEntityExceptionHandler  {
	
	// 🔹 Handle validation errors (from @Valid)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getDefaultMessage())
                                .findFirst()
                                .orElse("Validation failed");

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("FAILURE");
        response.setData(errorMessage);

        return ResponseEntity.badRequest().body(response);
    }
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.NOT_FOUND.value());
		b.setMessage("FAILURE");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRFE(NoRecordFoundException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.NOT_FOUND.value());
		b.setMessage("FAILURE");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ResponseStructure<String>> handleNPE(NullPointerException e) {
		ResponseStructure<String> b=new ResponseStructure<>();
		b.setStatusCode(HttpStatus.BAD_REQUEST.value());
		b.setMessage("FAILURE");
		b.setData(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(b,HttpStatus.BAD_REQUEST);
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
 
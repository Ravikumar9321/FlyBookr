package com.flight_booking_system.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flight_booking_system.DAO.User_Dao;
import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.Flight;
import com.flight_booking_system.Entity.UserInfo;
import com.flight_booking_system.Exception.NoRecordFoundException;

@Service
public class User_Service {
	
	@Autowired
	private User_Dao userdao;
	public ResponseEntity<ResponseStructure<UserInfo>> createUser(UserInfo user) {

		ResponseStructure<UserInfo> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("User Details Saved");
		rs.setData(userdao.createUser(user));
		return new ResponseEntity<>(rs,HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<UserInfo>>> getAllUser() {
		ResponseStructure<List<UserInfo>> rs=new ResponseStructure<>();
	     List<UserInfo> ls=userdao.getallUser();
	     if(ls.size()>0) {
	    	 rs.setStatusCode(HttpStatus.OK.value());
	    	 rs.setMessage("User details  retrieved");
	    	 rs.setData(ls);
	    	 return new ResponseEntity<>(rs,HttpStatus.OK);
	     }
	     else
	    	 throw new NoRecordFoundException("No Records Found");
	}
	
}
	
	



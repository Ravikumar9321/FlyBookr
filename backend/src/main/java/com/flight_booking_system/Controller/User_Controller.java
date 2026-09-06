package com.flight_booking_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight_booking_system.DTO.ResponseStructure;
import com.flight_booking_system.Entity.UserInfo;
import com.flight_booking_system.Service.User_Service;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/user")
public class User_Controller {
       @Autowired
       private User_Service service;
       
       public ResponseEntity<ResponseStructure<List<UserInfo>>> getAllUser(){
    	   return service.getAllUser();
       }
       
       public ResponseEntity<ResponseStructure<UserInfo>> createUser(@RequestBody UserInfo user){
    	   return service.createUser(user);
       }
       
}

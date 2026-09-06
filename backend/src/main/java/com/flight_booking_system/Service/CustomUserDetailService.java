package com.flight_booking_system.Service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flight_booking_system.Entity.UserInfo;
import com.flight_booking_system.Exception.NoRecordFoundException;
import com.flight_booking_system.Repository.User_Repository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class CustomUserDetailService  implements UserDetailsService{
    private  User_Repository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		   UserInfo user= repository.findByEmail(email).orElseThrow(()->new NoRecordFoundException("not found"));
		   
		return User.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities("ROLE_USER")
				.build();
	}

}

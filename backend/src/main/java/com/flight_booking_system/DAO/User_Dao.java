package com.flight_booking_system.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.flight_booking_system.Entity.UserInfo;
import com.flight_booking_system.Repository.User_Repository;

@Repository
public class User_Dao {
	@Autowired
	private User_Repository repository;
	
	@Autowired
	private PasswordEncoder passswordEncoder;

	public UserInfo createUser(UserInfo user) {
		    user.setPassword(passswordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	public List<UserInfo> getallUser() {
		return repository.findAll();
	}

}

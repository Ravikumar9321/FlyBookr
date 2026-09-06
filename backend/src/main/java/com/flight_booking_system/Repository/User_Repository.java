package com.flight_booking_system.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flight_booking_system.Entity.UserInfo;

public interface User_Repository  extends JpaRepository<UserInfo, Integer>{
     Optional<UserInfo> findByEmail(String email);
}

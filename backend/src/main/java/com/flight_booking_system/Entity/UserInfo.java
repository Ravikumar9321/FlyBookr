package com.flight_booking_system.Entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "User_Table")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String password;
	

}

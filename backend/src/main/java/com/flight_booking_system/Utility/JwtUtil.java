package com.flight_booking_system.Utility;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String SECRET="udgwfgfhuhuinainfdowefh458y ffbfsifsfsfijbjfewfeqgfq";
	private Key secretkey=Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000;
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
		        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
				.signWith(secretkey,SignatureAlgorithm.HS256)
				.compact();
	}
	public boolean validateToken(String token) {
		try {
			extractEmail(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public String extractEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretkey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
	}
	

}

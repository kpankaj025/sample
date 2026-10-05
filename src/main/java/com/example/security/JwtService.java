package com.example.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final long EXPIRATION_TIME=15*60*1000;
	private static final long EXPIRATION_REFRESH_TIME=60*60*1000;
	
	
	private static final String SECRET="gcfhjiodswhiuybwndnweijfoieeeeeeeeejniouj689234joinksdjml;k3463";
	
	// generate token
	public String generateToken (String email,boolean isAccessToken) {
		long expTime=isAccessToken?EXPIRATION_TIME:EXPIRATION_REFRESH_TIME;
		String userType=isAccessToken?"Bearer":"refresh_token";
		
		String token=Jwts.builder()
				.setSubject(email)
				.claim("typ", userType)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expTime))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()),SignatureAlgorithm.HS256)
				.compact();
		return token;
	}
	
	
	// get UserName
	
	public String getUserName(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
		.parseClaimsJws(token).getBody().getSubject();
	}
	
	// check expiration
	
	public boolean isTokenExpired(String token) {
		
		Date expiration =Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
		.parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date());
		
	}
	
	
	// validate token
	
	public boolean validateToken(String token) {
		if(this.isTokenExpired(token)) {
			return false;
		}
		try {
			Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
			.parseClaimsJws(token);
			return true;
		}catch(JwtException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// is access token
	
	public boolean isAccessToken(String token) {
		Claims claims=Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
		.parseClaimsJws(token).getBody();
		
		String type=(String) claims.get("typ");
		return type.equals("Bearer");
	}
	
	// is refresh token
	
	public boolean isRefreshToken(String token) {
		Claims claims=Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
		.parseClaimsJws(token).getBody();
		
		String type=(String) claims.get("typ");
		return type.equals("refr5esh_token");
	}
	
	
	
	
}

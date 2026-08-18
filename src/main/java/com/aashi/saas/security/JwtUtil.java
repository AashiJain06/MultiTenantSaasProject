package com.aashi.saas.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final String SECRET = "mysecretkeysecretkeymykeysecret123";
	private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
	
	public static String generateToken(String username, Long tenantId) {

	    return Jwts.builder()
	            .subject(username)
	            .claim("tenantId", tenantId)
	            .issuedAt(new Date())   // Current time
	            .expiration(new Date(System.currentTimeMillis() + 900000)) // 15 min
	            .signWith(key)
	            .compact();
	}
	public static Claims extractClaims(String token) {
	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
	public static String extractUsername(String token)
	{
		return extractClaims(token).getSubject();
	}
	public static Long extractTenantId(String token)
	{
		return extractClaims(token).get("tenantId",Long.class);
	}
	public static boolean isValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

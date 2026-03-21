package com.aashi.saas.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.dto.LoginRequest;
import com.aashi.saas.dto.LoginResponse;
import com.aashi.saas.security.CustomUserDetails;
import com.aashi.saas.security.CustomUserDetailsService;
import com.aashi.saas.security.JwtUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authManager;
	private final CustomUserDetailsService service;
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request)
	{
		
		Authentication auth = authManager.authenticate(
			    new UsernamePasswordAuthenticationToken(
			        request.getUsername(),
			        request.getPassword()
			    )
			);

			CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

			String token = JwtUtil.generateToken(user.getUsername(),user.getTenantId());
	return new LoginResponse(token);
	}
	
	
	

}

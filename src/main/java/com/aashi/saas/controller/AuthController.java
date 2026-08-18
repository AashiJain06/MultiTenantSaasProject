package com.aashi.saas.controller;

import org.springframework.http.ResponseEntity;
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
import com.aashi.saas.dto.RefreshRequest;
import com.aashi.saas.entity.RefreshToken;
import com.aashi.saas.security.CustomUserDetails;
import com.aashi.saas.security.CustomUserDetailsService;
import com.aashi.saas.security.JwtUtil;
import com.aashi.saas.service.RefreshTokenService;
import com.aashi.saas.utility.UtilityClass;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authManager;
	private final CustomUserDetailsService service;
	private final RefreshTokenService refreshTokenService;
	
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
			RefreshToken refreshToken =
		            refreshTokenService.createRefreshToken(user.getUserId()
		            		);
			return new LoginResponse(token , refreshToken.getToken());
	}
	@PostMapping("/refresh-token")
	public String refresh(
	        @RequestBody RefreshRequest request){

	    RefreshToken token =
	            refreshTokenService.verify(
	                    request.getRefreshToken());

	    String accessToken = JwtUtil.generateToken(token.getUser().getUsername(),
                token.getUser().getTenant().getId());

	    return accessToken;
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout() {

	    CustomUserDetails currentUser = UtilityClass.getCurrentUser();

	    refreshTokenService.deleteByUser(currentUser.getUserId());

	    return ResponseEntity.ok("Logged out");
	}
	
}

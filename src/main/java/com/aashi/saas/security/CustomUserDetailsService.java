package com.aashi.saas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aashi.saas.entity.User;
import com.aashi.saas.exception.UserNotFoundException;
import com.aashi.saas.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service

public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Loading user: " + username);
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user not found with"));
		
		return new CustomUserDetails(user);
	}
    
	
}

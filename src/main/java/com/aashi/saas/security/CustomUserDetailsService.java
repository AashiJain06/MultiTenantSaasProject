package com.aashi.saas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aashi.saas.entity.User;
import com.aashi.saas.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service

public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Loading user: " + username);
		
		User user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
		    System.out.println("❌ USER NOT FOUND IN DATABASE");
		    throw new UsernameNotFoundException("User not found");
		}
		System.out.println("DB password: " + user.getPassword());
		return new CustomUserDetails(user);
	}
    
	
}

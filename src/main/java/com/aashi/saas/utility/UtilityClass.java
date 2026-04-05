package com.aashi.saas.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.aashi.saas.security.CustomUserDetails;

public class UtilityClass {
	public static CustomUserDetails  getCurrentUser()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    	CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
       return userDetails;
    	
	}

}

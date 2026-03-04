package com.aashi.saas.security;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import com.aashi.saas.entity.User;




public class CustomUserDetails implements UserDetails{
	
	private  User user;
	public CustomUserDetails(User user)
	{
		this.user = user;
	}
	public Long getTenantId()
	{
		return user.getTenant().getId();
	}
	public Long getUserId()
	{
		return user.getId();
	}
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	

}

package com.aashi.saas.security;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aashi.saas.context.TenantContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
	
	private final CustomUserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Jwt Filter called");
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
		    String name = headers.nextElement();
		    System.out.println(name + " = " + request.getHeader(name));
		}
		String header = request.getHeader("Authorization");
		System.out.println(header);
		if(header!=null && header.startsWith("Bearer "))
		{
			String token = header.substring(7);
			System.out.println(token);
			if(JwtUtil.isValid(token))
			{
				String username = JwtUtil.extractUsername(token);
				System.out.println("Username:"+username);
				Long tenantId = JwtUtil.extractTenantId(token);
				System.out.println("TenantId :"+tenantId);
				var userDetails = userDetailService.loadUserByUsername(username);
				System.out.println("User is loaded sucessfully");
				var auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				System.out.println("User is Authenticate");
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				System.out.println("User is authorised");
				TenantContext.setTenantId(tenantId);
				System.out.println("TenantId set Sucessfully");
			}
		}
		filterChain.doFilter(request,response);
	}
	
}

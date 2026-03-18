package com.aashi.saas.filter;

import java.io.IOException;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.security.CustomUserDetails;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component

public class TenantFilter extends OncePerRequestFilter{
	
    @PersistenceContext
	private EntityManager entityManager;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		try
		{
		   var auth = SecurityContextHolder.getContext().getAuthentication();
		   if(auth!=null && auth.getPrincipal() instanceof CustomUserDetails user)
		   {
			  TenantContext.setTenantId((long)user.getTenantId()); 
			  
//		      Session session = entityManager.unwrap(Session.class);
//		     Filter filter = session.enableFilter("tenantFilter");
//		      
//		      filter.setParameter("tenantId",TenantContext.getTenantId());
//		      System.out.println("Filter Enabled: " + (filter != null));
//		      System.out.println("Tenant ID: " + TenantContext.getTenantId());
		   }
		   chain.doFilter(request, response);
	}
	finally
	{
		TenantContext.clear();
	}
 
 }
}

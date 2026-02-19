package com.aashi.saas.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.aashi.saas.context.TenantContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TenantFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		try
		{
		String tenantHeader = httpRequest.getHeader("X-Tenant-Id");
		if(tenantHeader!=null)
			TenantContext.setTenantId(Long.parseLong(tenantHeader));
		
		chain.doFilter(request, response);
	}
	finally
	{
		TenantContext.clear();
	}
 
 }
}

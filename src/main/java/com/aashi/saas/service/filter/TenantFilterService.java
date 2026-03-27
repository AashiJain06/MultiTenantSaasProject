package com.aashi.saas.service.filter;

import org.hibernate.Session;

import com.aashi.saas.context.TenantContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;




public abstract class  TenantFilterService {
	@PersistenceContext
	protected EntityManager entityManager;
	protected void enableTenantFilter()
	{
		Session session = entityManager.unwrap(Session.class);
		session.enableFilter("tenantFilter")
		.setParameter("tenantId",TenantContext.getTenantId());
	}

}

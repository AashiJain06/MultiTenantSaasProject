package com.aashi.saas.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.entity.Task;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.repository.TaskRepository;
import com.aashi.saas.repository.TenantRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
	
	private final TenantRepository tenantRepository;
	private final TaskRepository taskRepository;
	private final EntityManager entityManager;
	
	public List<Task> getAlltask()
	{
		Session session = entityManager.unwrap(Session.class);
		session.enableFilter("tenantFilter")
		.setParameter("tenantId", TenantContext.getTenantId());
		return taskRepository.findAll();
	}
	public Task createTask(Task task) {
        Long tenantId = TenantContext.getTenantId();
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        task.setTenant(tenant);
        return taskRepository.save(task);
    }

}

package com.aashi.saas.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.entity.Task;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.repository.TaskRepository;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.service.filter.TenantFilterService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService extends TenantFilterService{
	
	private final TenantRepository tenantRepository;
	private final TaskRepository taskRepository;
	
	public List<Task> getAlltask()
	{
		enableTenantFilter();
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

package com.aashi.saas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	List<Task> findBYProjectId(Long projectId);
	List<Task> findByAssigned_user(Long userId);

}

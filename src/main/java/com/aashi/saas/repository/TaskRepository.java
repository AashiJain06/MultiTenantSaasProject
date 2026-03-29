package com.aashi.saas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	Page<Task> findByProjectId(Long projectId,Pageable pageable);
	Page<Task> findByAssignedUser_Id(Long userId,Pageable pageable);

}

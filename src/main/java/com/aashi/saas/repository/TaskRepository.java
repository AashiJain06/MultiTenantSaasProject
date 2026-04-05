package com.aashi.saas.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.Task;
import com.aashi.saas.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long>{
	Page<Task> findByProjectId(Long projectId,Pageable pageable);
	
	Page<Task> findByProjectIdAndAssignedUser_Id(Long projectId,Long userId,Pageable pageable);
	
	Page<Task> findByProjectIdAndTitleContaining(Long projectId,String keyword, Pageable pageable);
	
	Page<Task> findByProjectIdAndStatus(Long projectId , TaskStatus status, Pageable pageable);
	
	long countByStatus(TaskStatus status);
	
	long countByProjectId(Long projectId);
	
	long countByProjectIdAndStatus(Long projectID, TaskStatus status);
	

}

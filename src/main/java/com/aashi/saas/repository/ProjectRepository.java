package com.aashi.saas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	

}

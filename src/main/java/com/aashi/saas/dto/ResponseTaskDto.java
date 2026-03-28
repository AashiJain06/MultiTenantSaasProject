package com.aashi.saas.dto;

import com.aashi.saas.entity.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ResponseTaskDto {
	
	private Long id;
	private String title;
	private String deiscription;
	private TaskStatus status;
	private Long userId;
	private Long projectID;
	public ResponseTaskDto(Long id, String title, String deiscription, TaskStatus taskStatus, Long userId,Long projectID) {
		super();
		this.id = id;
		this.title = title;
		this.deiscription = deiscription;
		this.status = taskStatus;
		this.userId = userId;
		this.projectID = projectID;
	}
		

}

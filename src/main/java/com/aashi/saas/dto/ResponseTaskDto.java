package com.aashi.saas.dto;

import com.aashi.saas.entity.TaskStatus;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ResponseTaskDto {
	
	private Long id;
	private String title;
	private String deiscription;
	private TaskStatus status;
	private Long assignedUserId;
	private Long projectID;
	public ResponseTaskDto(Long id, String title, String deiscription, TaskStatus taskStatus, Long userId,Long projectID) {
		super();
		this.id = id;
		this.title = title;
		this.deiscription = deiscription;
		this.status = taskStatus;
		this.assignedUserId = userId;
		this.projectID = projectID;
	}
		

}

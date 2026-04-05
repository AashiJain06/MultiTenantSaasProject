package com.aashi.saas.dto;

import com.aashi.saas.entity.TaskStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Task response data")
public class ResponseTaskDto {
	
	@Schema(description = "Task response data")
	private Long id;
	
	@Schema(description = "Task ID", example = "1")
	private String title;
	
	 @Schema(description = "Task title", example = "Fix login bug")
	private String deiscription;
	
	@Schema(description = "Task status", example = "IN_PROGRESS")
	private TaskStatus status;
	
	@Schema(description = "Assigned user ID", example = "5")
	private Long assignedUserId;
	
	@Schema(description = "project Id",example = "2")
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

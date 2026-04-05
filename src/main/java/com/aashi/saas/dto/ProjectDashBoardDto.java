package com.aashi.saas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDashBoardDto {
	
	private Long projectId;
	private Long totalTasks;
	private Long completedTasks;
	private Long todoTasks;
	private Long inProgressTasks;
	
	

}

package com.aashi.saas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardDto {
	
	private Long totalProject;
	private Long totalTasks;
	private Long completedTask;
	

}

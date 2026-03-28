package com.aashi.saas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTaskDto {
	@NotBlank(message="Ttile is required")
	private String title;
	
	
	private String description;
	
	@NotNull(message = "Project ID required")
	private Long projectId;
	
	@NotNull(message = "User ID required")
	private Long userId;
	

}

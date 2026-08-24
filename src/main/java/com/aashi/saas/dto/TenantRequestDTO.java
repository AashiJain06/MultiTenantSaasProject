package com.aashi.saas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TenantRequestDTO {
	
	@NotBlank(message = "Tenant name is required")
    private String name;

}

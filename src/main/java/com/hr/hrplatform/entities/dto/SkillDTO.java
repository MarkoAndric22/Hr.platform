package com.hr.hrplatform.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SkillDTO {
	
	@NotBlank(message = "Skill name must not be blank")
	@Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters")
	private String name;

	public SkillDTO() {
		super();
	}

	public SkillDTO(
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}

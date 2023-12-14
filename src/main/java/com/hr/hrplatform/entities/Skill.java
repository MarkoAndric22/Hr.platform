package com.hr.hrplatform.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "skills")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "Skill name must not be blank")
	@Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters")
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "skill", cascade = CascadeType.REFRESH, orphanRemoval = true,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CandidateSkill> candidateSkills = new ArrayList<>();

	public Skill() {
		super();
	}

	public Skill(Integer id,
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name,
			List<CandidateSkill> candidateSkills) {
		super();
		this.id = id;
		this.name = name;
		this.candidateSkills = candidateSkills;
	}

	public Skill(
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name,
			List<CandidateSkill> candidateSkills) {
		super();
		this.name = name;
		this.candidateSkills = candidateSkills;
	}

	public Skill(
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CandidateSkill> getCandidateSkills() {
		return candidateSkills;
	}

	public void setCandidateSkills(List<CandidateSkill> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}

}

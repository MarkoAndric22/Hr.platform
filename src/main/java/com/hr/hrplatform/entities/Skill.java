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

@Entity(name = "skills")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer skill_id;
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

	public Skill(Integer skill_id,
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name,
			List<CandidateSkill> candidateSkills) {
		super();
		this.skill_id = skill_id;
		this.name = name;
		this.candidateSkills = candidateSkills;
	}
	
	

	public Skill(Integer skill_id,
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name) {
		super();
		this.skill_id = skill_id;
		this.name = name;
	}

	public Skill(
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name,
			List<CandidateSkill> candidateSkills) {
		super();
		this.name = name;
		this.candidateSkills = candidateSkills;
	}

	public Integer getSkill_id() {
		return skill_id;
	}

	public void setSkill_id(Integer skill_id) {
		this.skill_id = skill_id;
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

	public Skill(
			@NotBlank(message = "Skill name must not be blank") @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters") String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Skill [skill_id=" + skill_id + ", name=" + name + ", candidateSkills=" + candidateSkills + "]";
	}

	
	
	
}

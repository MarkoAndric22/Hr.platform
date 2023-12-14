package com.hr.hrplatform.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name= "candidate_skill")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class CandidateSkill {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Skill skill;

	public CandidateSkill() {
		super();
	}

	public CandidateSkill(int id, Candidate candidate, Skill skill) {
		super();
		this.id = id;
		this.candidate = candidate;
		this.skill = skill;
	}

	public CandidateSkill(Candidate candidate, Skill skill) {
		super();
		this.candidate = candidate;
		this.skill = skill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

    
	
}

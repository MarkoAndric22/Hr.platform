package com.hr.hrplatform.Services;

import java.util.List;
import java.util.Optional;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.dto.CandidateDTO;

public interface CandidateService {
	
	public CandidateDTO add(CandidateDTO candidate)throws RESTError;
	public CandidateDTO modify(Integer id_candidate,CandidateDTO candidate)throws RESTError;
	public Candidate deleteCandidate(Integer id)throws RESTError;
	public List<CandidateDTO> searchCandidateByName(String name)throws RESTError;
	public List<CandidateDTO> searchCandidateBySkill(String skillName)throws RESTError;
	public Optional<Candidate> getById(Integer id)throws RESTError;
	public Iterable<Candidate> getAll();
	
}

package com.hr.hrplatform.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.hrplatform.Services.CandidateService;
import com.hr.hrplatform.Services.CandidateSkillService;
import com.hr.hrplatform.controllers.CandidatesController;
import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.CandidateSkill;
import com.hr.hrplatform.entities.Skill;
import com.hr.hrplatform.entities.dto.CandidateDTO;
import com.hr.hrplatform.repositories.CandidateRepository;
import com.hr.hrplatform.repositories.CandidateSkillRepository;
import com.hr.hrplatform.repositories.SkillsRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(CandidatesController.class)
public class CandidateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CandidateRepository candidateRepository;

	@MockBean
	CandidateSkillRepository candidateSkillRepository;

	@MockBean
	CandidateSkillService candidateSkillService;

	@MockBean
	SkillsRepository skillsRepository;

	@MockBean
	private CandidateService candidateService;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void addCandidate_successTest() throws Exception {
		String dateString = "2022-08-31";
		LocalDate localDate = LocalDate.parse(dateString);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CandidateDTO candidateDTO = new CandidateDTO("Gavrilo", date, "6633995522", "gavrilo@gmail.com");
		when(candidateService.add(candidateDTO)).thenReturn(candidateDTO);
		this.mockMvc.perform(post("/hrplatform/candidate").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(candidateDTO))).andExpect(status().isOk());
	}

	@Test
	public void removeCandidate_successTest() throws Exception {
		String dateString = "2022-08-31";
		LocalDate localDate = LocalDate.parse(dateString);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Integer candidateId = 1;
		Candidate candidate = new Candidate(1, "Gavrilo", date, "6633995522", "gavrilo@email.com");
		when(candidateService.deleteCandidate(candidateId)).thenReturn(candidate);
		this.mockMvc.perform(delete("/hrplatform/candidate/{id}", candidateId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void removeCandidate_notFoundTest() throws Exception {
		Integer candidateId = -1;
		 when(candidateService.deleteCandidate(candidateId)).thenThrow(new RESTError(404, "Candidate not exists"));
		this.mockMvc.perform(delete("/hrplatform/candidate/{id}", candidateId)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$", containsString("Candidate not exists")));
	}

	@Test
	public void testGetCandidateByNameSuccess() throws Exception {
		String name = "Marko";
		String dateString = "2022-08-31";
		LocalDate localDate = LocalDate.parse(dateString);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CandidateDTO candidateDTO1 = new CandidateDTO(name, date, "6633995522", "sss@gmail.com");
		CandidateDTO candidateDTO2 = new CandidateDTO(name, date, "6633995522", "mmm@gmail.com");
		CandidateDTO candidateDTO3 = new CandidateDTO(name, date, "6633995522", "ggg@gmail.com");
		List<CandidateDTO> candidates = Arrays.asList(candidateDTO1, candidateDTO2, candidateDTO3);
		when(candidateService.searchCandidateByName(name))
				.thenReturn(List.of(candidateDTO1, candidateDTO2, candidateDTO3));
		given(candidateService.searchCandidateByName(name)).willReturn(candidates);

		mockMvc.perform(get("/hrplatform/candidate/name").param("name", name)).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(candidates)));
	}

	@Test
	public void testGetCandidateByNameNotFound() throws Exception {
		String name = "Mika";
		when(candidateService.searchCandidateByName(name)).thenThrow(new RESTError(404, "Candidate not exists"));

		mockMvc.perform(get("/hrplatform/candidate/name").param("name", name)).andExpect(status().isNotFound())
				.andExpect(content().string("Candidate not exists"));
	}

	@Test
	public void getCandidateBySkill_successTest() throws Exception {
		String skill = "Java";
		String dateString = "2022-08-31";
		LocalDate localDate = LocalDate.parse(dateString);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CandidateDTO candidateDTO = new CandidateDTO("Gavrilo", date, "6633995522", "gavrilo@gmail.com");
		List<CandidateDTO> candidateDTOList = Collections.singletonList(candidateDTO);
		when(candidateService.searchCandidateBySkill(skill)).thenReturn(candidateDTOList);
		this.mockMvc.perform(get("/hrplatform/candidate/skill").param("skill", skill)).andExpect(status().isOk())
		.andExpect(content().json(objectMapper.writeValueAsString(candidateDTOList)));
	}

	@Test
	public void getCandidateBySkill_candidateNotFoundTest() throws Exception {
		String skill = "css";
		when(candidateService.searchCandidateBySkill(skill)).thenThrow(new RESTError(1, "Candidate not exists"));
		this.mockMvc.perform(get("/hrplatform/candidate/skill").param("skill", skill)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$", equalTo("Candidate not exists")));
	}

	@Test
	public void addCandidate_validationFailureTest() throws Exception {
		String dateString = "2024-01-01";
		LocalDate localDate = LocalDate.parse(dateString);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CandidateDTO candidateDTO = new CandidateDTO("", date, null, "");

		MvcResult result = mockMvc
				.perform(post("/hrplatform/candidate").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(candidateDTO)))
				.andExpect(status().isBadRequest()).andReturn();

		String response = result.getResponse().getContentAsString();
		assertTrue(response.contains("Candidate name must not be blank"));
		assertTrue(response.contains("Date of birth must be in the past"));
		assertTrue(response.contains("Contact number must not be null"));
		assertTrue(response.contains("Email must not be blank"));
	}
	
	@Test
    public void addSkillToCandidate_successTest() throws Exception {
        Integer id = 1;
        Integer skillId = 1;

        Candidate candidate = new Candidate();
        candidate.setId(id);

        Skill skill = new Skill();
        skill.setId(skillId);

        CandidateSkill candidateSkill = new CandidateSkill();
        candidateSkill.setCandidate(candidate);
        candidateSkill.setSkill(skill);

        when(candidateSkillService.addSkilltoKandidat(id, skillId)).thenReturn(candidateSkill);

        mockMvc.perform(post("/hrplatform/candidate/ADDskill")
        		.param("candidate_id", id.toString())
        		.param("skill_id", skillId.toString()))
        		.andExpect(status().isOk())
        		 .andExpect(jsonPath("$.candidate.candidate_id", is(candidateSkill.getCandidate().getId())))
                 .andExpect(jsonPath("$.skill.skill_id", is(candidateSkill.getSkill().getId())));
     }

    @Test
    public void removeSkilltoKandidate_successTest() throws Exception {
    	Integer candidateId = 1;
        Integer skillId = 1;

        Candidate candidate = new Candidate();
        candidate.setId(candidateId);

        Skill skill = new Skill();
        skill.setId(skillId);

        CandidateSkill candidateSkill = new CandidateSkill();
        candidateSkill.setCandidate(candidate);
        candidateSkill.setSkill(skill);

        when(candidateSkillService.removeSkilltoKandidat(candidateId, skillId)).thenReturn(candidateSkill);

        mockMvc.perform(delete("/hrplatform/candidate")
                .param("candidate_id", candidateId.toString())
                .param("skill_id", skillId.toString()))
                .andExpect(status().isOk());
    }

}



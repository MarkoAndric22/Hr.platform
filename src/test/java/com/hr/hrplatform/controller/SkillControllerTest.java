package com.hr.hrplatform.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.hrplatform.Services.SkillService;
import com.hr.hrplatform.controllers.SkillsController;
import com.hr.hrplatform.entities.dto.SkillDTO;
import com.hr.hrplatform.repositories.SkillsRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(SkillsController.class)
@AutoConfigureMockMvc
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillsRepository skillsRepository;

    @MockBean
    private SkillService skillService;
    
    @Autowired
    private ObjectMapper objectMapper;
    

    @Test
	public void addSkill_successTest() throws Exception {
		SkillDTO skillDTO = new SkillDTO("Java");
		when(skillService.add(skillDTO)).thenReturn(skillDTO);
		this.mockMvc
				.perform(post("/hrplatform/skill").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(skillDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addSkill_validationFailureTest() throws Exception {
	    SkillDTO skillDTO = new SkillDTO("");

	    MvcResult result = mockMvc.perform(post("/hrplatform/skill")
	            .contentType(MediaType.APPLICATION_JSON_VALUE)
	            .content(objectMapper.writeValueAsString(skillDTO)))
	            .andExpect(status().isBadRequest())
	            .andReturn();

	    String response = result.getResponse().getContentAsString();
	    assertTrue(response.contains("Skill name must not be blank"));
	}


}


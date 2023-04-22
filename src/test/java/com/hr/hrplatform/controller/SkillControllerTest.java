package com.hr.hrplatform.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import com.hr.hrplatform.controllers.util.RESTError;
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
    public void addSkill_skillAlreadyExistsTest() throws Exception {

    	 SkillDTO skillDTO = new SkillDTO("Java");
    	 skillService.add(skillDTO);
    	 SkillDTO skillDTO1 = new SkillDTO("Java");
    	    when(skillService.add(skillDTO1)).thenThrow(new RESTError(1, "Skill already exists"));
    	    this.mockMvc
    	        .perform(post("/hrplatform/skill")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(skillDTO)))
    	        .andExpect(status().isConflict())
    	        .andExpect(content().string("Skill already exists"));
    	}
  
    
    @Test
    void addSkill_skillAlreadyExistsTest3() throws Exception {
        // kreiranje lažnih podataka
        SkillDTO skillDTO = new SkillDTO("Java");

        // postavljanje mock metode
        when(skillService.add(any(SkillDTO.class))).thenThrow(new RESTError(1, "Skill already exists"));

        // slanje zahtjeva POST /skills i očekivanje greške 409 Conflict
        mockMvc.perform(post("/hrplatform/skill")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isConflict());
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


//@SpringBootTest
//@AutoConfigureMockMvc
//@WebAppConfiguration
//public class SkillControllerTest {
//	
//	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//			MediaType.APPLICATION_JSON.getSubtype());
//	private static MockMvc mockMvc;
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//
//	private static List<Skill> skills = new ArrayList<>();
//
//	@Autowired
//	private SkillsRepository skillsRepository;
//
//	private static boolean dbInit = false;
//	
//	
//
//	@BeforeEach
//	public void setup() throws Exception {
//		if (!dbInit) {
//			mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//			skills.add(this.skillsRepository.save(new Skill("Java")));
//			skills.add(this.skillsRepository.save(new Skill("Javacskript")));
//			skills.add(this.skillsRepository.save(new Skill("Payton")));
//			dbInit = true;
//		}
//	}
//	
//	@Test
//	public void skillControllerNotFound()throws Exception {
//		
//		mockMvc.perform(get("/hrplatform/skill/1"))
//		.andExpect(status().isNotFound());
//	}
//	
//	@Test
//	public void skillserviceFound()throws Exception {
//		
//		mockMvc.perform(get("/hrplatform/skill/2"))
//		.andExpect(status().isOk());
//	}
//	
//	@Test
//	public void readSingleSkill()throws Exception{
//		mockMvc.perform(get("/hrplatform/skill/"
//	+ this.skills.get(2).getSkill_id()))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.id",is(this.skills.get(1).getSkill_id().intValue())));
//	}
//	
//	@Test
//	public void readAllSkills() throws Exception {
//	mockMvc.perform(get("/hrplatform/skill/1"))
//	.andExpect(status().isOk())
//	.andExpect(content().contentType(contentType))
//	.andDo(MockMvcResultHandlers.print());
//	}
//	
//	@Test
//	public void createSkill() throws Exception  {
//		this.mockMvc.perform(post("/hrplatform/skill/").param("name", "Php")).andExpect(status().isOk()).andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name", is("Java")));
//	}
//	
//	@Test
//	public void updateSkill()throws Exception{
//		mockMvc.perform(put("/hrplatform/skill/"
//				+ this.skills.get(0).getSkill_id())
//				.param("name", "Payton"))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(contentType))
//				.andExpect(jsonPath("$.name", is("Payton")));
//				
//	}
//	
//	@Test
//	public void deleteSkill() throws Exception {
//	mockMvc.perform(delete("/hrplatform/skill/"
//	+ this.skills.get(0).getSkill_id()))
//	.andExpect(status().isOk())
//
//	.andExpect(content().contentType(contentType))
//	.andExpect(jsonPath("$.id",
//	is(this.skills.get(0).getSkill_id().intValue())));
//	}
//}

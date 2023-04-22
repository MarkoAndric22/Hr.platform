package com.hr.hrplatform.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hr.hrplatform.Services.SkillService;
import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.dto.SkillDTO;
import com.hr.hrplatform.mappers.SkillMapper;
import com.hr.hrplatform.repositories.SkillsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class SkillServiceTest {
	
	@Autowired
	SkillService skillService;
	@Autowired
	SkillMapper skillMapper;
	@Autowired
	SkillsRepository skillsRepository;
	
	@Test
	public void testAddSkill() throws Exception {
		SkillDTO skilltoAdd=new SkillDTO();
		skilltoAdd.setName("PHP");
		
		SkillDTO addedSkill= skillService.add(skilltoAdd);
		assertNotNull(addedSkill);		
	}
	
	@Test
	public void testAddExistingSkill() throws Exception {
		SkillDTO skilltoAdd=new SkillDTO();
		skilltoAdd.setName("Java");
		
		assertThrows(RESTError.class, () -> {
		    skillService.add(skilltoAdd);
		});	
	}

}

package com.hr.hrplatform.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.hr.hrplatform.Services.CandidateServiceImpl;
import com.hr.hrplatform.Services.CandidateSkillService;
import com.hr.hrplatform.Services.SkillService;
import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.CandidateSkill;
import com.hr.hrplatform.entities.dto.CandidateDTO;
import com.hr.hrplatform.mappers.CandidateMapper;
import com.hr.hrplatform.mappers.SkillMapper;
import com.hr.hrplatform.repositories.CandidateRepository;
import com.hr.hrplatform.repositories.SkillsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CandidateServiceTest {

	@Autowired
	CandidateServiceImpl candidateService;
	@Autowired
	CandidateMapper candidateMapper;
	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	SkillsRepository skillRepository;
	@Autowired 
	SkillService skillService;
	@Autowired
	CandidateSkillService candidateSkillService;
	@Autowired
	SkillMapper skillMapper;

	@Test
	public void testAddCandidate() throws Exception {
		String dateString = "1990-08-18";
		LocalDate localDate = LocalDate.parse(dateString);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CandidateDTO candidateToAdd = new CandidateDTO();
		candidateToAdd.setName("Marko");
		candidateToAdd.setDate_of_birth(date);
		candidateToAdd.setContact_number("6633995522");
		candidateToAdd.setEmail("markoistori@gmail.com");

		CandidateDTO addedCandidate = candidateService.add(candidateToAdd);

		assertNotNull(addedCandidate);
	}
	
	@Test
	public void testAddExistingCandidate() throws Exception {
	    String dateString = "2020-04-15";
	    LocalDate localDate = LocalDate.parse(dateString);
	    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    CandidateDTO candidateToAdd = new CandidateDTO();
	    candidateToAdd.setName("Marko");
	    candidateToAdd.setDate_of_birth(date);
	    candidateToAdd.setContact_number("6633995522");
	    candidateToAdd.setEmail("markoistorija90@gmail.com");

	    assertThrows(RESTError.class, () -> candidateService.add(candidateToAdd));
	}

	@Test
	public void testDeleteCandidate() throws RESTError {
		Integer id = 7;
		Candidate candidate = candidateRepository.findById(id).get();

		Candidate deletedCandidate = candidateService.deleteCandidate(candidate.getId());
		assertEquals(candidate.getId(), deletedCandidate.getId());
		assertEquals(candidate.getName(), deletedCandidate.getName());
		assertEquals(candidate.getDate_of_birth(), deletedCandidate.getDate_of_birth());
		assertEquals(candidate.getContact_number(), deletedCandidate.getContact_number());
		assertEquals(candidate.getEmail(), deletedCandidate.getEmail());

	}

	@Test
	public void testDeleteCandidate_NotExist() throws RESTError {
		Integer id = 2;
		assertThrows(RESTError.class, () -> {
			candidateService.deleteCandidate(id);
		});
	}

	@Test
	public void testSearchCandidateByName() throws RESTError {
		String name = "Marko";
		List<CandidateDTO> candidates = candidateService.searchCandidateByName(name);

		assertFalse(candidates.isEmpty());
		assertTrue(candidates.stream().anyMatch(c -> c.getName().equals(name)));
		System.out.println(candidates);

	}

	@Test
	public void testSearchCandidateByName_NotExist() throws RESTError {
		String name = "Janko";

		assertThrows(RESTError.class, () -> {
			candidateService.searchCandidateByName(name);
		});

	}

	@Test
	public void testSearchCandidateBySkill() throws RESTError {
		String skillName = "javascript";
		List<CandidateDTO> candidates = candidateService.searchCandidateBySkill(skillName);

		List<Candidate> candidatesEntity = candidates.stream()
				.map(dto -> candidateRepository.findByEmail(dto.getEmail())).filter(Objects::nonNull)
				.collect(Collectors.toList());

		assertTrue(candidatesEntity.stream().anyMatch(
				c -> c.getCandidateSkills().stream().anyMatch(cs -> cs.getSkill().getName().equals(skillName))));
		System.out.println(candidatesEntity);

	}

	@Test
	public void testSearchCandidateBySkill_NotExist() throws RESTError {
		String skillName = "Java";

		assertThrows(RESTError.class, () -> {
			candidateService.searchCandidateBySkill(skillName);
		});

	}

	@Test
	public void testAddSkilltoKandidat() throws RESTError {
		Integer candidateId = 7;
		Integer skillId = 94;

		CandidateSkill addedCandidateSkill = candidateSkillService.addSkilltoKandidat(candidateId, skillId);

		assertNotNull(addedCandidateSkill);
	}
	
	@Test
	public void testAddSkilltoKandidat_CandidateNotExists() {
	    Integer idCandidate = 999;
	    Integer idSkill = 8;
	    assertThrows(RESTError.class, () -> {
	        candidateSkillService.addSkilltoKandidat(idCandidate, idSkill);
	    });
	}

	@Test
	public void testAddSkilltoKandidat_SkillNotExists() {
	    Integer idCandidate = 7;
	    Integer idSkill = 999;
	    assertThrows(RESTError.class, () -> {
	    	candidateSkillService.addSkilltoKandidat(idCandidate, idSkill);
	    });
	}

	@Test
	public void testAddSkilltoKandidat_AlreadyHasSkill() {
	    Integer idCandidate = 7;
	    Integer idSkill = 8;
	    assertThrows(RESTError.class, () -> {
	    	candidateSkillService.addSkilltoKandidat(idCandidate, idSkill);
	    });
	}


	@Test
	public void testRemoveSkilltoKandidat() throws RESTError {
		Integer candidateId = 7;
		Integer skillId = 8;

		CandidateSkill removedCandidateSkill = candidateSkillService.removeSkilltoKandidat(candidateId, skillId);

		assertNotNull(removedCandidateSkill);
		assertEquals(candidateId, removedCandidateSkill.getCandidate().getId());
		assertEquals(skillId, removedCandidateSkill.getSkill().getId());
	}
	
	@Test
	public void testRemoveSkilltoKandidat_CandidateNotExists() {
	    Integer idCandidate = 999;
	    Integer idSkill = 8;
	    assertThrows(RESTError.class, () -> {
	        candidateSkillService.removeSkilltoKandidat(idCandidate, idSkill);
	    });
	}

	@Test
	public void testRemoveSkilltoKandidat_SkillNotExists() {
	    Integer idCandidate = 7;
	    Integer idSkill = 999;
	    assertThrows(RESTError.class, () -> {
	    	candidateSkillService.removeSkilltoKandidat(idCandidate, idSkill);
	    });
	}
	
	@Test
	public void testRemoveSkilltoKandidat_CandidateAndSkillNotLinked() {
	    Integer candidateId = 7;
	    Integer skillId = 94; 
	    assertThrows(RESTError.class, () -> {
	        candidateSkillService.removeSkilltoKandidat(candidateId, skillId);
	    });
	}


}

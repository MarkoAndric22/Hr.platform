package com.hr.hrplatform.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hr.hrplatform.entities.Skill;

@Repository
public interface SkillsRepository extends CrudRepository<Skill, Integer>{
	
	Skill findByName(String name);
	
	Skill findByNameIgnoreCase(String name);
	
	Boolean existsByName(String name); 

}

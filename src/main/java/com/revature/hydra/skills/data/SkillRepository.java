package com.revature.hydra.skills.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.hydra.skills.beans.SimpleSkill;

@Repository
public interface SkillRepository extends JpaRepository<SimpleSkill, Integer> {

	List<SimpleSkill> findAllByOrderBySkillNameAsc();

	List<SimpleSkill> findByActiveOrderBySkillIdAsc(boolean active);

	SimpleSkill findOneBySkillName(String skillName);

}

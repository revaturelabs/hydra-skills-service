package com.revature.hydra.skills.repo;

import com.revature.hydra.skills.model.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface SkillRepository extends CrudRepository<Skill, Integer> {
    Set<Skill> findByActiveIsTrue();
}
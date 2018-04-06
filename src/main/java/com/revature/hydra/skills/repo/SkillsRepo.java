package com.revature.hydra.skills.repo;

import com.revature.hydra.skills.model.Skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SkillsRepo extends JpaRepository<Skills, Integer> {
}
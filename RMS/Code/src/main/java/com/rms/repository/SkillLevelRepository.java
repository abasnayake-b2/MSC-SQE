package com.rms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rms.entity.SkillLevel;


@Repository
public interface SkillLevelRepository extends JpaRepository<SkillLevel, Long>{

}

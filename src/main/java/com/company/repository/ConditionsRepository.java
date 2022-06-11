package com.company.repository;

import com.company.entity.ConditionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionsRepository extends JpaRepository<ConditionsEntity, Long> {
}
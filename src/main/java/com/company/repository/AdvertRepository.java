package com.company.repository;

import com.company.entity.AdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {
}
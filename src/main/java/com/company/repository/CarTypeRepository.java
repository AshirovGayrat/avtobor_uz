package com.company.repository;

import com.company.entity.CarTypeEntity;
import com.company.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarTypeRepository extends JpaRepository<CarTypeEntity, Long> {
    @Transactional
    @Modifying
    @Query("update CarTypeEntity set visible =false where id = :id")
    int updateVisible(@Param("id") Long id);

    Optional<CarTypeEntity> findByNameEnAndNameUzAndNameRuAndVisibleTrue(String en, String uz, String ru);

    Page<CarTypeEntity> findAllByVisibleTrue(Pageable pageable);
}
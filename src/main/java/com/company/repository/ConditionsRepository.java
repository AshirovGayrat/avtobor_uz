package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.entity.ConditionsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ConditionsRepository extends JpaRepository<ConditionsEntity, Long> {
    @Transactional
    @Modifying
    @Query("update ConditionsEntity set visible =false where id = :id")
    int updateVisible(@Param("id") Long id);

    List<ConditionsEntity> findAllByVisibleTrue();

    Optional<ConditionsEntity> findByNameEnAndNameUzAndNameRuAndVisibleTrue(String en, String uz, String ru);

}
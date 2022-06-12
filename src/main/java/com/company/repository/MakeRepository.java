package com.company.repository;

import com.company.entity.MakeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MakeRepository extends JpaRepository<MakeEntity, Long> {
    Optional<MakeEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update MakeEntity set visible =false where id = :id")
    int updateVisible(@Param("id") Long id);

    Page<MakeEntity> findAllByVisibleTrue(Pageable pageable);
}
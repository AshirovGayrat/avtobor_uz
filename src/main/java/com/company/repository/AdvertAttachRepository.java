package com.company.repository;

import com.company.entity.AdvertAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdvertAttachRepository extends JpaRepository<AdvertAttachEntity, Long> {
    Optional<AdvertAttachEntity> findByAdvertIdAndVisibleTrue(Long id);

    @Transactional
    @Modifying
    @Query("update AdvertAttachEntity set visible =false where id = :id")
    int updateVisible(@Param("id") Long id);
}
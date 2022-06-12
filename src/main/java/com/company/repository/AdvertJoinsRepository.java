package com.company.repository;

import com.company.entity.AdvertJoinsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdvertJoinsRepository extends JpaRepository<AdvertJoinsEntity, Long> {
    Optional<AdvertJoinsEntity> findByAdvertIdAndVisibleTrue(Long id);

    Optional<AdvertJoinsEntity> findByProfileIdAndVisibleTrue(Long id);

    @Transactional
    @Modifying
    @Query("update AdvertJoinsEntity set visible=false where id=:id")
    int updateVisible(@Param("id")Long id);
}
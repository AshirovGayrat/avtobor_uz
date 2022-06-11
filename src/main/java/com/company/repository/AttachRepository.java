package com.company.repository;

import com.company.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {
    Optional<AttachEntity> findByIdAndVisibleTrue(String id);

    @Transactional
    @Modifying
    @Query("update AttachEntity set visible=:visible where id=:id")
    int updateVisible(@Param("visible")Boolean visible, @Param("id")String id);
}
package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ProfileStatus status, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set password = :password where id = :id")
    int updatePassword(@Param("password") String password, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible =false where id = :id")
    int updateVisible(@Param("id") Long id);

    Optional<ProfileEntity> findByIdAndPassword(Long id, String pswd);
}

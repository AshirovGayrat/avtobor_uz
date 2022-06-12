package com.company.repository;

import com.company.entity.AdvertEntity;
import com.company.mapper.AdvertMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {
    @Query("select a.id as id, a.description as descrip, a.price as price, a.createdDate as date," +
            " a.advertType as advertType, a.bodyColor as color, a.conditionType as conditionType," +
            " a.contactNumber as contactNumber, a.engineSize as engineSize, a.fuelType as fuelType," +
            " a.horsePower as horsePower, a.mileage as mileage, a.paymentType, a.region, a.tireSize," +
            " a.transmissionType, a.valyutaType, a.wheelDriveType,m.id as m_id, m.name as make," +
            " ct.id as ct_id, ct.nameUz as ct_uz," +
            " ct.nameRu as ct_ru, ct.nameEn as ct_en, c.id as c_id, aj.id as joinID," +
            " c.nameUz as c_uz, c.nameRu as c_ru, c.nameEn as c_en, p.id as profileId " +
            " from AdvertEntity as a inner join " +
            " AdvertJoinsEntity as aj inner join " +
            " MakeEntity as m on aj.makeId=m.id inner join " +
            " CarTypeEntity as ct on aj.carTypeId=ct.id inner join " +
            " CategoryEntity as c on aj.categoryId=c.id inner join " +
            " ProfileEntity as p on aj.profileId=p.id " +
            " where a.id=:advertId and a.visible=:visible")
    Optional<AdvertMapper> getFullAdvertById(@Param("advertId") Long advertId, @Param("visible")Boolean visible);


    @Transactional
    @Modifying
    @Query("update AdvertEntity set visible=false where id=:id")
    int updateVisible(@Param("id")Long id);



    @Query("select a.id as id, a.description as descrip, a.price as price, a.createdDate as date," +
            " a.advertType as advertType, a.bodyColor as color, a.conditionType as conditionType," +
            " a.contactNumber as contactNumber, a.engineSize as engineSize, a.fuelType as fuelType," +
            " a.horsePower as horsePower, a.mileage as mileage, a.paymentType, a.region, a.tireSize," +
            " a.transmissionType, a.valyutaType, a.wheelDriveType,m.id as m_id, m.name as make," +
            " ct.id as ct_id, ct.nameUz as ct_uz," +
            " ct.nameRu as ct_ru, ct.nameEn as ct_en, c.id as c_id, aj.id as joinID," +
            " c.nameUz as c_uz, c.nameRu as c_ru, c.nameEn as c_en, p.id as profileId " +
            " from AdvertEntity as a inner join " +
            " AdvertJoinsEntity as aj inner join " +
            " MakeEntity as m on aj.makeId=m.id inner join " +
            " CarTypeEntity as ct on aj.carTypeId=ct.id inner join " +
            " CategoryEntity as c on aj.categoryId=c.id inner join " +
            " ProfileEntity as p on aj.profileId=p.id " +
            " where a.price>1 " +
            " and a.mileage>1" +
            " and a.carYear>'2022-22-10' " +
            " and a.transmissionType='asd' ")
    Optional<AdvertMapper> getFull(@Param("advertId") Long advertId, @Param("visible")Boolean visible);
}
package com.company.repository.custom;

import com.company.dto.FilterDTO;
import com.company.dto.response.AdvertResponseDTO;
import com.company.mapper.AdvertFilterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdvertCustomRepository {
    private final EntityManager entityManager;

    public List<AdvertResponseDTO> filter(FilterDTO dto) {
//        String test=
        StringBuilder sql = new StringBuilder(
                " select new com.company.mapper.AdvertFilterMapper( a.id as advertId, a.description, a.price, a.createdDate," +
                        " a.advertType, a.bodyColor, a.conditionType," +
                        " a.contactNumber, a.engineSize, a.fuelType," +
                        " a.horsePower, a.mileage, a.paymentType, a.region, a.tireSize," +
                        " a.transmissionType, a.valyutaType, a.wheelDriveType,m.id as makeId, m.name as make," +
                        " ct.id as ctId, ct.nameUz as ctUz," +
                        " ct.nameRu as ctRu, ct.nameEn as ctEn, c.id as categoryId, aj.id as joinID," +
                        " c.nameUz as cUz, c.nameRu as cRu, c.nameEn as cEn, p.id as profileId ) " +
                        " from AdvertEntity as a inner join " +
                        " AdvertJoinsEntity as aj inner join " +
                        " MakeEntity as m on aj.makeId=m.id inner join " +
                        " CarTypeEntity as ct on aj.carTypeId=ct.id inner join " +
                        " CategoryEntity as c on aj.categoryId=c.id inner join " +
                        " ProfileEntity as p on aj.profileId=p.id ");

//        Query query=entityManager.createQuery(test, AdvertFilterMapper.class);


        if (Optional.ofNullable(dto.getCategoryId()).isPresent()) {
            sql.append(" where c.id=").append(dto.getCategoryId());
        } else sql.append(" where a.price>1 ");

        if (Optional.ofNullable(dto.getMakeId()).isPresent())
            sql.append(" and m.id=").append(dto.getMakeId());

        if (Optional.ofNullable(dto.getToPrice()).isPresent() && Optional.ofNullable(dto.getFromPrice()).isPresent())
            sql.append(" and a.price between ").append(dto.getFromPrice()).append(" and ").append(dto.getToPrice());

        if (Optional.ofNullable(dto.getFromPrice()).isPresent())
            sql.append(" and a.price > ").append(dto.getFromPrice());

        if (Optional.ofNullable(dto.getToPrice()).isPresent())
            sql.append(" and a.price < ").append(dto.getToPrice());

        if (Optional.ofNullable(dto.getToMileage()).isPresent() && Optional.ofNullable(dto.getFromMileage()).isPresent())
            sql.append(" and a.mileage between ").append(dto.getFromMileage()).append(" and ").append(dto.getToMileage());

        if (Optional.ofNullable(dto.getFromMileage()).isPresent())
            sql.append(" and a.mileage > ").append(dto.getFromMileage());

        if (Optional.ofNullable(dto.getToMileage()).isPresent())
            sql.append(" and a.mileage < ").append(dto.getToMileage());

        // Car Year
        if (Optional.ofNullable(dto.getFromYear()).isPresent() && Optional.ofNullable(dto.getToYear()).isPresent())
            sql.append(" and a.carYear between ").append(dto.getFromMileage()).append(" and ").append(dto.getToMileage());

        if (Optional.ofNullable(dto.getFromYear()).isPresent())
            sql.append(" and a.carYear > ").append(dto.getFromPrice());

        if (Optional.ofNullable(dto.getToYear()).isPresent())
            sql.append(" and a.carYear < ").append(dto.getToPrice());

        if (Optional.ofNullable(dto.getTransmission()).isPresent())
            sql.append("and a.transmissionType='").append(dto.getTransmission()).append("'");

        Query query = entityManager.createQuery(sql.toString(), AdvertFilterMapper.class);

        return query.getResultList();
    }

}

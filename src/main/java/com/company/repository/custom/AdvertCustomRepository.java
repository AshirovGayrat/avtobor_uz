package com.company.repository.custom;

import com.company.dto.FilterDTO;
import com.company.dto.response.AdvertResponseDTO;
import com.company.entity.AdvertEntity;
import com.company.mapper.AdvertMapper;
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
        StringBuilder sql = new StringBuilder();
        sql.append(
                " select a.id as id, a.description as descrip, a.price as price, a.createdDate as date," +
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
                        " ProfileEntity as p on aj.profileId=p.id ");


        if (Optional.ofNullable(dto.getCategoryId()).isPresent()) {
            sql.append(" where c.id=" + dto.getCategoryId());
        } else sql.append(" where a.price>1 ");

        if (Optional.ofNullable(dto.getMakeId()).isPresent())
            sql.append(" and m.id=" + dto.getMakeId());

        if (Optional.ofNullable(dto.getToPrice()).isPresent() && Optional.ofNullable(dto.getFromPrice()).isPresent())
            sql.append(" and a.price between " + dto.getFromPrice() + " and " + dto.getToPrice());

        if (Optional.ofNullable(dto.getFromPrice()).isPresent())
            sql.append(" and a.price > " + dto.getFromPrice());

        if (Optional.ofNullable(dto.getToPrice()).isPresent())
            sql.append(" and a.price < " + dto.getToPrice());

        if (Optional.ofNullable(dto.getToMileage()).isPresent() && Optional.ofNullable(dto.getFromMileage()).isPresent())
            sql.append(" and a.mileage between " + dto.getFromMileage() + " and " + dto.getToMileage());

        if (Optional.ofNullable(dto.getFromPrice()).isPresent())
            sql.append(" and a.mileage > " + dto.getFromPrice());

        if (Optional.ofNullable(dto.getToPrice()).isPresent())
            sql.append(" and a.mileage < " + dto.getToPrice());

        // Car Year
        if (Optional.ofNullable(dto.getFromYear()).isPresent() && Optional.ofNullable(dto.getToYear()).isPresent())
            sql.append(" and a.carYear between " + dto.getFromMileage() + " and " + dto.getToMileage());

        if (Optional.ofNullable(dto.getFromYear()).isPresent())
            sql.append(" and a.carYear > " + dto.getFromPrice());

        if (Optional.ofNullable(dto.getToYear()).isPresent())
            sql.append(" and a.carYear < " + dto.getToPrice());

        if (Optional.ofNullable(dto.getTransmission()).isPresent())
            sql.append("and a.transmissionType='"+dto.getTransmission()+"'");

        Query query = entityManager.createQuery(sql.toString(), AdvertMapper.class);

        return query.getResultList();
    }

}

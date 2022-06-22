package com.company.mapper;

import com.company.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdvertFilterMapper {
    //a.id as advertId, a.description, a.price, a.createdDate," +
    //          " a.advertType, a.bodyColor, a.conditionType," +
    //          " a.contactNumber, a.engineSize, a.fuelType," +
    //          " a.horsePower, a.mileage, a.paymentType, a.region, a.tireSize," +
    //         " a.transmissionType, a.valyutaType, a.wheelDriveType,m.id as makeId, m.name as make," +
    //        " ct.id as ctId, ct.nameUz as ctUz," +
    //       " ct.nameRu as ctRu, ct.nameEn as ctEn, c.id as categoryId, aj.id as joinID," +
    //  " c.nameUz as cUz, c.nameRu as cRu, c.nameEn as cEn, p.id as profileId
    private Long advertId;
    private String description;
    private Long price;
    private LocalDateTime createdDate;
    private AdvertType advertType;
    private BodyColor bodyColor;
    private ConditionType conditionType;
    private String contactNumber;
    private Float engineSize;
    private FuelType fuelType;
    private Integer horsePower;
    private Double mileage;
    private PaymentType paymentType;
    private String region;
    private Integer tireSize;
    private TransmissionType transmissionType;
    private ValyutaType valyutaType;
    private WheelDriveType wheelDriveType;
    private Long makeId;
    private String make;
    private Long ctId;
    private String ctUz;
    private String ctRu;
    private String ctEn;
    private Long categoryId;
    private Long joinId;
    private String cUz;
    private String cRu;
    private String cEn;
    private Long profileId;

    public AdvertFilterMapper(
            Long advertId, String description,
            Long price, LocalDateTime createdDate,
            AdvertType advertType, BodyColor bodyColor,
            ConditionType conditionType, String contactNumber,
            Float engineSize, FuelType fuelType, Integer horsePower,
            Double mileage, PaymentType paymentType, String region,
            Integer tireSize, TransmissionType transmissionType,
            ValyutaType valyutaType, WheelDriveType wheelDriveType,
            Long makeId, String make, Long ctId, String ctUz, String ctRu,
            String ctEn, Long categoryId, Long joinId, String cUz,
            String cRu, String cEn, Long profileId) {
        this.advertId = advertId;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.advertType = advertType;
        this.bodyColor = bodyColor;
        this.conditionType = conditionType;
        this.contactNumber = contactNumber;
        this.engineSize = engineSize;
        this.fuelType = fuelType;
        this.horsePower = horsePower;
        this.mileage = mileage;
        this.paymentType = paymentType;
        this.region = region;
        this.tireSize = tireSize;
        this.transmissionType = transmissionType;
        this.valyutaType = valyutaType;
        this.wheelDriveType = wheelDriveType;
        this.makeId = makeId;
        this.make = make;
        this.ctId = ctId;
        this.ctUz = ctUz;
        this.ctRu = ctRu;
        this.ctEn = ctEn;
        this.categoryId = categoryId;
        this.joinId = joinId;
        this.cUz = cUz;
        this.cRu = cRu;
        this.cEn = cEn;
        this.profileId = profileId;
    }
}

package com.company.entity;

import com.company.enums.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "advert")
public class AdvertEntity extends BaseEntity {

    private String description;
    @Column(name = "contact_num")
    private String contactNumber;
    private String region;
    private Long price;
    @Column(name = "engine_size")
    private Float engineSize;
    @Column(name = "tire_size")
    private Integer tireSize;
    private Double mileage;
    @Column(name = "horse_power")
    private Integer horsePower;

    //ENUMS
    @Enumerated(EnumType.STRING)
    private ValyutaType valyutaType;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private AdvertType advertType;
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
    @Enumerated(EnumType.STRING)
    private ConditionType conditionType;
    @Enumerated(EnumType.STRING)
    private WheelDriveType wheelDriveType;
    @Enumerated(EnumType.STRING)
    private BodyColor bodyColor;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

//    @TypeDef(name = "list-array", typeClass = ListArrayType.class)
//    @Type(type = "list-array")
//    @Column(name = "mazgi-string", columnDefinition = "string[]")
//    private List<String> tagList;
//    @Column
//    @Convert(converter = ListToStringConverter.class)
//    private List<String> conditionList;
}

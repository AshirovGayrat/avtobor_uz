package com.company.dto.request;

import com.company.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class AdvertRequestDTO {
    private List<String> attachIdList;

    private String description;
    private String contactNumber;
    private String region;
    private Long price;
    private Float engineSize;
    private Integer tireSize;
    private Double mileage;
    private Integer horsePower;

    //
    private Long makeId;
    private Long categoryId;
    private Long carTypeId;

    //ENUMS
    private ValyutaType valyutaType;
    private PaymentType paymentType;
    private AdvertType advertType;
    private TransmissionType transmissionType;
    private ConditionType conditionType;
    private WheelDriveType wheelDriveType;
    private BodyColor bodyColor;
    private FuelType fuelType;
}

package com.company.mapper;

import com.company.enums.*;

import java.time.LocalDateTime;

public interface AdvertMapper {
    Long getId();
    Long getM_id();
    Long getC_id();
    Long getCt_id();
    Long getJoinId();
    String getDescrip();
    Long getPrice();
    LocalDateTime getDate();
    AdvertType getAdvertType();
    BodyColor getColor();
    ConditionType getConditionType();
    String getContactNumber();
    Float getEngineSize();
    FuelType getFuelType();
    Integer getHorsePower();
    Double getMileage();
    PaymentType getPaymentType();
    String getRegion();
    Integer getTireSize();
    TransmissionType getTransmissionType();
    ValyutaType getValyutaType();
    WheelDriveType getWheelDriveType();
    String getMake();
    String getCt_uz();
    String getCt_ru();
    String getCt_en();

    String getC_en();
    String getC_ru();
    String getC_uz();

    Long getProfileId();
}

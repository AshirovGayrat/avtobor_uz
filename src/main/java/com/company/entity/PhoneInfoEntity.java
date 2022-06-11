package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "phone_info")
public class PhoneInfoEntity extends BaseEntity{
    private String phone;
    private String name;

    private Boolean telegram;
    private Boolean whatsapp;
    private Boolean imo;
    private Boolean viber;

    @Column(name = "profile_id")
    private Long profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
}

package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "advert_joins")
public class AdvertJoinsEntity extends BaseEntity{
    //JOINS
    @Column(name = "advert_id")
    private Long advertId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id", insertable = false, updatable = false)
    private AdvertEntity advert;

    @Column(name = "make_id")
    private Long makeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "make_id", insertable = false, updatable = false)
    private MakeEntity make;

    @Column(name = "category_id")
    private Long categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "car_type_id")
    private Long carTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_type_id", insertable = false, updatable = false)
    private CarTypeEntity carType;

    @Column(name = "profile_id")
    private Long profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
}

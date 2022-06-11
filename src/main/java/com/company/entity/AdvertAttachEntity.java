package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "advert_attach")
public class AdvertAttachEntity extends BaseEntity{

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "advert_id")
    private Long advertId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id", insertable = false, updatable = false)
    private AdvertEntity advert;
}

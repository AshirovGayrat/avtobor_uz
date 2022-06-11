package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "make_entity")
public class MakeEntity extends BaseEntity{
    private String name;
}

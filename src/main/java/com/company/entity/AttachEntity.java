package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id; // uuid

    @NotBlank
    @Column(name = "extension")
    private String extension;

    @Column(name = "path")
    private String path;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "size")
    private Long size;

    @Column(name = "create_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    private Boolean visible=true;
}
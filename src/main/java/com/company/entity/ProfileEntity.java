package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.enums.ProfileType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "profile_table")
public class ProfileEntity extends BaseEntity{
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String password;
    @Column(name = "web_site")
    private String webSite;
    private String facebook;
    private String about;

    @Enumerated(EnumType.STRING)
    private ProfileType type;
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
}

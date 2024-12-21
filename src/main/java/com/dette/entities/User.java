package com.dette.entities;

import com.dette.enums.Role;
import com.dette.enums.RoleConvert;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
// import lombok.ToString;

@Getter
@Setter
// @ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(length = 30, unique = true)
    private String login;

    @Column(length = 60)
    private String password;

    @Convert(converter = RoleConvert.class)
    @Column(name = "roleId")
    private Role role;

    private Boolean etat;

    @OneToOne(mappedBy = "user")
    private Client client;

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + ", etat="
                + etat
                + "]";
    }

    // mappedby on le met dans laclasse qui ne dois pa avoir la clé etranger
}

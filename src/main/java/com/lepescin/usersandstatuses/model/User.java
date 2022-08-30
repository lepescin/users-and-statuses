package com.lepescin.usersandstatuses.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    @NotEmpty @Email
    private String email;

    @Column(name = "phone_number")
    @NotEmpty
    private String phoneNumber;

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "status_changed")
    private LocalDateTime statusChanged;
}

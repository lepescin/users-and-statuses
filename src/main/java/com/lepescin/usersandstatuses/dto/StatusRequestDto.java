package com.lepescin.usersandstatuses.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusRequestDto {
    private Integer userId;
    private String newStatus;
}

package com.lepescin.usersandstatuses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusResponseDto {
    private Integer userId;
    private String oldStatus;
    private String newStatus;
}

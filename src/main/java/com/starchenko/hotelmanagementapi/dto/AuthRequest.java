package com.starchenko.hotelmanagementapi.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
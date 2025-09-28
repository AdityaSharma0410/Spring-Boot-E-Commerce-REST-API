package com.etherealcart.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRoleRequestDTO {

    @NotBlank(message = "Role is required")
    private String role;

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
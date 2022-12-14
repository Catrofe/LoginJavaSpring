package com.login.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDTO {
    private Long id;
    private String email;
    private String name;
    private String cpf;
    private String phone;
}

package com.login.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEdit implements UserEditDTO{
    private String name;
    private String email;
    private String phone;
}

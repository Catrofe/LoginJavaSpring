package com.login.login.security.dto;

import lombok.*;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationToken implements Serializable {
    private Long id;
    private Long exp;
}
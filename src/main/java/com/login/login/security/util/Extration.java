package com.login.login.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.login.security.dto.GenerationToken;
import io.jsonwebtoken.Claims;

public class Extration {

    public Long extrationId(Claims claims) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String extrationClaims = objectMapper.writeValueAsString(claims);
        GenerationToken generationToken = objectMapper.readValue(extrationClaims, GenerationToken.class);
        return generationToken.getId();
    }
}


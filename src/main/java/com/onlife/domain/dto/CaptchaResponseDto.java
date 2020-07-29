package com.onlife.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CaptchaResponseDto {

    @JsonAlias("error-codes")
    private Set<String> errorCode;

    private boolean success;

    public boolean isSuccess() {
        return success;
    }
}
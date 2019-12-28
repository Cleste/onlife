package com.onlife.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {

    @JsonAlias("error-codes")
    private Set<String> errorCode;

    private boolean success;

    public Set<String> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Set<String> errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
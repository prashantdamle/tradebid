package com.tradespace.tradebid.adapters.api.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorDTO {
    private final String type;
    private final String status;
    private final List<String> errors;

    public ErrorDTO(String type, String status, List<String> errors) {
        this.type = type;
        this.status = status;
        this.errors = errors;
    }
}

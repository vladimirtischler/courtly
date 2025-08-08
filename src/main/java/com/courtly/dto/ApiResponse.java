package com.courtly.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class ApiResponse<T> {
    private T data;
    private Collection<String> errors;
}

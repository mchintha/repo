package com.spring.reactive.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private int statusCode;
    private String errorMessage;
}

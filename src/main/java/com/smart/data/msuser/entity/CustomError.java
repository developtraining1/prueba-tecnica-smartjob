package com.smart.data.msuser.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class CustomError {

    private String message;

    private HttpStatus status;

}

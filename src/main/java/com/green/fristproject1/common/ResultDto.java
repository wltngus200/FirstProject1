package com.green.fristproject1.common;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ResultDto <T> {
    private HttpStatus statusCode;
    private String resultMsg;
    private T resultData;
}

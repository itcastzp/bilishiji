package com.example.demo.bilibili.plugin.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> result = new HashMap<>();
        result.put("code", "444");
        result.put("message", ex.getMessage());
        result.put("success", false);
        return new ResponseEntity<Object>(result, headers, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
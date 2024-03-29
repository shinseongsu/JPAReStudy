package com.example.jpa.common.handler;

import com.example.jpa.common.exception.AuthFailException;
import com.example.jpa.common.model.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptioinHandler {

    @ExceptionHandler(AuthFailException.class)
    public ResponseEntity<?> AuthFailException(AuthFailException exception) {

        return ResponseResult.fail("[인증실패]"+exception.getMessage());
    }

}

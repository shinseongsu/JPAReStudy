package com.example.jpa.common.exception;

import com.example.jpa.board.entity.Board;

import java.util.List;

public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}

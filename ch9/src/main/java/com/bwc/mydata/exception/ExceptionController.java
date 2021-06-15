package com.bwc.mydata.exception;

import com.bwc.mydata.vo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@RestController
public class ExceptionController {

    /**
     * json으로 받는 @valid 에러 시,
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> ExceptionMethodArgument(MethodArgumentNotValidException e) {
        List<ObjectError> errors =  e.getBindingResult().getAllErrors();

        ErrorResponse response = ErrorResponse.builder()
                                            .rsp_code("40001")
                                            .rsp_msg(errors.get(0).getDefaultMessage())
                                            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * application/json으로 수정해주세요.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeException.class)
    public ResponseEntity<?> ExceptionMediaType(HttpMediaTypeException e) {

        ErrorResponse response = ErrorResponse.builder()
                .rsp_code("40001")
                .rsp_msg("Content-Type을 application/json;chartset=UTF-8으로 해주세요.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseEntity<?> ExceptionMissingRequestHeader(MissingRequestHeaderException e) {

        ErrorResponse response = ErrorResponse.builder()
                .rsp_code("40002")
                .rsp_msg("Header 값이 빈값입니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> ExceptionConstrainViolationException(ConstraintViolationException e) {

        ErrorResponse response = ErrorResponse.builder()
                .rsp_code("40001")
                .rsp_msg(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> BadRequest() {

        ErrorResponse response = ErrorResponse.builder()
                .rsp_code("40001")
                .rsp_msg("Bad Request 요청입니다.")
                .build();

        return new ResponseEntity<>(response ,HttpStatus.OK);
    }

}

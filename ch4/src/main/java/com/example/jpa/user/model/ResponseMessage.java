package com.example.jpa.user.model;

import com.example.jpa.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private ResponseMessageHeader header;
    private Object body;

    public static Object fail(String message) {

        return ResponseMessage.builder()
                    .header(ResponseMessageHeader.builder()
                                .result(false)
                                .resultCode("")
                                .message(message)
                                .status(HttpStatus.BAD_REQUEST.value())
                                .build())
                    .body(null)
                    .build();
    }

    public static ResponseMessage success(Object data) {

        return ResponseMessage.builder()
                    .header(ResponseMessageHeader.builder()
                                .result(true)
                                .resultCode("")
                                .message("")
                                .status(HttpStatus.OK.value())
                                .build())
                    .body(data)
                    .build();

    }

    public static ResponseMessage success() {

        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("")
                        .status(HttpStatus.OK.value())
                        .build())
                .body(null)
                .build();

    }

//    private long totalCount;
//    private List<User> data;

}

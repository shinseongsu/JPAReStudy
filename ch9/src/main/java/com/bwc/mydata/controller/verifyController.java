package com.bwc.mydata.controller;

import com.bwc.mydata.vo.ErrorResponse;
import com.bwc.mydata.vo.verifyReqvo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@Validated
public class verifyController {

    @RequestMapping(value = "{version}/customer/verify" , method = RequestMethod.POST)
    public ResponseEntity<?> verify(@Valid @RequestBody verifyReqvo vo,
                                    @PathVariable(value = "version") String version,
                                    @RequestHeader(value = "X-Req-Id", required = false)
                                        @Pattern(regexp = "^[0-9a-z]*$", message = "알파벳과 숫자만 입력가능합니다.")
                                        @Size(max=2, message = "header 자릿값 에러") String ReqId) {

        ErrorResponse response = ErrorResponse.builder()
                                    .rsp_code("00000")
                                    .rsp_msg("성공")
                                    .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Req-Id", ReqId);

        return new ResponseEntity<>(response, headers ,HttpStatus.OK);
    }

}

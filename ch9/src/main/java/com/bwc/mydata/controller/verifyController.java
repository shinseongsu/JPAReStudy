package com.bwc.mydata.controller;

import com.bwc.mydata.vo.ErrorResponse;
import com.bwc.mydata.vo.paidMethodvo;
import com.bwc.mydata.vo.paidvo;
import com.bwc.mydata.vo.verifyReqvo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class verifyController {

    @RequestMapping(value = "{version}/customer/verify" , method = RequestMethod.POST)
    public ResponseEntity<Object> verify(@Valid @RequestBody verifyReqvo vo,
                                    @PathVariable(value = "version") String version,
                                    Errors error) {

        if(error.hasErrors()) {
            FieldError fieldError = error.getFieldError();
            System.out.println(fieldError.getDefaultMessage());
        }

        ErrorResponse response = ErrorResponse.builder()
                                    .rsp_code("00000")
                                    .rsp_msg("성공")
                                    .build();

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers ,HttpStatus.OK);
    }

    @RequestMapping(value = "/pre/list")
    public ResponseEntity<?> PreList() {

        paidvo result = paidvo.builder()
                .rsp_code("00000")
                .rsp_code("성공")
                .accountList(makeDummyData())
                .build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public List<paidMethodvo> makeDummyData() {

        paidMethodvo paidMethod1 = paidMethodvo.builder()
                .payname("스타벅스카드")
                .amt(10000)
                .build();

        paidMethodvo paidMethod2 = paidMethodvo.builder()
                .payid("002")
                .payname("배달의민족카드")
                .amt(100000)
                .build();

        List<paidMethodvo> list = Arrays.asList( paidMethod1, paidMethod2 );
        list = new ArrayList<>();
        return list;
    }

}

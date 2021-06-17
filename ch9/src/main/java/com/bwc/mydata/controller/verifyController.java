package com.bwc.mydata.controller;

import com.bwc.mydata.vo.ErrorResponse;
import com.bwc.mydata.vo.paidMethodvo;
import com.bwc.mydata.vo.paidvo;
import com.bwc.mydata.vo.verifyReqvo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

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
        return list;
    }

}

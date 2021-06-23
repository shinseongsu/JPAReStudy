package com.bwc.mydata.controller;

import com.bwc.mydata.repository.MemberRepository;
import com.bwc.mydata.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

@Slf4j
@RestController
public class verifyController {

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "{version}/customer/verify" , method = RequestMethod.POST)
    @Cacheable(cacheNames = "verify", key="#vo.ci")
    public ResponseEntity<Object> verify(@Valid @RequestBody verifyReqvo vo,
                                    @PathVariable(value = "version") String version,
                                    Errors error) {


        ErrorResponse response = ErrorResponse.builder()
                                    .rsp_code("00000")
                                    .rsp_msg("성공")
                                    .build();

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers ,HttpStatus.OK);
    }

    @RequestMapping(value = "/pre/list")
    public ResponseEntity<?> PreList(@RequestParam String ci, @RequestParam String req_type) {

        paidvo result = paidvo.builder()
                .rsp_code("00000")
                .rsp_code("성공")
                .accountList(makeDummyData(new verifyReqvo().builder().req_type(req_type).ci(ci).build()))
                .build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public List<paidMethodvo> makeDummyData(verifyReqvo vo) {
        log.info("######################################");
        log.info("##################"+ vo.getReq_type() + "#####" + vo.getCi() +"####################");
        log.info("######################################");

        paidMethodvo paidMethod1 = paidMethodvo.builder()
                .payname(vo.getCi())
                .amt(10000)
                .build();

        paidMethodvo paidMethod2 = paidMethodvo.builder()
                .payid("002")
                .payname(vo.getCi())
                .amt(100000)
                .build();

        List<paidMethodvo> list = Arrays.asList( paidMethod1, paidMethod2 );

        return list;
    }

    @GetMapping("/member/nocache/{name}")
    public Member getNoCacheMember(@PathVariable String name){

        long start = System.currentTimeMillis(); // 수행시간 측정
        Member member = memberRepository.findByNameNoCache(name); // db 조회
        long end = System.currentTimeMillis();

        log.info(name+ "의 NoCache 수행시간 : "+ Long.toString(end-start));

        return member;
    }

    @GetMapping("/member/cache/{name}")
    public Member getCacheMember(@PathVariable String name){

        long start = System.currentTimeMillis(); // 수행시간 측정
        Member member = memberRepository.findByNameCache(name); // db 조회
        long end = System.currentTimeMillis();

        log.info(name+ "의 Cache 수행시간 : "+ Long.toString(end-start));

        return member;
    }

    @GetMapping("/member/refresh/{name}")
    public String refresh(@PathVariable String name){
        memberRepository.refresh(name); // 캐시제거
        return "cache clear!";
    }



}

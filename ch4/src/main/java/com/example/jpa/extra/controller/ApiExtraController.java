package com.example.jpa.extra.controller;

import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.extra.model.AirInput;
import com.example.jpa.extra.model.OpenApiResult;
import com.example.jpa.extra.model.PharmacySearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class ApiExtraController {

    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> pharmacy() {

        String apiKey = "%2FAZVKcyk74moGpLTCAi6K6GDE9BUTRi%2BReTZW6bHYsBD4qHJgi%2F9Odu1FEZ%2BPHCevoJ7nAb94xolpbyGCbZyZQ%3D%3D";
        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10";
        String apiResult = "";

        try {
            URI uri = new URI(String.format(url, apiKey));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;

        } catch (Exception e ) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);
    }

    @GetMapping("/api/extra/pharmacy2")
    public String pharmacy2() {

        String apiKey = "%2FAZVKcyk74moGpLTCAi6K6GDE9BUTRi%2BReTZW6bHYsBD4qHJgi%2F9Odu1FEZ%2BPHCevoJ7nAb94xolpbyGCbZyZQ%3D%3D";
        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10";
        String apiResult = "";

        try {
            URI uri = new URI(String.format(url, apiKey));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);
            apiResult = result;

        } catch (Exception e ) {
            e.printStackTrace();
        }

        return apiResult;
    }

    @GetMapping("/api/extra/pharmacy3")
    public ResponseEntity<?> pharmacy3(@RequestBody PharmacySearch pharmacySearch) {

        String apiKey = "%2FAZVKcyk74moGpLTCAi6K6GDE9BUTRi%2BReTZW6bHYsBD4qHJgi%2F9Odu1FEZ%2BPHCevoJ7nAb94xolpbyGCbZyZQ%3D%3D";
        String url = String.format("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10", apiKey);
        String apiResult = "";

        try {
            url += String.format("&Q0=%s&Q1=%s"
                    , URLEncoder.encode(pharmacySearch.getSearchSidi(), "UTF-8")
                    , URLEncoder.encode(pharmacySearch.getSearchGugun(), "UTF-8"));

            URI uri = new URI(url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;

        } catch (Exception e ) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);
    }

    @GetMapping("/api/extra/air")
    public String air(@RequestBody AirInput airInput) {

        String apiKey = "%2FAZVKcyk74moGpLTCAi6K6GDE9BUTRi%2BReTZW6bHYsBD4qHJgi%2F9Odu1FEZ%2BPHCevoJ7nAb94xolpbyGCbZyZQ%3D%3D";
        String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=%s&numOfRows=100&pageNo=1&sidoName=%s";

        String apiResult = "";

        try {
            URI uri = new URI(String.format( url,
                                    apiKey,
                                    URLEncoder.encode(airInput.getSearchSido(), "UTF-8")));

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(url, String.class);

            apiResult = result;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return apiResult;
    }



}

package com.example.jpa.extra.controller;

import com.example.jpa.common.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiExtraKakaoController {

    @GetMapping("api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody KakaoTranslateInput kakaoTranslateInput) {

        String key = "0b52bdc0f285ff261fea6244eed0c44b";
        String url = "https://dapi.kakao.com/v2/translation/translate";

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("src_lang", "kr");
        parameter.add("target_lang", "en");
        parameter.add("query", kakaoTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + key);

        HttpEntity formEntity = new HttpEntity<>(parameter, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);

        return ResponseResult.success(responseEntity.getBody());
    }

}

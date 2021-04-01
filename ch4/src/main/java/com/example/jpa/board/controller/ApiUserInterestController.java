package com.example.jpa.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.user.service.UserService;
import com.example.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiUserInterestController {

    private final UserService userService;

    /**
     * 관심사용자 등록 하는 API
     */
    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> interestUser(@PathVariable Long id,
                                          @RequestHeader("S-TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.addInterestUser(email, id);
        return ResponseResult.result(result);
    }

    /**
     * 관심 사용자에 등록하는 API를 작성해 보세요.
     */
    @DeleteMapping("/api/user/{id}/interest")
    public ResponseEntity<?> deleteInterestUser(@PathVariable Long id,
                                                @RequestHeader("S-TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.removeInterestUser(email, id);
        return ResponseResult.result(result);
    }

}

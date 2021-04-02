package com.example.jpa.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpa.board.entity.Board;
import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.*;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {

    private final BoardService boardService;

    /**
     * 게시판 타입을 추가하는 API
     */
    @PostMapping("/api/board/type")
    public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

        if(errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result = boardService.addBoard(boardTypeInput);

        if(!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 게시판타입을 수정하는 API
     */
    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> updateBoardType(@PathVariable Long id, @RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

        if(errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result = boardService.updateBoard(id, boardTypeInput);

        if(!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 삭제하는 API
     *
     */
    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> deleteBoardType(@PathVariable Long id) {

        ServiceResult result = boardService.deleteBoard(id);

        if(!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    /**
     * 게시판타입의 목록을 리턴하는 API
     */
    @GetMapping("/api/board/type")
    public ResponseEntity<?> boardType() {

        List<BoardType> boardTypeList =  boardService.getAllBoardType();

        return ResponseEntity.ok().body(ResponseMessage.success(boardTypeList));
    }

    /**
     * 사용여부를 조회하는 API
     */
    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> usingBoardType(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing) {

        ServiceResult result =  boardService.setBoardTypeUsing(id, boardTypeUsing);

        if(!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    /**
     * 게시판별 작성된 게시글 조회하는 API
     */
    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> boardTypeCount() {

        List<BoardTypeCount> list = boardService.getBoardTypeCount();
        return ResponseEntity.ok().body(list);
    }

    /**
     * 게시판 게시글을 촤상단에 배치하는 API를 작성해보자.
     */
    @PatchMapping("/api/board/{id}/top")
    public ResponseEntity<?> boardPostTop(@PathVariable Long id) {

        ServiceResult result = boardService.setBoardTop(id, true);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/board/{id}/top/clear")
    public ResponseEntity<?> boardPostTopClear(@PathVariable Long id) {
        ServiceResult result = boardService.setBoardTop(id, false);
        return ResponseEntity.ok().body(result);
    }


    /**
     * 개사글 게시기간을 시작일과 종료일로 설정하는 API
     */
    @PatchMapping("/api/board/{id}/publish")
    public ResponseEntity<?> boardPeriod(@PathVariable Long id, @RequestBody BoardPeriod boardPeriod) {

        ServiceResult result = boardService.setBoardPeriod(id, boardPeriod);

        if (result.isResult()) {
            return ResponseResult.fail(result.getMessage());
        }

        return ResponseResult.success();
    }

    /**
     * 게시글의 조회수를 증가시키는 API
     */
    @PutMapping("/api/board/{id}/hits")
    public ResponseEntity<?> boardHits(@PathVariable Long id
                        , @RequestHeader("S-TOKEN") String token ) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardHits(id, email);
        if(result.isResult()) {
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }

    /**
     * 게시글에 대한 좋아요가 기능을 수행하는 API
     */
    @PutMapping("/api/board/{id}/like")
    public ResponseEntity<?> boardLike(@PathVariable Long id
                        , @RequestHeader("S-TOKEN") String token ) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardLike(id, email);
        return ResponseResult.result(result);
    }

    /**
     * 게시글에 대한 좋아요가 기능을 수행하는 API
     */
    @PutMapping("/api/board/{id}/unlike")
    public ResponseEntity<?> boardUnLike(@PathVariable Long id
            , @RequestHeader("S-TOKEN") String token ) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardUnLike(id, email);
        return ResponseResult.result(result);
    }

    /**
     * 게시판 게시글에 대해서 문제가 있는 게시글을 신고하는 기능의 API
     */
    @PutMapping("/api/board/{id}/badreport")
    public ResponseEntity<?> boardBadReport(@PathVariable Long id
                            , @RequestHeader("S-TOKEN") String token
                            , @RequestBody BoardBadReportInput boardBadReportInput) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.addBadReport(id, email, boardBadReportInput);
        return ResponseResult.result(result);
    }

    /**
     * AOP를 이용하여 게시물 상세 조회
     */
    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {

        Board board = null;
        try {
            board = boardService.detail(id);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }

        return ResponseResult.success(board);
    }

}

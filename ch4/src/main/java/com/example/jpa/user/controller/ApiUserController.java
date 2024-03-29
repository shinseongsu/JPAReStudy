package com.example.jpa.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.jpa.board.entity.Board;
import com.example.jpa.board.entity.BoardComment;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.entity.NoticeLike;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeLikeRepository;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.exception.ExistsEmailException;
import com.example.jpa.user.exception.PasswordNotMatchException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.*;
import com.example.jpa.user.repository.UserRepository;
import com.example.jpa.user.service.PointService;
import com.example.jpa.user.service.UserService;
import com.example.jpa.util.JWTUtils;
import com.example.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ApiUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;

    private final BoardService boardService;
    private final PointService pointService;

    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/user2")
    public ResponseEntity<?> addUser2(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(userInput.getPassword())
                .phone(userInput.getPhone())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id
                         , @RequestBody UserUpdate userUpdate
                         , Errors errors ) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        user.setPhone(userUpdate.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/user/{id}")
    public UserResponse getUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        // UserResponse userResponse = new UserResponse(user);
        UserResponse userResponse = UserResponse.of(user);

        return userResponse;
    }

    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> userNotice(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByUser(user);

        List<NoticeResponse> noticeResponseList = new ArrayList<>();

        noticeList.stream().forEach((e) -> {
            noticeResponseList.add(NoticeResponse.of(e));
        });

        return noticeResponseList;
    }

    @PostMapping("/api/user3")
    public ResponseEntity<?> addUser3(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if( userRepository.countByEmail(userInput.getEmail())  > 0) {
            throw new ExistsEmailException("이미 가입된 이메일 입니다.");
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .phone(userInput.getPhone())
                .password(userInput.getPassword())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler( value = { ExistsEmailException.class, PasswordNotMatchException.class } )
    public ResponseEntity<?> ExistsEmailExceptionHandler(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 비밀번호 변경
     *
     * @param id
     * @param userInputPassword
     * @param errors
     * @return
     */
    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody UserInputPassword userInputPassword, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByIdAndPassword(id, userInputPassword.getPassword())
                    .orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));

        user.setPassword(userInputPassword.getNewPassword());

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


    private String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    @PostMapping("/api/user4")
    public ResponseEntity<?> addUser4(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if( userRepository.countByEmail(userInput.getEmail())  > 0) {
            throw new ExistsEmailException("이미 가입된 이메일 입니다.");
        }

        String encryptPassword = getEncryptPassword(userInput.getPassword());

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .phone(userInput.getPhone())
                .password(encryptPassword)
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        // 내가 쓴 공지사항이 있는 경우
        // -> ???
        // 1. 삭제 못해. 삭제하려면 공지사항 제거하고 와.
        // 2. 회원삭제전에 공지사항글을 다 삭제하는 경우.

        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            String message = "제약조건에 문제가 발생하였습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String message = "회원 탈퇴 중 문제가 발생하였습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 아이디 찾는 API
     *
     */
    @GetMapping("/api/user")
    public ResponseEntity<?> findUser(@RequestBody UserInputFind userInputFind) {

        User user = userRepository.findByUserNameAndPhone(userInputFind.getUserName(), userInputFind.getPhone())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        UserResponse userResponse = UserResponse.of(user);

        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * 사용자 비밀번호 초기화 요청
     */
    @GetMapping("/api/user/{id}/password/reset")
    public ResponseEntity<?> resetUserPassword(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        // 비밀번호 초기화
        String resetPassword = getResetPassword();
        String resetEncryptPassword = getEncryptPassword(  resetPassword );
        user.setPassword(resetEncryptPassword);
        userRepository.save(user);

        String message = String.format("[%s]님의 임시비밀번호는 [%s] 로 초기화 되었습니다.",
                 user.getUserName() ,
                 resetPassword);
        sendSMS(message);

        return ResponseEntity.ok().build();
    }


    public String getResetPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public void sendSMS(String message) {
        System.out.println("[문자 메시지 전송]");
        System.out.println(message);
    }

    @GetMapping("/api/user/{id}/notice/like")
    public List<NoticeLike> likeNotice(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(user);

        return noticeLikeList;
    }

    /**
     * 록그인 JWT 유효기간-1개월
     */
    @PostMapping("/api/user/login")
    public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 발행
        /*String token = JWT.create()
                            .withExpiresAt(new Date())
                            .withClaim("user_id", user.getId())
                            .withSubject(user.getUserName())
                            .withIssuer(user.getEmail())
                            .sign(Algorithm.HMAC512("shinseongsu".getBytes()));*/

        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = Timestamp.valueOf(expiredDateTime);

        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("shinseongsu".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
    }

    @PatchMapping("/api/user/login")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        String token = request.getHeader("S-TOKEN");
        String email = "";

        try {
            email = JWT.require(Algorithm.HMAC512("shinseongsu".getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
        } catch(SignatureVerificationException e) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        } catch(Exception e) {
            throw new PasswordNotMatchException("토큰 발행에 실패했습니다.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = Timestamp.valueOf(expiredDateTime);

        String nearToken = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("shinseongsu".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(nearToken).build());
    }

    /**
     * 토큰을 삭제하는 API
     */
    @DeleteMapping("/api/user/login")
    public ResponseEntity<?> removeToken(@RequestHeader("S-TOKEN") String token) {

        String email = "";

        try {
            email = JWTUtils.getIssuer(token);
        } catch(SignatureVerificationException e) {

            return new ResponseEntity<>("토크 정보가 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 세션, 쿠키삭제
        // 클라이언트 쿠키/ 로컬스토리리 / 세션스토리지
        // 블랙리스트 작성

        return ResponseEntity.ok().build();
    }

    /**
     * 내가 작성한 게시글 목록을 리턴
     */
    @GetMapping("/api/user/board/post")
    public ResponseEntity<?> myPost(@RequestHeader("S-TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰정보가 정확하지 않습니다.");
        }

        List<Board> list = boardService.postList(email);
        return ResponseResult.success(list);
    }

    /**
     * 내가 작성한 게시글의 코멘트 목록을 리턴하는 API
     */
    @GetMapping("/api/user/board/comment")
    public ResponseEntity<?> myComments(@RequestHeader("S-TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰정보가 정확하지 않습니다.");
        }

        List<BoardComment> list = boardService.commentList(email);
        return ResponseResult.success(list);
    }

    /**
     * 사용자의 포인트 정보를 만들고 게시글을 작성할 경우, 포인트를 누적하는 API 작성
     */
    @PostMapping("/api/user/point")
    public ResponseEntity<?> userPoint(@RequestHeader("S-TOKEN") String token,
                          @RequestBody UserPointInput userPointInput) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰정보가 정확하지 않습니다.");
        }

        ServiceResult result = pointService.ADDPoint(email, userPointInput);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/public/user")
    public ResponseEntity<?> addUser(@RequestBody UserInput userInput) {

        ServiceResult result = userService.addUser(userInput);
        return ResponseResult.result(result);
    }

    /**
     * 비밀번호 초기화를 위해서 이메일로 인증코드를 전송하는 API
     */
    @PostMapping("/api/public/user/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserPasswordResultInput userPasswordResultInput, Errors errors) {

        if(errors.hasErrors()) {
            return ResponseResult.fail("입력값이 정확하지 않습니다.", ResponseError.of(errors.getAllErrors()));
        }

        ServiceResult result = null;
        try {
            result = userService.resetPassword(userPasswordResultInput);
        } catch(BizException e) {
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.result(result);
    }

}

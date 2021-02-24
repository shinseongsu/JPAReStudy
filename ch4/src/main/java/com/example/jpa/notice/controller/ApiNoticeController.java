package com.example.jpa.notice.controller;

import com.example.jpa.notice.Exception.DuplicateNoticeException;
import com.example.jpa.notice.Exception.NoticeNotFoundException;
import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.NoticeDeleteInput;
import com.example.jpa.notice.model.NoticeInput;
import com.example.jpa.notice.model.NoticeModel;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;

//    @GetMapping("/api/notice")
//    public String noticeString() {
//
//        return "공지사항입니다.";
//    }

//    @GetMapping("/api/notice")
//    public NoticeModel notice() {
//
//        LocalDateTime regDate = LocalDateTime.of(2021, 2,8,0,0);
//
//        NoticeModel notice = new NoticeModel();
//        notice.setId(1);
//        notice.setTitle("공지사항입니다.");
//        notice.setContents("공지사항 내용입니다.");
//        notice.setRegDate(regDate);
//
//        return notice;
//    }

//    @GetMapping("/api/notice")
//    public List<NoticeModel> noticeList() {
//
//        List<NoticeModel> noticeList = new ArrayList<>();
//
//        NoticeModel notice1 = new NoticeModel();
//        notice1.setId(1);
//        notice1.setTitle("공지사항입니다.");
//        notice1.setContents("공지사항내용입니다.");
//        notice1.setRegDate(LocalDateTime.of(2021, 1, 30, 0,0));
//        noticeList.add(notice1);
//
//        noticeList.add(NoticeModel.builder()
//                .id(2)
//                .title("두번쨰 공지사항입니다.")
//                .contents("두번째 공지사항입니다.")
//                .regDate(LocalDateTime.of(2021, 1, 31, 0, 0))
//                .build());
//
//        return noticeList;
//    }

    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {

        List<NoticeModel> noticeList = new ArrayList<>();

        return noticeList;      // null 하고는 다름.
    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {

        return 10;
    }

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(@RequestParam String title,
//                                 @RequestParam String contents) {
//
//        NoticeModel notice = NoticeModel.builder()
//                .id(1)
//                .title(title)
//                .contents(contents)
//                .regDate(LocalDateTime.of(2020, 2, 22, 0, 0))
//                .build();
//
//        return notice;
//    }

    // x-www-form-unlencoded
    @PostMapping("/api/notice")
    public NoticeModel addNotice(NoticeModel noticeModel) {

        noticeModel.setId(2);
        noticeModel.setRegDate(LocalDateTime.now());

        return noticeModel;
    }

    // json 형태로 파라미터를 받는다.
    @PostMapping("/api/notice2")
    public NoticeModel addNotice2(@RequestBody NoticeModel noticeModel) {

        noticeModel.setId(3);
        noticeModel.setRegDate(LocalDateTime.now());

        return noticeModel;
    }

    @PostMapping("/api/notice3")
    public Notice addNotice3(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);

        return notice;
    }

    @PostMapping("/api/notice4")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .build();

        Notice resultNotice =  noticeRepository.save(notice);

        return resultNotice;
    }

    @GetMapping("/api/notice/{id}")
    public Notice notice(@PathVariable long id) {

        Optional<Notice> notice = noticeRepository.findById(id);

        if(notice.isPresent()) {
            return notice.get();
        }
        return null;
    }

//    @PutMapping("/api/notice/{id}")
//    public void updateNotice(@PathVariable long id, @RequestBody NoticeInput noticeInput) {
//
//        Optional<Notice> notice = noticeRepository.findById(id);
//
//        if(notice.isPresent()) {
//            notice.get().setTitle(noticeInput.getTitle());
//            notice.get().setContents(noticeInput.getContents());
//            notice.get().setUpdateDate(LocalDateTime.now());
//            noticeRepository.save(notice.get());
//        }
//    }

    // 예외 처리 handler
    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable long id,
                             @RequestBody NoticeInput noticeInput) {

        /*
        Optional<Notice> notices = noticeRepository.findById(id);

        if(!notices.isPresent()) {
           // 예외 발생
            throw new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다.");
        }
        */

        Notice notices = noticeRepository.findById(id)
                .orElseThrow( () -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        // 정상적인 로직을 수행
        notices.setTitle(noticeInput.getTitle());
        notices.setContents(noticeInput.getContents());
        notices.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notices);
    }

    @PatchMapping("/api/notice/{id}/hits")
    public void noticeHits(@PathVariable Long id) {

        Notice notices = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        notices.setHits(notices.getHits() + 1);
        noticeRepository.save(notices);
    }

    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {

        Notice notices = noticeRepository.findById(id)
            .orElseThrow( () -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeRepository.delete(notices);
    }

    @DeleteMapping("/api/notice")
    public void deleteNoticesList(@RequestBody NoticeDeleteInput noticeDeleteInput) {
        List<Notice> noticeList = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
                                            .orElseThrow( () -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeList.stream().forEach(e -> {
            e.setDeleted(true);
            e.setDeletedDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(noticeList);
    }

    @DeleteMapping("/api/notice/all")
    public void deleteAll() {
        noticeRepository.deleteAll();
    }

    @PostMapping("/api/notice6")
    public void addNotices(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);
    }


    @PostMapping("/api/notice7")
    public ResponseEntity<Object> addNotices1(@RequestBody @Valid NoticeInput noticeInput
                        , Errors errors) {

        if(errors.hasErrors()) {
          List<ResponseError> responseErrors = new ArrayList<>();
           errors.getAllErrors().stream().forEach(e -> {
//               ResponseError responseError = new ResponseError();
//               responseError.setField(((FieldError)e).getField());
//               responseError.setMessage(e.getDefaultMessage());
//               responseErrors.add(responseError);
               responseErrors.add(ResponseError.of((FieldError) e));
           });

           return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

//        if(noticeInput.getTitle() == null
//                || noticeInput.getTitle().length() < 1
//                || noticeInput.getContents() == null
//                || noticeInput.getContents().length() < 1 ) {
//
//            return new ResponseEntity<>("입력값이 정확하지 않습니다.", HttpStatus.BAD_REQUEST);
//        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/notice8")
    public ResponseEntity<Object> addNotices2(@RequestBody @Valid NoticeInput noticeInput
            , Errors errors) {

        if(errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().stream().forEach(e -> {
               responseErrors.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/notice/latest/{size}")
    public Page<Notice> noticeLatest(@PathVariable int size) {

        Page<Notice> noticeList = noticeRepository.findAll(PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));

        return noticeList;
    }

    @ExceptionHandler(DuplicateNoticeException.class)
    public ResponseEntity<?> handlerDuplicationNoticeException(DuplicateNoticeException exception ) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/notice9")
    public void addNotices3(@RequestBody NoticeInput noticeInput) {

        // 중복체크
        LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);

//        Optional<List<Notice>> noticeList = noticeRepository.findByTitleAndContentsAndRegDateIsGreaterThanEqual(
//                noticeInput.getTitle()
//                , noticeInput.getContents()
//                , checkDate);
//
//        if(noticeList.isPresent()) {
//            if(noticeList.get().size() > 0) {
//                throw new DuplicateNoticeException("1분이내에 동록된 동일한 공지사항이 존재합니다.");
//            }
//        }

        int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
                noticeInput.getTitle()
                , noticeInput.getContents()
                , checkDate);

        if(noticeCount > 0 ) {
            throw new DuplicateNoticeException("1분이내에 동록된 동일한 공지사항이 존재합니다.");
        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());

    }


}

package com.example.jpa.notice.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.NoticeInput;
import com.example.jpa.notice.model.NoticeModel;
import com.example.jpa.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}

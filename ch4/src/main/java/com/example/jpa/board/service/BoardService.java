package com.example.jpa.board.service;

import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.*;

import java.util.List;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult updateBoard(long id, BoardTypeInput boardTypeInput);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getAllBoardType();

    /**
     * 게시판 타입의 사용여부를 설정
     *
     * @param id
     * @param boardTypeUsing
     * @return
     */
    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    /**
     * 게시판 타입의 게시글 수를 리턴
     *
     * @return
     */
    List<BoardTypeCount> getBoardTypeCount();

    /**
     * 게시글을 최상단에 배치하는 API
     *
     * @param id
     * @return
     */
    ServiceResult setBoardTop(Long id);

    ServiceResult setBoardTop(Long id, boolean flag);

    /**
     * 게시의 게시기간을 설정
     */
    ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod);

    /**
     * 게시글의 조회수 증가
     */
    ServiceResult setBoardHits(Long id, String email);

    /**
     * 게시글의 좋아요를 증가
     */
    ServiceResult setBoardLike(Long id, String email);

    /**
     * 게시글의 좋아요를 취소함
     */
    ServiceResult setBoardUnLike(Long id, String email);
}

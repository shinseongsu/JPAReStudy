package com.example.jpa.user.repository;

import com.example.jpa.user.model.UserLogCount;
import com.example.jpa.user.model.UserNoticeCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserCustomRepository {

    private final EntityManager entityManager;


    public List<UserNoticeCount> findUserNoticeCount() {

        String query = " select u.id, u.email, u.user_name, (select count(*) from Notice n where n.user_id = u.id) notice_count from USER u ";

        List<UserNoticeCount> List = entityManager.createNativeQuery(query).getResultList();
        return List;
    }


    public List<UserLogCount> findUserLogCount() {

        String query = " select u.id, u.email, u.user_name, " +
                       " (select count(*) from Notice n where n.user_id = u.id) notice_count, " +
                       " (select count(*) from Notice_Like n where n.user_id = u.id) notice_like_count " +
                       " from USER u ";

        List<UserLogCount> List = entityManager.createNativeQuery(query).getResultList();
        return List;

    }

    public List<UserLogCount> findUserLikeBest() {
        String query = " SELECT t1.id, t1.email, t1.user_name, t1.notice_like_count "
                     + " from  ( "
                     + "  SELECT u.*, (select count(*) from notice_like nl where nl.user_id = u.id) as notice_like_count "
                     + "  FROM user u "
                     + " ) t1 "
                     + " order by t1.notice_like_count desc ";

        List<UserLogCount> List = entityManager.createNativeQuery(query).getResultList();
        return List;
    }
}

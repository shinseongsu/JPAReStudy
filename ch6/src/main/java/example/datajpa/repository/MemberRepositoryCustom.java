package example.datajpa.repository;

import example.datajpa.entity.Member;

import java.util.List;

//사용자 정의 리포지토리 인터페이스
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}

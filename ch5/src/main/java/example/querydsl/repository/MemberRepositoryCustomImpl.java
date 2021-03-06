package example.querydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.querydsl.dto.MemberSearchCondition;
import example.querydsl.dto.MemberTeamDto;
import example.querydsl.dto.QMemberTeamDto;
import example.querydsl.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static example.querydsl.entity.QMember.*;
import static example.querydsl.entity.QTeam.*;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDto(
                            member.id.as("memberId"),
                            member.username,
                            member.age,
                            team.id.as("teamId"),
                            team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset()) // 몇번째 데이터부터 시작할거야
                .limit(pageable.getPageSize()) // 한 페이지에 몇개 데이터 가져올거야
                .fetchResults();//fetch로 가져오면 데이터 컨텐츠를 바로 가져옴

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset()) // 몇번째 데이터부터 시작할거야
                .limit(pageable.getPageSize()) // 한 페이지에 몇개 데이터 가져올거야
                .fetch();//fetch로 컨텐츠만 가져온다 (count쿼리 안날림)

        //count쿼리 따로날림 (select projection 단순화)
        JPAQuery<Member> countQuery = queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                ); //여기서 fetchCount()해야 실제 카운트 쿼리 날라감

        /**
         * 이 유틸이 카운트 쿼리 최적화해줌
         * - 첫번째 페이지고 컨텐츠 사이즈가 페이지 사이즈보다 작을 때
         * - 마지막 페이지 일때 (offset + 컨텐츠 사이즈 = 전체 사이즈)
         * 위 두가지 경우 count쿼리 안날림
         */
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
//        return new PageImpl<>(content, pageable, total);

    }


    //반환형을 Predicate보다는 BooleanExpression해주는게 낫다
    private BooleanExpression usernameEq(String username) {
        //부정조건 StringUtils.isEmpty(), 긍정조건 StringUtils.hasText()
        return isEmpty(username) ? null : member.username.eq(username);
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
}

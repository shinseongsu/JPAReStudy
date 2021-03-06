package example.datajpa.entity;

import example.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() throws Exception {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush(); //db에 영속성 컨텍스트 반영
        em.clear(); //영속성 컨텍스트의 캐시 다 날림


        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        members.forEach(m-> {
            System.out.println("member = " + m);
            System.out.println("-> member.team = " + m.getTeam());
        });

    }

    @Test
    public void jpaTestBaseEntity() throws Exception {
        //given
        Member member = new Member("member1");

        memberRepository.save(member);//@PrePersist 발생

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush();//@PreUpdate 발생
        em.clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        System.out.println("findMember.createdDate = " + findMember.getCreatedDate());
        System.out.println("findMember.updatedDate = " + findMember.getLastModifiedDate());
        System.out.println("findMember.createdBy = " + findMember.getCreatedBy());
        System.out.println("findMember.modifiedBy = " + findMember.getLastModifiedBy());

    }

}

package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;


    @Test
    public void testEntity() {
        //given
        Team teamA = new Team("파리생제르망");
        Team teamB = new Team("바르셀로나");

        em.persist(teamA);
        em.persist(teamB);

        //when
        Member member1 = new Member("음바페",20,teamA);
        Member member2 = new Member("이카르디",23,teamA);
        Member member3 = new Member("메시",31,teamB);
        Member member4 = new Member("피케",33,teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();
        em.clear();
        //then

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " +member);
            System.out.println("member.team = " +member.getTeam());

        }
    }




}
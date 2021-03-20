package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        //given
        Member member = new Member("memberA");
        Member saveMember  = memberJpaRepository.save(member);


        //when
        Member findMember = memberJpaRepository.find(saveMember.getId());


        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("음바페");
        Member member2 = new Member("홀란드");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //단건 조회 검사
        Member findmember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findmember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findmember1).isEqualTo(member1);
        assertThat(findmember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);


        //count 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long count1 = memberJpaRepository.count();
        assertThat(count1).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        Member m3 = new Member("AAA",22);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 15);

        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findByUsername(){

        Member mm = new Member("음바페", 20);
        Member m2 = new Member("홀란드",21);

        memberJpaRepository.save(mm);
        memberJpaRepository.save(m2);

        List<Member> findMember = memberJpaRepository.findByUsername("음바페");

        assertThat(mm.getUsername()).isEqualTo(findMember.get(0).getUsername());
        assertThat(findMember.size()).isEqualTo(1);
    }

    @Test
    public void paging(){

        //given
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",10));
        memberJpaRepository.save(new Member("member3",10));
        memberJpaRepository.save(new Member("member4",10));
        memberJpaRepository.save(new Member("member5",10));
        memberJpaRepository.save(new Member("member77",15));

        int age = 10;
        int offset = 0;
        int limit = 3;


        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        // 페이지 계산 공식 적용...
        //totalPage = totalCount / size...
        // 마지막 페이지...
        // 최초 페이지
        //then

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);


    }

}
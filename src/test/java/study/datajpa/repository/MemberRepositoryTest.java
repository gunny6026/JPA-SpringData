package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void TestMember() {
        //given
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);


        //when
        Member findMember = memberRepository.findById(saveMember.getId()).get();


        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());


    }
    @Test
    public void basicCRUD(){
        Member member1 = new Member("음바페");
        Member member2 = new Member("홀란드");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검사
        Member findmember1 = memberRepository.findById(member1.getId()).get();
        Member findmember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findmember1).isEqualTo(member1);
        assertThat(findmember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);


        //count 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long count1 = memberRepository.count();
        assertThat(count1).isEqualTo(0);
    }


    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        Member m3 = new Member("AAA",22);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(2);
    }


    @Test
    public void findByUsername(){

        Member mm = new Member("음바페", 20);
        Member m2 = new Member("홀란드",21);

        memberRepository.save(mm);
        memberRepository.save(m2);

        List<Member> findMember = memberRepository.findByUsername("음바페");

        assertThat(mm.getUsername()).isEqualTo(findMember.get(0).getUsername());
        assertThat(findMember.size()).isEqualTo(1);
    }

    @Test
    public void testRepositoryQuery(){

        Member mm = new Member("음바페", 20);
        Member m2 = new Member("홀란드",21);

        memberRepository.save(mm);
        memberRepository.save(m2);

        List<Member> findMember = memberRepository.findUser("음바페",20);

        assertThat(findMember.get(0)).isEqualTo(mm);

        assertThat(mm.getUsername()).isEqualTo(findMember.get(0).getUsername());
        assertThat(findMember.size()).isEqualTo(1);
    }


}

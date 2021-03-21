package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager entityManager;



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

    @Test
    public void findUsernameList(){

        Member mm = new Member("음바페", 20);
        Member m2 = new Member("홀란드",21);

        memberRepository.save(mm);
        memberRepository.save(m2);

        List<String> findUsernameList = memberRepository.findUsernameList();

       assertThat(findUsernameList.get(0)).isEqualTo("음바페");
       assertThat(findUsernameList.size()).isEqualTo(2);
    }

    @Test
    public void findMemberDto(){

        Team t1 = new Team("파리 생제르망");
        teamRepository.save(t1);

        Member mm = new Member("음바페", 20);
        mm.setTeam(t1);
        memberRepository.save(mm);

        List<MemberDTO> memberDto = memberRepository.findMemberDto();

        for (MemberDTO memberDTO : memberDto) {
        System.out.println("memberDtO ====> " +memberDTO);

        }

    }

    @Test
    public void findByNamesTest(){

        Team t1 = new Team("파리 생제르망");
        teamRepository.save(t1);

        Member mm = new Member("음바페", 20);
        mm.setTeam(t1);
        Member m2 = new Member("즐라탄",41);
        Member m3 = new Member("호날두",36);
        Member m4 = new Member("메시",34);
        memberRepository.save(mm);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);

//        List<String> usernameList = memberRepository.findUsernameList();
       List<String> array = new ArrayList<>();
       array.add("호날두");
       array.add("메시");

//        List<Member> byNames = memberRepository.findByNames(array);
        List<Member> byNames = memberRepository.findByNames(Arrays.asList("즐라탄","음바페"));
        for (Member byName : byNames) {
            System.out.println("byName = "+byName);
        }

    }

    @Test
    public void paging(){

        //given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));
        memberRepository.save(new Member("member77",15));


        int age =10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        Page<MemberDTO> toMap = page.map(m -> new MemberDTO(m.getId(), m.getUsername(), null));

        //then
        List<Member> content = page.getContent();


        long totalElements = page.getTotalElements();


//        for (Member member : content) {
//            System.out.println("member = " + member);
//        }
//        System.out.println("totalElements = " +totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();


    }

    @Test
    public void bulkUpdate(){


        memberRepository.save(new Member("member1",20));
        memberRepository.save(new Member("member2",30));
        memberRepository.save(new Member("member3",31));
        memberRepository.save(new Member("member4",32));
        memberRepository.save(new Member("member5",33));
        memberRepository.save(new Member("member6",34));

        int i = memberRepository.bulkAgePlus(33);
//        entityManager.flush();
//        entityManager.clear();

        List<Member> result = memberRepository.findByUsername("member6");

        Member member = result.get(0);

        System.out.println("member = "+member);

        assertThat(i).isEqualTo(2);


    }

    @Test
    public void findMemberLazy() {
        //given

        //member1 => teamA
        //member2 => teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        entityManager.flush();
        entityManager.clear();

        //when
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("members = " + member.getUsername());
            System.out.println("members.team = "+member.getTeam().getName());
        }

        //then

    }

    @Test
    public void queryHint() {
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();
        //when

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
//        findMember.setUsername("member2");


        entityManager.flush();
        //then

    }

    @Test
    public void lock() {
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();
        //when


        List<Member> result = memberRepository.findLockByUsername("member1");
//        findMember.setUsername("member2");


        entityManager.flush();
        //then

    }


    @Test
    public void callCustom(){
        List<Member> memberCustom = memberRepository.findMemberCustom();
    }


}

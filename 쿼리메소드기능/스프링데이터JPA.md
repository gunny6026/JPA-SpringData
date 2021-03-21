## 스프링 데이터 JPA 페이징과 정렬

`페이징과 정렬 파라미터`

__org.springframework.data.domain.Sort__ : 정렬기능
__org.springframework.data.domain.Pageable__ : 페이징 기능

`특별한 반환 타입`

__org.springframework.data.domain.Page__ : 추가 count 쿼라 결과를 포함하는 페이징
__org.springframework.data.domain.Slice__ : 추가 count 쿼리 없이 다음 페이지만 확인 가능(내부적으로 limit +1조회)
__List__ :(자바 컬렉션) : 추가 count 쿼리 없이 결과만 반환

```인터페이스```
```java
 Page<Member> findByAge(int age, Pageable pageable);
```

`테스트 케이스`
```java

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
```

실무에서 사용 할 때 
```java
    Page<MemberDTO> toMap = page.map(m -> new MemberDTO(m.getId(), m.getUsername(), null));
```
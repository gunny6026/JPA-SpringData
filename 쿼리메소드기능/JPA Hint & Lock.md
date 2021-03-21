# JPA Hint & Lock
****

## JPA Hint
JPA 쿼리 힌트(SQL 힌트가 아니라 JPA 구현체에게 제공하는 힌트)

```java
  @Test
    public void queryHint() {
        //given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();
        //when

        Member findMember = memberRepository.findById(member1.getId()).get();
//        findMember.setUsername("member2");


        entityManager.flush();
        //then
```

여기서 member의 이름을 바꾸는 로직이 아니라

그냥 단순히 조회만 할려고 findById로 데이터 가져와도

JPA는 하나의 객체를 더 생성하게 된다.

근데 단순히 조회만 할 건데 객체 데이터를 2개(원본에 해당하는 member1 , 새로 가져온 findMember)

들고 있으면 메모리 낭비이다.

```java

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly",value = "true"))
    Member findReadOnlyByUsername(String username);
```


```java
Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();
        //when

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
```

그러면 @QueryHints 설정을 했기 때문에 

알아서 성능 최적화를 위해 새로 객체를 생성하지 않는다.

즉,snapshot도 없기 때문에 변경감지 체크도 하지 않는다.

## JPA Lock

```java
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
```


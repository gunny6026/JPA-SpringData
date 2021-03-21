## @EntityGraph

연관된 엔티티를 조회하는 방법

```java
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
```

fetch 전략이 LAZY이기 때문에

```java
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("members = " + member.getUsername());
            System.out.println("members.team = "+member.getTeam().getName());
        }

```

처음에 member에 대한 정보를 호출 할 때

select를 member 엔티티에서만 하게 된다.

그러다가 team를 호출 하면 select를 해서 team에 대한 데이터를 가져온다.

그리고 또 member에 대한 데이터를 가져올때는 영속성 콘텍스트 1차캐시에

데이터가 있기 때문에 select 하지 않고 다음 member 객체를 가져온다

하지만 team은 가짜 객체 , 즉 엔티티가 아닌 프록시이기 때문에

다음 team에 대한 정보를 가져올 때 또 select를 한다.

이것을 N+1 문제라고한다.

이 문제를 해결하려면

```java

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();
```

fetch join을 사용한다.

```java

        //when
        List<Member> members = memberRepository.findMemberFetchJoin();

        for (Member member : members) {
            System.out.println("members = " + member.getUsername());
            System.out.println("members.team = "+member.getTeam().getName());
        }
```

그러면 이제는 가짜 객체 프록시가 아니라

진짜 team 엔티티 객체를 가져온다.

근데, 이렇게 JPQL로 하기 귀찮으면

원래 인터페이스에 있는 findAll을 오버라이딩 한뒤
```java
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
```

JPQL @Query 대신 EntityGraph 어노테이션을 사용하면 된다.


*************
또, 내가 직접 @Query JPQL로 쿼리를 만들고, @EntityGraph를 쓰고 싶으면

```java
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();
```

이렇게 하면 된다.
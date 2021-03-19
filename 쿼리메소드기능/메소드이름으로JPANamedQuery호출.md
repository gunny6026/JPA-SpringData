# JPA NamedQuery


작성

멤버 엔티티

`Member.java`
```java
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    
    private String username;
    private int age;
    .....

}
}
```

---------------------
### 순수 JPA 로 호출


`MemberJpaRepository.java`
```java
public List<Member> findByUsername(String username){
        return em.createNamedQuery("Member.findByUsername" ,Member.class)
                .setParameter("username","박건희")
                .getResultList();
    }
```
***********

### 스프링 데이터 JPA로 호출

```java
 @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);
```

`//    @Query(name = "Member.findByUsername")`

위에 @Query는 생략 가능하다.

대신 Member 엔티티에 NamedQuery 이름이 findByUsername과 일치해야한다.

일치하면 인터페이스가 자동으로 Member 다음에 .을 찍고 findByusername을 붙인 다음

@NamedQuery가 있으면 실행한다. 만약 없으면 메소드 이름으로 쿼리를 생성한다.



단, NamedQuery는 실무에서 많이 사용하지 않는다고 한다.
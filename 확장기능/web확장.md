# Web 확장

## Web 확장 - 도메인 클래스 컨버터

HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩

```java
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){

        Member member = memberRepository.findById(id).get();

        return member.getUsername();
    }
    // 도메인 클래스 컨버터
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){



        return member.getUsername();
    }

    @PostConstruct
    public void init(){
        memberRepository.save(new Member("useA"));
    }
}

```

- HTTP 요청은 회원 `id`를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
- 도메인 클래스 컨버터도 리포지토리를 사용해서 엔티티를 찾음

__주의__ : 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다.
(트랜잭션이 없는 범위에서 엔티티를 조회했으므로, 엔티티를 변경해도 DB에 반영되지 않는다.)



## Web 확장 - 페이징과 정렬

스프링 데이터가 제공하는 페이징과 정렬 기능을 스프링 MVC에서 편리하게 사용 할 수 있다.

```java
@GetMapping("/members")
    public Page<Member> list(Pageable pageable){

        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }
```

- 파라미터로 `Pageable`을 받을 수 있다.
- `Pageable`은 인터페이스, 실제는 `org.springframework.data.domain.PageRequest` 객체 생성


`요청 파라미터`

- 예) `/members?page=0&size=3$sort=id,desc$sort=username,desc`
- page : 현재페이지 , 0부터 시작한다.
- size : 한 페이지에 노출할 데이터 건수
- sort : 정렬 조건을 정의한다. 예) 정렬 속성, 정렬속정...(ASC | DESC), 정렬 방향을 변경
하고 싶으면 `sort` 파라미터 추가(asc 생략가능)
  

`기본값`

글로벌 설정

__application.properties__

```java
spring.data.web.pageable.default-page-size = 10
```

개별설정 - `@PageableDefault ` 어노테이션 사용

```java
 @GetMapping("/members")
    public Page<Member> list(@PageableDefault(size = 5,sort = "username") Pageable pageable){

        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }
```

__접두사__

- 페이징 정보가 둘 이상이면 접두사로 구분
- `@Qualifier`에 접두사명 추가 "(접두사명)_xxx"
- 예제 : `/members?member_page=0&order_page=1`

```java 
public String list{
    @Qualifier("member") Pageable memberPageable,
    @Qualifier("order") Pageable orderPageable, ...    
        }
```



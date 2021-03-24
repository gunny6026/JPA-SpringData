## Auditing

### 순수 JPA 

- 엔티티를 생성, 변경할 때 변경한 사람과 시간을 추적하고 싶으면?
    - 등록일
    - 수정일
    - 등록자
    - 수정자

__JpaBaseEntity.java__    
```java
@Getter
@MappedSuperclass
public class JpaBaseEntity {


    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;

    }
    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
```

`JPA 주요 이벤트 어노테이션`

- @PrePersist , @PostPersist
- @PreUpdate , @PostUpdate

```java
public class Member extends JpaBaseEntity
public class Team extends JpaBaseEntity
```

### 스프링 데이터 JPA

__설정__

`@EnableJpaAuditing` -> 스프링 부트 설정 클래스에 적용해야함
`@EntityListeners(AuditingEntityListener.class)` -> 엔티티에 적용


__사용 어노테이션__

- `@CreatedDate`
- `@LastModifiedDate`
- `@CreatedBy`
- `@LastModifiedBy`

@EnableJpaAuditing 설정

```java
@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider(){

		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
```

어노테이션 추가
```java

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

}

```

```java
public class Member extends BaseEntity {

    ...

}
```
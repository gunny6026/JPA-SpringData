## 순수 JPA 페이징과 정렬

JPA에서 페이징을 어떻게 할 것인가?

다음 조건으로 페이징과 정렬을 사용하는 예제 코드를 보자

- 검색 조건 : 나이가 10살
- 정렬 조건 : 이름으로 내림차순
- 페이징 조건 : 첫번째 페이지,  페이지당 보여줄 데이터는 3건


```java
   public List<Member> findByPage(int age, int offset , int limit){
     return    em.createNamedQuery("select m from Member m where m.age = :age order by m.username desc")
        .setParameter("age",age)
        .setFirstResult(offset)
        .setMaxResults(limit)
        .getResultList();

    }

    public long totalCount(int age){

        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age",age)
                .getSingleResult();
    }
```

`테스트 케이스`
```java

```
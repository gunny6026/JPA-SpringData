# 실전! 스프링 데이터 JPA

## 프로젝트 환경설정
- [프로젝트생성](프로젝트환경설정/프로젝트생성.md)

## 공통 인터페이스 기능

- [순수JPA기반리포지토리](src/main/java/study/datajpa/repository/MemberJpaRepository.java)
- [순수JPA-테스트케이스](src/test/java/study/datajpa/repository/MemberJpaRepositoryTest.java)
- [스프링데이터JPA-공통인터페이스](src/main/java/study/datajpa/repository/MemberRepository.java)
- [스프링데이터JPA-테스트케이스](src/test/java/study/datajpa/repository/MemberRepositoryTest.java)
- [스프링데이터JPA-공통인터페이스](공통인터페이스기능/공통인터페이스.md)


## 쿼리 메소드 기능

- [메소드이름으로 쿼리생성](쿼리메소드기능/메소드이름으로%20쿼리생성.md)
- [메소드 이름으로 JPA NamedQuery 호출](쿼리메소드기능/메소드이름으로JPANamedQuery호출.md)
- [리포지토리메소드에쿼리정의하기,@Query](쿼리메소드기능/리포지토리메소드에쿼리정의.md)
- [반환타입](쿼리메소드기능/반환타입.md)
- [순수JPA페이징과정렬](쿼리메소드기능/순수JPA페이징과정렬.md)
- [스프링데이터JPA](쿼리메소드기능/스프링데이터JPA.md)
- [벌크성수정쿼리](쿼리메소드기능/벌크성수정쿼리.md)
- [@EntityGraph](쿼리메소드기능/@EntityGraph.md)
- [JPA Hint & Lock](쿼리메소드기능/JPA%20Hint%20&%20Lock.md)

## 확장기능

- [사용자정의 리포지토리 구현](확장기능/사용자정의리포지토리구현.md)
- [Auditing](확장기능/auditing.md)
- [Web 확장 -도메인 클래스 컨버터 & 페이징과 정렬](확장기능/web확장.md)

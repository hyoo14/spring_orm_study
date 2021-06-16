# SPRING_MVC_STUDY

스프링 MVC 1편 (https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-mvc-1/dashboard)

<br>
## 1. JPA 소개
- maven : pom.xml == gradle : build.gradle

<br>
## 2. JPA 시작하기
- JPA는 Transaction 단위로 무조건 실행해야됨 -> EntityTransaction의 begin() 담에 JPA (SQL로 변환됨) 넣어주고 commit()해야 실행됨
- EntityManager는 스레드 간 공유 불가 -> close() 필수
- EntityManagerFactory도 Application이 끝나면 close() 필수
+ C : em.persist()
+ R : em.find()
+ U : class.setName() 등
+ D : em.remove(class)
- 복잡한 Query : em.createQuery("select m from Member as m", Member.class) 등으로 직접 객체지향적 쿼리 (JPQL) 가능
-실행 후 Console에서 Hibernate 부분 보면 해당 DB Dialect에 맞는 SQL 쿼리로 자동 변환되어 실행되는 걸 볼 수 있음

<br>
## 3. 영속성 관리 - 내부 동작 방식
- 비영속 : class 객체만 생성한 상황
- 영속 : persist()하면 관리되고, commit()해야 DB에 반영
- 준영속 : 객체 생성 후 detach() 하면 준영속으로 보류됨
- 삭제 : remove() 하면 완전히 삭제됨


### 영속성의 특징

1. 캐시를 활용한 효율성 증대
- EntityManager는 1차 캐시가 있고, 영속화 하면 캐시에 객체가 들어간 상태
- 조회 시 DBMS는 1차 캐시를 뒤진 후, DB를 뒤짐 (OS나 인터넷 캐시처럼 효율성 증대가 목적)
+ 캐시에 있으면 바로 가져오고
+ 캐시에 없으면 DB에서 찾아 가져온 뒤 캐시에 저장

2. 영속 엔티티의 동일성 보장
- 같은 객체 (예를 들어 pk가 같은)를 참조 시 같은 객체로 간주 (동일성 보장)

3. 트랜잭션을 지원하는 쓰기 지연
- commit()해야만 JPA를 SQL 변환 후 DB에 반영하는 쓰기 지연 발생
- 대기 중인 여러 개의 SQL은 Queue에 들어간 뒤 순서보장 + Batch 처리로 성능향상 가능

4. 엔티티 수정 시 변경 감지 (dirty checking)
- class.setName() 등으로 변경만 하면 persist 안해도 자동 반영됨 (SQL을 객체지향 프로그래밍 하듯 코딩할 수 있음)
- commit() 시, snapshot과 현 entity를 비교해서 달라지면 update 쿼리 날림

5. 엔티티 삭제
- em.remove() = delete 쿼리

### 플러시
- 변경감지 후 쓰기 지연 CRUD 쿼리를 DB에 반영하는 단계
- em.flush() / 트랜잭션 commit or JPQL 실행 시 자동 실행
- em.setFlushMode(FlushModeType.xxx)
+ xxx = AUTO : commit or JPQL 시 플러시 (default)
+ xxx = COMMIT : commit 할 때만 플러시

### 준영속
- em.detatch() : 특정 엔티티가 영속 -> 준영속이 됨
- em.clear() : 모든 엔티티 통채로 준영속화 (1차 캐시 초기화)
- em.close() : 영속성 컨텍스트를 닫아버림 (모든 엔티티가 관리 안됨)
- 준영속 : 커밋할 때 해당 트랜잭션이 commit 안됨 (영속성 컨텍스트에서 관리 해제)


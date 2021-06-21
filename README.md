# SPRING_ORM_STUDY

## 1 - JPA 소개
- maven : pom.xml == gradle : build.gradle

## 2장 - JPA 시작하기
- JPA는 Transaction 단위로 무조건 실행해야됨 -> EntityTransaction의 begin() 담에 JPA (SQL로 변환됨) 넣어주고 commit()해야 실행됨
- EntityManager는 스레드 간 공유 불가 -> close() 필수
- EntityManagerFactory도 Application이 끝나면 close() 필수
+ C : em.persist()
+ R : em.find()
+ U : class.setName() 등
+ D : em.remove(class)
- 복잡한 Query : em.createQuery("select m from Member as m", Member.class) 등으로 직접 객체지향적 쿼리 (JPQL) 가능
-실행 후 Console에서 Hibernate 부분 보면 해당 DB Dialect에 맞는 SQL 쿼리로 자동 변환되어 실행되는 걸 볼 수 있음

## 3장 - 영속성 관리 - 내부 동작 방식
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

## 4장 - 엔티티 매핑

### 객체와 테이블 매핑
1. @Entity
+ 이게 붙은 클래스는 JPA가 관리하는 엔티티가 됨
+ '기본 생성자' 필수 (non-parameter인 public / protected constructor)
+ final, enum, interface, inner class (X), final field (X)
+ @Entity(name = "XXX") : XXX가 엔티티 테이블 이름이 됨 (설정 안하면 class name이 default)

※ persistence.xml
+ <property name="hibernate.hbm2ddl.auto" value="create" /> : @Entity로 등록된 모든 클래스를 DDL함
+ <property name="hibernate.hbm2ddl.auto" value="update" /> : @Entity에 대해 ALTER TABLE 해줌
+ value는 create, create-drop, update, validate, none 다섯 종류
+ 로컬 개발 할 때나 쓰고, 운영 시에는 validate, none만 써야 안전함

2. @Table
+ JPA에서 관리하는 엔티티와는 다르게, 실제 DB Table 이름을 설정할 수 있음
+ @Table(name = "XXX") : XXX가 Table Name이므로 JPA는 쿼리를 만들 때 'select * from XXX' 이런 식으로 변환해줌

3. @Id
+ field를 PK로 설정

4. @Column
+ field를 Table의 Column으로 설정
+ @Column(unique=true, nullable=false, length=10) : 중복불가, 널불가, 최대10자리 등의 제약조건 줄 수 있음
 
5. @Enumerated
+ Java의 Enum 클래스를 매핑
+ DB에는 Enum 속성이 없으므로 아래처럼 매핑해줘야됨
+ @Enumerated(EnumType.STRING) : Enum을 String 타입으로 매핑
+ EnumType.ORDINAL로 하면 클래스에 새 enum 넣을 시 순서가 꼬이므로 위험함

6. @Temporal
+ Java의 Date나 Calendar 클래스를 매핑
+ @Temporal(TemporalType.TIMESTAMP) : Timestamp 타입으로 매핑
+ TemporalType은 DATE (2012-01-01처럼 날짜만), TIME (13:00:00처럼 시간만), TIMESTAMP (날짜+시간)

7. @Lob
+ Blob, Clob 같은 큰 데이터를 넣을 때 사용
+ 자바 클래스가 문자면 (String, char[] 등) CLOB, 나머지 (byte[] 등)는 BLOB으로 매칭

8. @Transient
+ DB에 Column으로 반영하지 않음 (노 매핑)


### 기본 키 매핑

1. 직접 할당 : @Id만 쓰고 로직으로 할당해줘야됨
2. 자동 생성 : @GeneratedValue 사용
- @GeneratedValue(strategy=GenerationType.AUTO) : 자동으로 DB Dialect에 맞춰 할당
- IDENTITY : MySQL 등 (영속성 1차캐시에서 Null로 들고 있다가 DB에 들어가는 순간 정해짐)
- SEQUENCE : Oracle 등 (Sequence Object 생성 후 Increment)
- 클래스에 @SequenceGenerator 생성 후 @GeneratedValue(generator="SEQ_NAME")에 넘겨줘도 됨


### Table 전략
- 키 생성 전용 Table을 만들어서 Sequence처럼 사용하는 대체전략
- 모든 DB에 적용 가능한 장점 / 성능이 떨어지는 단점
- 클래스에 @TableGenerator 생성
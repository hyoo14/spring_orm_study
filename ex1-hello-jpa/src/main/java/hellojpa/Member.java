package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity //이거 꼭 넣어야함. jpa가 사용하는 애구나 하고 인식함.
// @Table(name = "USER") //테이블 매핑용 db테이블로 매핑 //테이블명 바꿀 때 사용 굿
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
//@TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50) //DB에 50개 넣어놓고. 메모리에서 1씩 씀. 50개 다 쌓이면 NEXT CALL 한번 호출.
public class Member {


    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
//    @GeneratedValue(strategy = GenerationType.TABLE,
//            generator = "MEMBER_SEQ_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", nullable = false) //이 컬럼을 반영 할 거냐 말 거냐
    private String username;

    public Member(){
        //기본 생성자가 있어야 요류가 안 남.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

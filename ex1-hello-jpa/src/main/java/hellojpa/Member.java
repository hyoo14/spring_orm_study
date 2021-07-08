package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //이거 꼭 넣어야함. jpa가 사용하는 애구나 하고 인식함.
// @Table(name = "USER") //테이블 매핑용 db테이블로 매핑 //테이블명 바꿀 때 사용 굿
public class Member {


    @Id
    private Long id;

    //@Column(name = "username") //테이블 매핑
    @Column(unique = true, length = 10) //ddl 생성기능
    private String name;
    private int gogos2;

    public Member(){
        //기본 생성자가 있어야 요류가 안 남.
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

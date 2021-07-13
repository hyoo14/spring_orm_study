package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;


@Entity
public class Member extends BaseEntity{


    @Id    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY) //멤버 클래스만 db서 조회
    @JoinColumn(name = "TEAM_ID") // , insertable = false, updatable = false)//읽기전용으로 만들어 준 것! //억지로 맞춘느낌!
    private Team team;


    public Long getId() {        return id;    }

    public void setId(Long id) {        this.id = id;    }

    public String getUsername() {        return username;    }

    public void setUsername(String username) {        this.username = username;    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


}

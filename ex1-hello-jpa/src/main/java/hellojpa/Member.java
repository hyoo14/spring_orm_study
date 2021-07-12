package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;


@Entity
public class Member {


    @Id    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID" , insertable = false, updatable = false)//읽기전용으로 만들어 준 것! //억지로 맞춘느낌!
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();


    public Long getId() {        return id;    }

    public void setId(Long id) {        this.id = id;    }

    public String getUsername() {        return username;    }

    public void setUsername(String username) {        this.username = username;    }


}

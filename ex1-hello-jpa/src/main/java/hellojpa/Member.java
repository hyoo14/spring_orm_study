package hellojpa;

import javax.persistence.*;


@Entity
public class Member {


    @Id    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name= "TEAM_ID")
//    private Long teamId; //이렇게 안 쓰고 연관관계를 쓰자!

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

//    public void changeTeam(Team team) {
//        this.team = team;
//
//        //아예 양방향을 여기서 고려.
//        team.getMembers().add(this);
//    }
    //양쪽 다 있으면 안 되니 지워줌


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team + //team.toString 또 호출한다는 말.
                '}';
    }
}

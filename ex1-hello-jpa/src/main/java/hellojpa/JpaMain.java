package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);//member.setTeamId(team.getId()); 
            em.persist(member);

            em.flush();
            em.clear(); //영속성컨텍스트 말고 db 쿼리 날리는 거 보고 싶으면 이렇게 영속성 날려주고 보면 됨

            Member findMember = em.find(Member.class, member.getId());

            //Long findTeamId = findMember.getTeamId();
            //Team findTeam = em.find(Team.class, findTeamId); //연관관계가 없어서 객체지향스럽지 않게 계속 이렇게 조회해야..
            Team findTeam = findMember.getTeam(); //이렇게 바로 쓸 수 있음 //객체지향 스럽게 레퍼런스 잘 가져올 수 있두아!
            System.out.println("findTeam = " + findTeam.getName());

            //
            Team newTeam = em.find(Team.class, 100L); //있다가정하고
            findMember.setTeam(newTeam);  //팀에서 바꿔주면 외래키값 업데이트,, 수정할 수 있다!

            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();

    }
}

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
            //team.getMembers().add(member);//가짜매핑이라 안 됨.. 이렇게 해야함-> //member.setTeam(team);//member.setTeamId(team.getId());
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            //member.changeTeam(team);//원래는 개터새터 지양하므로 명명 변경 //member.setTeam(team); //**
            em.persist(member);

            team.addMember(member);
            //team.getMembers().add(member); //** //양방향 연관관계 세팅해줄 때는 양 쪽에 다 해줘야. -> 이걸 결과적으로 member entity 세터에서 처리
            em.flush();
            em.clear(); //영속성컨텍스트 말고 db 쿼리 날리는 거 보고 싶으면 이렇게 영속성 날려주고 보면 됨

            Team findTeam = em.find(Team.class, team.getId()); //1차 캐시 - 플러시 클리어 안 하면.
            List<Member> members = findTeam.getMembers(); //db서 셀렉쿼리 안 나감

            System.out.println("===============");
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//
//            }
            System.out.println("members = " + findTeam); //무한루프에 빠짐
            System.out.println("===============");


            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();

    }
}

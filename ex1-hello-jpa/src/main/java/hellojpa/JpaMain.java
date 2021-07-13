package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("hello1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("hello2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId()); //jpa가 자체적으로 해줌

            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class) //jpql은 쿼리 다 가져오고
                    .getResultList(); //멤버개수만큼 즉시로딩 다 함

            //SQL: select * from Member
            //SQL: select * from Team where TEAM_ID = xxx //쿼리 엄청 나감
//
//            System.out.println("m = " + m.getTeam().getClass()); //Proxy
//
//            System.out.println("=========================");
//            m.getTeam(); //여기서는 프록시 가져오니까 쿼리를 가져오지는 않음!
//            m.getTeam().getName(); //디비서 값 가져옴. 지연로딩 세팅하면 연관된 것을 프록시로 가져오는 것!
//            System.out.println("=========================");

            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally{
            em.close();
        }

        emf.close();

    }



    private static Member saveMember(EntityManager em) {
        Member member = new Member();
        member.setUsername("member1");

        em.persist(member);
        return member;
    }
}

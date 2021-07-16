package jpql;

import org.hibernate.mapping.Collection;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);
//            em.flush();
//            em.clear();

            //FLUSH 자동 호출 //db에 age 0으로 업데이트 되고 영속성 컨텍스트에는 그대로 남아 있음
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            em.clear(); //이렇게 해줘야 영속성 컨텍스트에 아무것도 안 남아서 디비서 새로 가져옴

            Member findMember = em.find(Member.class, member1.getId());

            System.out.println("findMember = " + findMember.getAge());
//
//            System.out.println("resultCount = " + resultCount);
//
//            System.out.println("member1.getAge() = " +member1.getAge());
//            System.out.println("member2.getAge() = " +member2.getAge());
//            System.out.println("member3.getAge() = " +member3.getAge());


            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally{
            em.close();
        }

        emf.close();

    }




}

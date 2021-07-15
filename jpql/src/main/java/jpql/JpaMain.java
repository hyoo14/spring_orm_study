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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);
            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금'" +
//                            "     when m.age >= 60 then '경로요금'" +
//                            "     else '일반요금' " +
//                            "end " +
//                    "from Member m";
//            String query = "select nullif(m.username, '관리자') as username "+
//                         "from Member m";
            //language=JQL
//            String query = "select 'a' || 'b' From Member m";

//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }
            ////language=JPQ

//            String query = "select m.team.name From Member m";
            String query = "select m From Team t join t.members m"; //묵시적 조인 쓰지 마시오

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();
            System.out.println("result = " + result);

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

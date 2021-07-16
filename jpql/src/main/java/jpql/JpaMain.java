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
            em.flush();
            em.clear();

            //String query = "select m From Member m"; //->패치조인 사용 아래와 같음
            //String query = "select m From Member m join fetch m.team";//매니투원 패치 주인이었음 여기까지

            //패치 조인 사용 전은 아래와 같았으나 사용 후에 한방에 찍힘. 굿
            //회원1, 팀A(SQL)
            //회원2, 팀A(1차 캐시)
            //회원3, 팀B(SQL)

            //회원 100명 -> N + 1

            String query = "select distinct t From Team t join fetch t.members";//컬렉션 페치 조인
            //String query = "select t From Team t join t.members";//그냥 조인과 비교

            List<Team> result = em.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("reslut = " + result.size());
            for (Team team : result) {
                System.out.println("team = " + team.getName() + "|" + team.getMembers().size());
                for( Member member : team.getMembers()){
                    System.out.println(" -> member = "+ member);
                }
            }

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

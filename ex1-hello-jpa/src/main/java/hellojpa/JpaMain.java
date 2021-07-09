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
            Member member = saveMember(em);

            Team team = new Team();
            team.setName("teamA");

            //아래가 좀 애매해짐.
            team.getMembers().add(member); //팀테이블에 인서트될 수도 없구..
            //그래서 업데이트 쿼리가 하나 더 나감. 성능상 차이가 조금 있음(크진 않지만 무튼 손해는 손해)
            //심각한 점: 왜 쿼리가 이렇게 되고 동작이 이런지 이해하기 힘들어짐.. 그래서 단방향 다대일로 씀

            em.persist(team);

            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
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

package hellojpa;

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

            Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("KIM");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

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

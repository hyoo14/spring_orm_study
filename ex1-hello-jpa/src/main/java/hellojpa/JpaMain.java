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
            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("=======================");
            em.persist(member1); //1, 51
            em.persist(member2); //MEM
            em.persist(member3); //MEM

            System.out.println("member = "+member1.getId());
            System.out.println("member = "+member2.getId());
            System.out.println("member = "+member3.getId());
            System.out.println("=======================");

            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();

    }
}

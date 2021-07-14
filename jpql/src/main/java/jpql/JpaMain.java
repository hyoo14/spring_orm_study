package jpql;

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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);//타입정보 기본적으로 엔티티
//            TypedQuery<String> query2 = em.createQuery("select m.username, m.age from Member m", String.class);//타입정보 기본적으로 엔티티
//            Query query3 = em.createQuery("select m.username, m.age from Member m");//반환타입 명확하지 않을 때

//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
//
//            //List<Member> resultList = query.getResultList() //여러개
////            Member result = query.getSingleResult(); //결과 하나
////            //Spring Data JPA -> exception 처리해줌
////            System.out.println("result = "+ result);
//
//
//            query.setParameter("username","member1");
//            Member singleResult = query.getSingleResult();

            //위를 보통 (엮어서) 체이닝해줌
            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("singleResult = " +result.getUsername());
//

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

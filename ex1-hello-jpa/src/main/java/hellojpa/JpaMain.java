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
//            Member member = new Member(); //등록
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member); //멤버 저장.

//            Member findMember = em.find(Member.class, 1L);//수정(우선 조회)
//            System.out.println("findMember.id = " +findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
//            findMember.setName("HelloJPA");//(수정) jpa차원서 가져오면 jpa가 체크해서 바뀐 거 업데이트쿼리 날려서 업뎃해줌

            //em.persist(findMember);//없어도 업뎃됨

            //전체회원 조회하고 싶다?
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)  //5번부터
                    .setMaxResults(8)  //8개 가져
                    .getResultList(); //멤버 객체 대상의 쿼리! (jpa에서는 테이블 대상의 쿼리 노우)
            for (Member member : result){
                System.out.println("member.neme = " + member.getName());
            }

            tx.commit();
        } catch( Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();

    }
}

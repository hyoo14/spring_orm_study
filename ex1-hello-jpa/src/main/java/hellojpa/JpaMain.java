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

            Member member1 = new Member();
            member1.setUsername("hello1");
            em.persist(member1);
//            Member member2 = new Member();
//            member2.setUsername("hello1");

//            em.persist(member2);

            em.flush();
            em.clear();

//            Member m1 = em.getReference(Member.class, member1.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());
//            //System.out.println("m1 == m2 " + (m1.getClass() == m2.getClass())); //프록시로 넘어올지 뭐로 넘어올지 모르니 클래스로 타입비교 ㄴㄴ. 인스턴스로 하시오
//            System.out.println("m1 == m2 " + (m1 instanceof Member));
//            System.out.println("m1 == m2 " + (m2 instanceof Member));
//
//            System.out.println("m1 = " + m1.getClass());
//            m1.getUsername();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); //Proxy
            //refMember.getUsername(); //강제 초기화
            //System.out.println("isLoaded = "+ emf.getPersistenceUnitUtil().isLoaded(refMember)); //프록시초기화 안 하면 포스

            Hibernate.initialize(refMember); //강제초기화



//            em.detach(refMember);
//            em.clear();
//            refMember.getUsername();
//            System.out.println("refMembetr = "+refMember.getUsername());
//            refMember.getUsername();

//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("find Member = " + findMember.getClass()); // Member
//            //프록시더 ㄴ아니던 문제 없게 개발하는 것이 중요! jpa가 어떻게든 맞춰줌. 실무에서는 이렇게 복잡할 일 없두아!


//            System.out.println("refMember == findMember" + (refMember == findMember) );

//            //
////            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference(Member.class, member.getId());
//            System.out.println("findMember = "+findMember.getClass()); //하이버네이트가 가짜로 만든 가짜클랫스(프록시클래스?)
//            System.out.println("findMember.id = " +findMember.getId());
//            System.out.println("findMember.username = " +findMember.getUsername());//값 없으니까 쿼리로 가져옴
//            System.out.println("findMember.username = " +findMember.getUsername());//이미 가져온 상태니깐 걍 가져옴

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

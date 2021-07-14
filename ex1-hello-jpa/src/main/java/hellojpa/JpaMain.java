package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            String qlString = "select m From Member m where m.username like '%kim%' "; //동적쿼리가 넘 어렵다
//            //이거 이프문 넣고 문자 붙이고 띠고 하면 에러나기 딱 좋다! 실무에서.
//
//            List<Member> result = em.createQuery(
//                    qlString,
//                    Member.class
//            ).getResultList();
//            for (Member member : result) {
//                System.out.println("member = " + member);
//            }
//
//            //동적쿼리 잘 사용하기 위해 크리어티리아 씀
//            //Criteria 사용 준비
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//            //루트 클래스 (조회를 시작할 클래스)
//            Root<Member> m = query.from(Member.class);
//            //쿼리 생성 CriteriaQuery<Member> cq =
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
//
//            String username = "dsafsa";
//            if( username != null){
//                cq = cq.where(cb.equal(m.get("username"), "kim"));
//            }
//
//            List<Member> resultList = em.createQuery(cq)
//                    .getResultList();
//            //근데 알아보기도 어렵고 복잡하고 유지보수도 어려워서 실무에서는 안 씀. 그냥 있다는 것만 알아보세요. 이건 약간 망한듯 실제로 안 쓰임

            //근데 쿼리dsl 써라. 이게 실무적, 실전적 쵝오

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            //flush -> commit, query 날릴 때 동작함

            //진짜 쿼리 날리기도 가눙
            List<Member> resultList = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER", Member.class)
                    .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = "+member1);
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

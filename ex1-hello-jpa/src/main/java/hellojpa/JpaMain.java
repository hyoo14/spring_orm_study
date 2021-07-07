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

//            //em.persist(findMember);//없어도 업뎃됨
//
//            //전체회원 조회하고 싶다?
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5)  //5번부터
//                    .setMaxResults(8)  //8개 가져
//                    .getResultList(); //멤버 객체 대상의 쿼리! (jpa에서는 테이블 대상의 쿼리 노우)
//            for (Member member : result){
//                System.out.println("member.neme = " + member.getName());
//            }

            //영속성컨텍스트에 대한

//            //비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            //영속
//            System.out.println("=== BEFORE ===");
//            em.persist(member); //엔티티메니저를 통해 영속성 컨텍스트로 관리가 된다는 뜻. 여기서는 db에 저장되지 않음.
//            //em.detach(member); //영속 컨텍스트에서 지워줌.
//            System.out.println("=== AFTER ==="); //BEFORE AFTER 사이에 아무것도 없다.

//            Member findMember1 = em.find(Member.class, 101L); //db에서 가져와서 영속성컨텍스트에 올림
//            Member findMember2 = em.find(Member.class, 101L); //영속성컨텍스트 1차 캐시부터 찾아보고 반환
//
////            System.out.println("findMember.id = " + findMember.getId());
////            System.out.println("findMember.name  = " + findMember.getName());
//
//            System.out.println("result = " + (findMember1 == findMember2));

//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");

//            em.persist(member1);
//            em.persist(member2);

//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZ");
            //em.persist(member); //이렇게 할 필요가 없다 //호출하지 않는 것이 정답!
            //찾아온 다음에 데이터 변경하는 거엇
            //커밋하면서 변경됨
            //삭제는 em.remove(member)

            //
//            if (member.getName().equals("ZZZZZ")){
//                em.update(member);
//            } //이런거 안 해줘도 업데이트 쿼리 쌓아줌. 자동으로 변경반영하는 것! 그러므로 em.persist 하지 말아야!
//            Member member = new Member(200L, "member200");
//            em.persist(member);
//            em.flush();
//
            //준영속 테스트
            //밑 영속
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");
            //
            //em.detach(member); //영속에서 떼어냄. jpa에서 관리x commit해도 아무일도 안 일어나겠죠

            em.clear(); //em의 영속성 컨텍스트 다 지워버리는 것


            System.out.println("===================================");

            tx.commit();//커밋하는 순간에 영속성 컨텍스트에 있는 것이 쿼리 날라감
        } catch( Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();

    }
}

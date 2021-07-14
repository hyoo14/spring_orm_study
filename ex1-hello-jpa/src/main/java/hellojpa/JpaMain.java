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

            Address address = new Address("city", "street", "10000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            //바꾸고 싶으면 새로 만들어야함. immutable되면
            Address newAddress = new Address("NewCity", address.getStreet(), address.getCity());
            member.setHomeAddress(newAddress); //이런식으로 쫙 바꿔줌

//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(copyAddress); //누가 실수로 그냥 adress 넣어도 막을 방법이 없다!
//            em.persist(member2);

            //
            //member.getHomeAddress().setCity("newCity"); //둘 다 바뀌어버림 //이런 side effect는 잡기 진짜 어렵다
            //같이 공유해서 뭘 하고 싶으면 어드레스를 엔티티로 만들어야!


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

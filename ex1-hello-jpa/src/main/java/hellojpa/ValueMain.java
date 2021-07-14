package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        int a = 10;
        int b = 10;

        System.out.println("a == b : "+ (a == b));

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2 : " +(address1 == address2 ));

        System.out.println("address1 equals address2 : " +(address1.equals(address2) )); //기본이 == 비교여서 false
        //어디레스에서 override 해서 셋다 비교해주니까 투루가 나옴
        //이퀄스 구현하면 해쉬코드도 같이 구현해줘야함. 자바콜렉션에서 효율적으로 사용하게끔 //기초자바내용임
    }
}

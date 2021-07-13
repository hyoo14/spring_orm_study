package hellojpa;

public class ValueMain {

    public static void main(String[] args) {
        Integer a = new Integer(10);
        Integer b = a;
//
//        int a = 10;
//        int b = a;
//
//        a = 20;

//        a.setValue(20); //둘 다 바꿈 하지만 이런 건 없으니깐.

        System.out.println("a = "+a);
        System.out.println("b = " +b);
    }
}

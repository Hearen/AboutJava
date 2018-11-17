package site.lhearen.ajava.base.lambda;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Thread thread = new Thread(() ->  System.out.println("Hello world in thread"));
        thread.start();

        TestException.testUnchecked();
        TestException.testChecked();

        TestBasic.test();
        TestBasic.testFunctionChain();
        TestBasic.testSorter();

        TestBasic basic = new TestBasic();
        basic.testLocal();
    }
}

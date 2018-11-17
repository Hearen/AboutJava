package site.lhearen.ajava.base.lambda.se8_1;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_11 {

    @Test
    public void testConflict() {
        MyClass myClass = new MyClass();
        myClass.myAbstract();
        myClass.myDefault();
        I.myStatic();
        J.myStatic();
    }

    class MyClass extends S implements I, J {
//        @Override
//        public void myAbstract() { // S already implements myAbstract;
//            out.println("This is the abstract");
//        }
    }

    class S implements I {
        @Override
        public void myAbstract() {
            out.println("Abstract in S");
        }
    }

    interface I {
        void myAbstract(); // acceptable;
        default void myDefault() { // conflict;
            out.println("Default in I");
        }
        static void myStatic() { // will not be implemented by implementor -> no conflict consequently;
            out.println("Static in I");
        }
    }

    interface J {
        void myAbstract();
//        default void myDefault() {
//            out.println("Default in J");
//        }
        static void myStatic() {
            out.println("Static in J");
        }
    }
}

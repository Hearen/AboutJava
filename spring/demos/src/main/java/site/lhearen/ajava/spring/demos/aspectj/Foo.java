package site.lhearen.ajava.spring.demos.aspectj;

public class Foo {
    public void foo() {
        System.out.println("foo");
    }

    @MyAnnotation(type = MyAnnotation.MyType.CREATE)
    public void test() {
    	System.out.println("testing now...");
	}
}

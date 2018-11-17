package site.lhearen.ajava.spring.demos.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class FooAspect {
	@Pointcut("execution(* site.lhearen.ajava.spring.demos.aspectj.Foo.foo(..))")
	public void methodFooFromTypeFoo() {}

	@Before("methodFooFromTypeFoo()")
	public void before(JoinPoint joinPoint) {
		System.out.println("before " + joinPoint.toLongString());
	}

	@Around(value = "@annotation(site.lhearen.ajava.spring.demos.aspectj.MyAnnotation)")
	public void testAspect(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("within advice body");
		jp.proceed();
		System.out.println("leaving advice body");
	}

}

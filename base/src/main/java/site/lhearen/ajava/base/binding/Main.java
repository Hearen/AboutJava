package site.lhearen.ajava.base.binding;

import static java.lang.System.out;

public class Main extends StaticHuman {

    public static void main(String[] args) {
        // https://beginnersbook.com/2013/04/java-static-dynamic-binding/
        // final, static and private cannot be overridden
        // so they must be static binding - no polymorphism;
        FinalHuman finalHuman = new FinalBoy();
        FinalHuman finalHuman1 = new FinalHuman();
        finalHuman.walk();
        finalHuman1.walk();

        StaticHuman staticHuman = new StaticBoy();
        StaticHuman staticHuman1 = new StaticHuman();
        staticHuman.walk();
        staticHuman1.walk();

        DynamicHuman dynamicHuman = new DynamicBoy();
        DynamicHuman dynamicHuman1 = new DynamicHuman();
        dynamicHuman.walk();
        dynamicHuman1.walk();
    }
}

class FinalHuman {
    public static void walk()
    {
        out.println("Human walks using final");
    }
}
class FinalBoy extends FinalHuman {
    public static void walk() {
        out.println("Boy walks using final");
    }
}

class StaticHuman {
    public static void walk()
    {
        out.println("Human walks using static");
    }
}
class StaticBoy extends StaticHuman {
    public static void walk() {
        out.println("Boy walks using static");
    }
}

class DynamicHuman {
    public void walk()
    {
        out.println("Human walks");
    }
}
class DynamicBoy extends DynamicHuman {
    public void walk() {
        out.println("Boy walks");
    }
}

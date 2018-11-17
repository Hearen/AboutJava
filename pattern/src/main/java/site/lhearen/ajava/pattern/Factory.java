package site.lhearen.ajava.pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Factory {
    public static void runTest() {
        Product p1 = ProductFactory.createProduct("loan");
        p1.sayHi();
        Supplier<Product> loanSupplier = Loan::new;
        Product p2 = loanSupplier.get();
        p2.sayHi();
        Product p3 = ProductFactory.createProductLambda("loan");
        p3.sayHi();
    }

    static private class ProductFactory {
        public static Product createProduct(String name){
            switch(name){
                case "loan": return new Loan();
                case "stock": return new Stock();
                case "bond": return new Bond();
                default: throw new RuntimeException("No such product " + name);
            }
        }

        public static Product createProductLambda(String name){
            Supplier<Product> p = map.get(name);
            if(p != null) return p.get();
            throw new RuntimeException("No such product " + name);
        }
    }

    private interface Product {
        default void sayHi() {
            System.out.println("This is Product.");
        }
    }
    static private class Loan implements Product {
        @Override
        public void sayHi() {
            System.out.println("This is Loan.");
        }
    }
    static private class Stock implements Product {
        @Override
        public void sayHi() {
            System.out.println("This is Stock.");
        }
    }
    static private class Bond implements Product {
        @Override
        public void sayHi() {
            System.out.println("This is Bond.");
        }
    }

    final static private Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
}

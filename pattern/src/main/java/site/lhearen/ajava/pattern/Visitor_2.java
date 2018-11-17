package site.lhearen.ajava.pattern;

import static java.lang.System.out;
public class Visitor_2 {
    public static void main(String...args) {
        Hearen hearen = new Hearen();
        FoodImpl food = new FoodImpl();
        hearen.showHobby(food);
        Katherine katherine = new Katherine();
        katherine.showHobby(food);
    }
}

interface Hobby {
    void insert(Hearen hearen);
    void insert(Katherine katherine);
}

abstract class Person {
    String name;
    protected Person(String n) {
        this.name = n;
    }
    abstract void showHobby(Hobby hobby);
}

class Hearen extends  Person {
    public Hearen() {
        super("Hearen");
    }
    @Override
    void showHobby(Hobby hobby) {
        hobby.insert(this);
    }
}

class Katherine extends Person {
    public Katherine() {
        super("Katherine");
    }

    @Override
    void showHobby(Hobby hobby) {
        hobby.insert(this);
    }
}

class FoodImpl implements Hobby {
    public void insert(Hearen hearen) {
        out.println(hearen.name + " start to eat bread");
    }
    public void insert(Katherine katherine) {
        out.println(katherine.name + " start to eat mango");
    }
}

package site.lhearen.ajava.pattern;

import static java.lang.System.out;
public class Visitor_1 {
    public static void main(String... args) {
        Animal[] animals = { new Cat(), new Dog() };
        Sound sound = new Sound();
        Eat eat = new Eat();
        for (Animal animal : animals) {
            animal.letsDo(sound);
            animal.letsDo(eat);
        }
    }
}

interface Operation {
    void make(Dog dog);
    void make(Cat cat);
}

class Sound implements Operation {
    public void make(Dog dog) {
        out.println("Wang-Wang ....");
    }

    public void make(Cat cat) {
        out.println("Meow-Meow ....");
    }
}

class Eat implements Operation {
    public void make(Dog dog) {
        out.println("Eat meat and sausage ...");
    }
    public void make(Cat cat) {
        out.println("Eat fish and fish ...");
    }
}

abstract class Animal {
    abstract public void letsDo(Operation opt);
}

class Dog extends Animal {
    @Override
    public void letsDo(Operation opt) {
        opt.make(this);
    }
}

class Cat extends Animal {
    @Override
    public void letsDo(Operation opt) {
        opt.make(this);
    }
}


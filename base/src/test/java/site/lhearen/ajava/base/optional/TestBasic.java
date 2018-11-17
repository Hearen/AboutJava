package site.lhearen.ajava.base.optional;

import java.util.Optional;
import java.util.Properties;

import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class TestBasic {


    public static void main(String[] args) {
        Car bmw = new Car();
        Optional<Person> hearen = Optional.of(new Person(bmw));
        out.println(getCarInsuranceName(hearen));

        Insurance insurance = new Insurance("AWESOME");
        Car porsche = new Car(insurance);
        Optional<Person> katherine = Optional.of(new Person(porsche));
        out.println(getCarInsuranceName(katherine));

        Optional<Insurance> cheapestInsurance = findCheapestInsurance(hearen, Optional.of(bmw));
        out.println(cheapestInsurance.map(i -> i.getName()).orElse("Unknown"));

        Optional<Insurance> cheapestInsuranceForK = findCheapestInsurance(katherine, Optional.of(porsche));
        out.println(cheapestInsuranceForK.map(i -> i.getName()).orElse("Unknown"));
    }

    private static String getCarInsuranceName(Optional<Person> person) {
        return person
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance) // flatMap will just return the Optional<value> without extra encapsulation;
                .map(Insurance::getName) // map will encapsulate the value in Optional;
                .orElse("Unknown"); // orElse will retrieve the value if failed return the default "Unknown";
    }

    @Test
    public void testMapAndFlatMap() {
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("TEST"), s.map(String::toUpperCase));

        assertEquals(Optional.of(Optional.of("STRING")),
                Optional.of("string").map(ss -> Optional.of("STRING")));

        assertEquals(Optional.of("STRING"),
                Optional.of("string").flatMap(ss -> Optional.of("STRING")));

        assertEquals(Optional.of("STRING"),
                Optional.of("string").flatMap(ss -> Optional.of(ss.toUpperCase())));
    }

    private static Optional<Insurance> findCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        return car.flatMap(c -> c.getInsurance());
    }

    @Test
    public void testOptionalFilter() {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        assertEquals(5, readDurationWithSimpleOptional(props, "a"));
        assertEquals(0, readDurationWithSimpleOptional(props, "b"));
        assertEquals(0, readDurationWithSimpleOptional(props, "c"));
        assertEquals(0, readDurationWithSimpleOptional(props, "d"));

        assertEquals(5, readDurationWithFullOptional(props, "a"));
        assertEquals(0, readDurationWithFullOptional(props, "b"));
        assertEquals(0, readDurationWithFullOptional(props, "c"));
        assertEquals(0, readDurationWithFullOptional(props, "d"));

    }


    private static int readDurationWithSimpleOptional(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .map(TestBasic::stringToInt)
                .filter(i -> i > 0).orElse(0);
    }

    private static Integer stringToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int readDurationWithFullOptional(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(TestBasic::stringToIntOptional)
                .filter(i -> i > 0).orElse(0);
    }

    private static Optional<Integer> stringToIntOptional(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

class Person {

    private Optional<Car> car = Optional.empty();

    public Person(Car car) {
        this.car = Optional.of(car);
    }

    public Optional<Car> getCar() {
        return car;
    }
}

class Car {

    private Optional<Insurance> insurance = Optional.empty();

    public Car() {
    }

    public Car(Insurance insurance) {
        this.insurance = Optional.of(insurance);
    }

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}

class Insurance {

    private String name;

    public Insurance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

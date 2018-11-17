package site.lhearen.ajava.base.lambda.se8_3;

import java.lang.reflect.Field;
import java.util.*;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_9 {
    @Test
    public void testReflection() {
        List<Person> personList = new ArrayList<>(Arrays.asList(new Person("Hearen", "Lo"),
                new Person("Katherine", "Li"), new Person("Hearen", "Li")));
        Collections.reverse(personList);
        personList.sort(getComparator("firstname", "lastname"));
        personList.forEach(out::println);
    }

    private Comparator<Person> getComparator(String... fieldNames) {
        return (hearen, katherine) -> {
            try {
            for (String fieldName : fieldNames) {
                Field field = Person.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                // Or
                // set the fields public access in class Person;
                // Field field = Person.class.getField(fieldName);
                String s1 = field.get(hearen).toString();
                String s2 = field.get(katherine).toString();
                if (!s1.equals(s2)){
                    return s1.compareTo(s2);
                }
            } } catch (NoSuchFieldException | IllegalAccessException ignored) {
                ignored.printStackTrace();
            }
            return 0;
        };
    }

    class Person {
        String firstname;
        String lastname;
        public Person(String theFirst, String theLast) {
            this.firstname = theFirst;
            this.lastname = theLast;
        }

        @Override
        public String toString() {
            return String.format("{ \"firstname\": \"%s\", \"lastname\": \"%s\" }", firstname, lastname);
        }
    }
}

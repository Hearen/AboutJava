package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleFlatMap {
    public static void main(String... args) {
        List<EntityWithNames> listOne = new ArrayList<>();
        listOne.add(new EntityWithNames());
        List<EntityWithAge> listTwo = new ArrayList<>();
        listTwo.add(new EntityWithAge("test", 3));
        List<EntityWithNameAndAge> listThree = listOne.stream().map(withNames -> {
            EntityWithAge entityWithAge = listTwo.stream()
                    .filter(withAge -> withNames.names.contains(withAge.name))
                    .findAny()
                    .orElse(new EntityWithAge());
            return new EntityWithNameAndAge(withNames.id, entityWithAge.name, entityWithAge.age);
        }).collect(Collectors.toList());
        System.out.println(listThree);
    }

    static class EntityWithNames {
        Long id;
        List<String> names;

        public EntityWithNames() {
            id = 3L;
            names = new ArrayList<>(Arrays.asList("test", "test1"));
        }
    }

    static class EntityWithAge {
        String name;
        int age;

        public EntityWithAge() {
            name = "default";
            age = -1;
        }

        public EntityWithAge(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    static class EntityWithNameAndAge {
        Long id;
        String name;
        int age;

        public EntityWithNameAndAge(Long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return String.format("id: %d, name: %s, age: %d", id, name, age);
        }
    }
}

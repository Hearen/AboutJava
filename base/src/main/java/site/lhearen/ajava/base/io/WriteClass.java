package site.lhearen.ajava.base.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteClass {
    public static void main(String... args) {
        Person jack = new Person("Jack", 1, 2, 1, 250.99);
        List<String> list = new ArrayList<>();
        list.add(jack.toString());
        try {
            Path path = Paths.get(Paths.get("").toAbsolutePath().toString().concat("/persons.txt"));
            System.out.println(path.toAbsolutePath().toString());
            Files.write(path, list);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        System.out.println(jack);
    }

    static class Person {
        String name;
        int buildingNum;
        int floorNum;
        int roomNum;
        double rentCost;

        public Person(String name, int buildingNum, int floorNum, int roomNum, double rentCost) {
            this.name = name;
            this.buildingNum = buildingNum;
            this.floorNum = floorNum;
            this.roomNum = roomNum;
            this.rentCost = rentCost;
        }

        @Override
        public String toString() {
            return String.format("{name: %s, building No.: %d, floor No.: %d, room No.: %d, rent cost: %.2f}",
                    name, buildingNum, floorNum, roomNum, rentCost);
        }
    }
}

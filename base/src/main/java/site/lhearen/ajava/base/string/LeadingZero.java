package site.lhearen.ajava.base.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeadingZero {
    List<Student> createdStudents;
    public static void main(String... args) {
        System.out.println(String.format("%04d", 3));
    }

    public void generatePin() {
        String pin;
        Random r = new Random();
        List<String> pinUsed = new ArrayList<>();
        for (int i = 0; i < createdStudents.size(); i++) {
            int rand = r.nextInt((3998 - 1) + 1) + 1;
            pin = String.format("%04d", rand);
            if (pinUsed.contains(pin)) {
                i--; // ignore this pin and
                // try another random pin for the same student;
            } else {
                pinUsed.add(pin); // this pin is not used yet;
                createdStudents.get(i).setPin(pin);
            }
        }
    }
}

class Student {
    String pin;

    public void setPin(String pin) {
        this.pin = pin;
    }
}

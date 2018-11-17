package site.lhearen.ajava.base.enumeration;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Main {
    public enum DaysOfWeekEnum {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;
    }

    public static void main(String[] args) {
        DaysOfWeekEnum friday = Enum.valueOf(DaysOfWeekEnum.class, "FRIDAY");
        out.println(friday);
        out.println("Using for loop to iterate Enum");
        for (DaysOfWeekEnum day : DaysOfWeekEnum.values()) {
            out.println(day);
        }
    }

    private static void testEnumStream() {
        System.out.println("Using EnumSet.allOf(DaysOfWeekEnum.class) to iterate Enum");
        EnumSet.allOf(DaysOfWeekEnum.class)
                .forEach(System.out::println);

        System.out.println("Using Arrays.asList(DaysOfWeekEnum.values()) to iterate Enum");
        Arrays.asList(DaysOfWeekEnum.values())
                .forEach(System.out::println);

        System.out.println("Using Stream.of(DaysOfWeekEnum.values()) to iterate Enum");
        Stream.of(DaysOfWeekEnum.values()).forEach(System.out::println);

        System.out.println("Using Arrays.stream(DaysOfWeekEnum.values()) to iterate Enum");
        Arrays.stream(DaysOfWeekEnum.values()).forEach(System.out::println);
        System.out.println("\n********** The End ************");
    }
}

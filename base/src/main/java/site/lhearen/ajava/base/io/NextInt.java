package site.lhearen.ajava.base.io;

import site.lhearen.ajava.mytools.util.Timer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class NextInt {
    public static void main(String... args) {
        prepareInputFile(1000, 500); // create 1_000 arrays which each contains 500 numbers;
        Timer.timer(() -> readFromFile(), 20, "NextInt"); // read from the file 20 times using Scanner.nextInt();
        Timer.timer(() -> readTest(), 20, "Split"); // read from the file 20 times using split() and Integer.parseInt();
    }

    private static void readTest() {
        Path inputPath = Paths.get(Paths.get("").toAbsolutePath().toString().concat("/src/main/java/io/input.txt"));
        try (Scanner scanner = new Scanner(new File(inputPath.toString()))) {
            int n = Integer.valueOf(scanner.nextLine());
            int[] iVau = new int[n];
            String[] temp = scanner.nextLine().split(" ");
            for (int i = 0; i < n; i++) {
                iVau[i] = Integer.parseInt(temp[i]);
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private static void readFromFile() {
        Path inputPath = Paths.get(Paths.get("").toAbsolutePath().toString().concat("/src/main/java/io/input.txt"));
        try (Scanner scanner = new Scanner(new File(inputPath.toString()))) {
            while (scanner.hasNextInt()) {
                int arrSize = scanner.nextInt();
                int[] arr = new int[arrSize];
                for (int i = 0; i < arrSize; ++i) {
                    arr[i] = scanner.nextInt();
                }
//                System.out.println(Arrays.toString(arr));
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private static void prepareInputFile(int arrCount, int arrSize) {
        Path outputPath = Paths.get(Paths.get("").toAbsolutePath().toString().concat("/src/main/java/io/input.txt"));
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < arrCount; ++i) {
            int[] arr = new int[arrSize];
            for (int j = 0; j < arrSize; ++j) {
                arr[j] = new Random().nextInt();
            }
            lines.add(String.valueOf(arrSize));
            lines.add(Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        }
        try {
            Files.write(outputPath, lines);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }
}

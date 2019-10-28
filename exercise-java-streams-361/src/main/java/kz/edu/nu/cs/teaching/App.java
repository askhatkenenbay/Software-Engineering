package kz.edu.nu.cs.teaching;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {
    static final String h = "~~~~~~~~~~~~\n";

    public static void main(String[] args) {
        System.out.println("\nTask 1 " + h);
        wordcount(getTestLinesStream());

        System.out.println("\nTask 2 " + h);
        filterByWordLength(getTestLinesStream());

        System.out.println("\nTask 3 " + h);
        groupWordsByFirstCharacter(getTestLinesStream());

    }

    /*
     * Task 1, count words in entire file
     */
    public static void wordcount(Stream<String> stream) {
        System.out.println(stream.flatMap(str -> Stream.of(str.split("\\s+"))).count());
        stream.close();
    }

    /*
     * Task 2, filter lines by lengths of longest words
     */
    public static void filterByWordLength(Stream<String> stream) {
        stream.filter(line -> Stream.of(line.split("\\s+")).filter(str -> str.length() > 7).count() > 0)
                .forEach(System.out::println);
        stream.close();
    }

    /*
     * Task 3, group words by first character
     */
    public static void groupWordsByFirstCharacter(Stream<String> stream) {
        Map<Character, Long> hashMap = stream.map(q -> q.toUpperCase()).flatMap(str -> Stream.of(str.split("\\s+")))
                .collect(Collectors.groupingBy(str -> str.charAt(0), Collectors.counting()));
        System.out.println(hashMap);
        stream.close();
    }

    /*
     * Return Stream of lines from file
     */
    public static Stream<String> getTestLinesStream() {
        File file = new File("lambtest.txt");

        try {
            return Files.lines(file.toPath());
        } catch (Exception e) {
            System.out.println("Error reading from file");
            return null;
        }
    }


}

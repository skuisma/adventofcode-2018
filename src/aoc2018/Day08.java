package aoc2018;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day08 {
    private List<Integer> codes = new ArrayList<>();
    private int result = 0;

    public static void main(String[] args) {
        Day08 day = new Day08();
        day.solve2();
    }

    private void solve2() {
        String dataPath = "src\\resources\\input-day8-1.txt";
        FileReader fr = new FileReader(dataPath);
        String[] data = fr.readFirstRow().split(" ");
        codes = Stream.of(data).map(Integer::parseInt).collect(Collectors.toList());

        while (codes.size() > 1) {
            int metaCount = codes.get(1);
            if (codes.get(0) > 0) {
                for (int i = 0; i < metaCount; i++) {
                    int pos = codes.size() - 1;
                    getMetadata(pos);
                }
                codes.remove(0);
                codes.remove(0);
            } else if (codes.get(0) == 0) {
                codes.remove(0);
                codes.remove(0);
                for (int i = 0; i < metaCount; i++) {
                    getMetadata(0);
                }
            }
        }
        System.out.println("Total: " + result);
        codes.forEach(System.out::println);
    }


    private void getMetadata(int pos) {
        //System.out.println("Added " + codes.get(pos));
        result += codes.get(pos);
        codes.remove(pos);
    }

}


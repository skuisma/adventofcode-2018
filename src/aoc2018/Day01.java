package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Day01 {
    private int freq;
    private String dataPath = "src\\resources\\input-day1-1.txt";


    public static void main(String[] args) {
        Day01 day01 = new Day01();
        day01.solve1();
        day01.solve2();
    }

    private void solve1() {
        freq = 0;
        List<String> allLines = readFile(dataPath);
        for (String line : allLines) {
            freq += line.length() > 0 ? Integer.parseInt(line) : 0;
        }
        System.out.println("Calibration done: " + freq);
    }

    private void solve2() {
        List<String> allLines = readFile(dataPath);
        boolean loop = true;
        freq = 0;
        HashMap<Integer,Integer> freqs = new HashMap<>();
        freqs.put(0,0);

        while (loop) {
            for (String line : allLines) {
                freq += line.length() > 0 ? Integer.parseInt(line) : 0;
                if (freqs.containsKey(freq) == false) {
                    freqs.put(freq, 0);
                } else {
                    System.out.println("First duplicate found: " + freq);
                    loop = false;
                    break;
                }
            }
        }
    }

    private List<String> readFile(String path){
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(Paths.get(path));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return allLines;
    }
}

package aoc2018;

import java.util.HashMap;
import java.util.List;

public class Day03 {
    private String dataPath = "src\\resources\\input-day3-1.txt";
    private String[][] canvas = new String[1000][1000];

    public static void main(String[] args) {
        Day03 day = new Day03();
        day.solve1();
        //day.solve2();
    }

    private void solve1() {
        int collision = 0;
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();
        HashMap<String,Integer> claims = new HashMap<>();
        for (String line : data) {
            // #1 @ 1,3: 4x4
            String pattern = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)";
            // id, x, y, w, h
            String splittedLine[] = line.replaceAll(pattern,"$1;$2;$3;$4;$5").split(";");
            int x = Integer.parseInt(splittedLine[1]);
            int y = Integer.parseInt(splittedLine[2]);
            int w = Integer.parseInt(splittedLine[3]);
            int h = Integer.parseInt(splittedLine[4]);
            claims.put(splittedLine[0],(w*h));

            for (int i = x; i < x+w; i++) {
                for (int j = y; j < y+h; j++) {
                    if (canvas[i][j] != null) {
                        canvas[i][j] = "X";
                    } else {
                        canvas[i][j] = splittedLine[0];
                    }
                }
            }
        }
        /**
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                //System.out.println(canvas[i][j]);
                if (canvas[i][j] != null && canvas[i][j].equals("X")){
                    collision += 1;
                }
            }
        }
        */
        // Task 1
        collision = countById(canvas, "X");
        System.out.println(collision);


        // Task2
        claims.forEach((k,v)->{
            if(countById(canvas, k) == v) {
                System.out.println(k);
            }
        });
    }

    private int countById(String[][] canvas, String key) {
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (canvas[i][j] != null && canvas[i][j].equals(key)){
                    count += 1;
                }
            }
        }
        return count;
    }
}

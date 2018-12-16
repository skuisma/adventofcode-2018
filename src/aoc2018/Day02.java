package aoc2018;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Day02 {
    private String dataPath = "src\\resources\\input-day2-1.txt";

    public static void main(String[] args) {
        Day02 day02 = new Day02();
        day02.solve1();
        day02.solve2();
    }

    private void solve1() {
        AtomicInteger doubleValue = new AtomicInteger();
        AtomicInteger tripleValue = new AtomicInteger();
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();
        for (String line : data){
            AtomicBoolean doubleAdded = new AtomicBoolean(false);
            AtomicBoolean tripleAdded = new AtomicBoolean(false);
            HashMap<Character,Integer> chars = new HashMap<>();
            for (int i = 0; i < line.length(); i++) {
                int curval = chars.getOrDefault(line.charAt(i),0);
                chars.put(line.charAt(i),curval + 1);
            }

            chars.forEach((k,v)->{
                if(v==2 && doubleAdded.get() == false){
                    doubleValue.addAndGet(1);
                    doubleAdded.set(true);
                }
                if(v==3 && tripleAdded.get() == false){
                    tripleValue.addAndGet(1);
                    tripleAdded.set(true);
                }
            });
        }
        System.out.println("double: " + doubleValue + " triple: " + tripleValue + " --> " + doubleValue.get() * tripleValue.get());
    }

    private void solve2() {
        String boxId;
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();
        for (String line : data){
            for (String lines : data) {
                int[] delta = compareBoxIds(line, lines);
                if (delta[0] == 1) {
                    boxId = line.substring(0,delta[1]) + line.substring(delta[1]+1);
                    System.out.println(boxId);
                    break;
                }
            }
        }
    }


    private int[] compareBoxIds(String id1, String id2) {
        int delta[] = {0,0};
        for (int i = 0; i < id1.length(); i++) {
            if (id1.charAt(i) != id2.charAt(i)) {
                delta[0] += 1;
                delta[1] = i;
            }
        }
        //System.out.println("delta: " +  delta[0] + " pos: " + delta[1]);
        return delta;
    }
}

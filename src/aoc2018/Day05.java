package aoc2018;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05 {
    private String dataPath = "src\\resources\\input-day5-1.txt";

    public static void main(String[] args) {
        Day05 day = new Day05();
        day.solve1();
        //day.solve2();
    }

    private void solve1() {
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();
        String line = data.get(0);

        // Task 1
        //System.out.println(react(line));

        // Task 2
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        HashMap<Character,Integer> reaction = new HashMap<>();
        for (int i = 0; i < alphabet.length; i++) {
            String pattern = "["+String.valueOf(alphabet[i])+String.valueOf(alphabet[i]).toUpperCase()+"]+";
            String line2 = line.replaceAll(pattern, "");
            //System.out.println(line2);
            int res = react(String.valueOf(line2));
            reaction.put(alphabet[i], res);
            //System.out.println(alphabet[i] + " : " + res);
        }
        System.out.println(reaction
                .entrySet()
                .stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .get());
    }

    private int react(String polymer) {
        char prev = '0';
        int reactions = 1;
        while (reactions > 0){
            reactions = 0;
            prev = '0';
            for (int i = 0; i < polymer.length(); i++) {
                if(Character.getNumericValue(polymer.charAt(i)) == Character.getNumericValue(prev) &&
                        Character.getType(polymer.charAt(i)) != Character.getType(prev)) {
                    String pattern = String.valueOf(prev) + polymer.charAt(i);
                    polymer = polymer.replaceFirst(pattern,"");
                    reactions += 1;
                    break;
                }

                prev = polymer.charAt(i);
            }
            //System.out.println(polymer.length());
        }
        return polymer.length();
    }
}

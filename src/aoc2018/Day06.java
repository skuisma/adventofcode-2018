package aoc2018;

import java.util.*;
import java.util.stream.Collectors;

public class Day06 {
    private String dataPath = "src\\resources\\input-day6-1.txt";
    private List<int[]> coords = new ArrayList<>();

    public static void main(String[] args) {
        Day06 day = new Day06();
        day.solve1();
        //day.solve2();
    }

    private void solve1() {
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();
        // minX, maxX, minY, maxY
        List<Integer> boundaries = new ArrayList<>();

        for (String line : data) {
            String[] val = line.split(", ");
            coords.add(Arrays.stream(val).mapToInt(Integer::parseInt).toArray());
        }

        int buffer = 0;
        boundaries.add(0,coords
                .stream()
                .mapToInt(x->x[0]-buffer)
                .min()
                .getAsInt());
        boundaries.add(1,coords
                .stream()
                .mapToInt(x->x[0]+buffer)
                .max()
                .getAsInt());
        boundaries.add(2,coords
                .stream()
                .mapToInt(x->x[1]-buffer)
                .min()
                .getAsInt());
        boundaries.add(3,coords
                .stream()
                .mapToInt(x->x[1]+buffer)
                .max()
                .getAsInt());

        boundaries.forEach(System.out::println);

        Map<Integer,Integer> areas = new HashMap<>();
        Map<Integer,Integer> distances = new HashMap<>();
        List<Integer> banned = new ArrayList<>();
        int totalDis = 0;
        for (int i = boundaries.get(0); i < boundaries.get(1)+1; i++) {
            for (int j = boundaries.get(2); j < boundaries.get(3)+1; j++) {
                distances.clear();
                for (int k = 0; k < coords.size(); k++) {
                    int dis = getDis(i,j,coords.get(k)[0], coords.get(k)[1]);
                    distances.put(k, dis);
                }
                int key = distances
                        .entrySet()
                        .stream()
                        .min(Comparator.comparingInt(Map.Entry::getValue))
                        .map(Map.Entry::getKey)
                        .get();

                //System.out.println("..." + i + " x " + j + " --> " + key);
                int dissum = distances
                        .entrySet()
                        .stream()
                        .map(Map.Entry::getValue)
                        .reduce(0,(x,y) -> x+y );
                if (dissum < 10000) {
                    totalDis += 1;
                }

                if ( distances.entrySet().stream().filter(x -> x.getValue() == distances.get(key)).count() == 1 ) {
                    int oldVal = areas.containsKey(key) ? areas.get(key) : 0;
                    areas.put(key, oldVal + 1);
                    //System.out.println("..." + i + " x " + j + " --> " + key);
                }
                if (    i == boundaries.get(0) ||
                        i == boundaries.get(1) ||
                        j == boundaries.get(2) ||
                        j == boundaries.get(3)
                ) {
                    banned.add(key);
                }
            }
        }

        banned.stream().distinct().forEach(x -> areas.remove(x));


        List<Integer> maxArea = areas
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        maxArea.stream().forEach(x -> {
            System.out.println("key " + x + "\txy " + Arrays.toString(coords.get(x)) + " " + areas.get(x));
        });
        System.out.println("Total area " + totalDis);

        /**
        List<int[]> filtered = coords
                .stream()
                .filter(ints -> ints[0]==boundaries.get(0))
                .filter(ints -> ints[0]==boundaries.get(1))
                .filter(ints -> ints[1]==boundaries.get(2))
                .filter(ints -> ints[1]==boundaries.get(3))
                .collect(Collectors.toList());

        filtered.forEach(c -> System.out.println(Arrays.toString(c)));
        **/


    }

    private int getDis(int x1, int y1, int x2, int y2) {
        int x = x1-x2 > 0 ? x1-x2 : x2-x1;
        int y = y1-y2 > 0 ? y1-y2 : y2-y1;
        return x+y;
    }

}

package aoc2018;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {
    private String dataPath = "src\\resources\\input-day4-1.txt";
    List<Guard> guards;

    public static void main(String[] args) {
        Day04 day = new Day04();
        day.solve();
        //day.solve2();
    }

    private void solve() {
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();

        Stream<String> sortedData = data
                .stream()
                .sorted();

        guards = sortedData
                .filter(line -> line.matches("^.*shift$"))
                .map(line -> {
                //    if (line.matches("^.*shift$")) {
                        Guard guard = new Guard();
                        if (line.substring(12, 14).equals("00")) {
                            guard.setShift(LocalDate.parse(line.substring(1, 11)));
                        } else {
                            guard.setShift(LocalDate.parse(line.substring(1, 11)).plusDays(1));
                        }
                        guard.setId(Integer.parseInt(line.replaceAll("^.+ Guard #(\\d+).*", "$1")));
                        return guard;
                })
                .collect(Collectors.toList());

        guards = data
                .stream()
                .sorted()
                .filter(line -> !line.matches("^.*shift$"))
                .map(line -> {
                    Guard guard = getGuard(LocalDate.parse(line.substring(1, 11)));
                    if (line.substring(19,24).equals("falls")) {
                        guard.setSleep(Integer.parseInt(line.substring(15,17)), 1);
                    } else {
                        guard.setSleep(Integer.parseInt(line.substring(15,17)), 0);
                    }
                    return guard;
                })
                .distinct()
                .collect(Collectors.toList());

        //guards.forEach(System.out::println);

        //HashMap<Integer, Integer> totalSleep = new HashMap<>();
        for (Guard g : guards) {
            //int sleep = totalSleep.getOrDefault(g.getId(), 0);
            int sleep = 0;
            for (int i = 0; i < g.getAsleep().length; i++) {
                sleep += g.getAsleep()[i];
            }
            //totalSleep.put(g.getId(),sleep);
            g.setTotalSleep(sleep);
        }

        //guards.forEach(System.out::println);


        Map<Integer,Integer> result = guards
                .stream()
                .collect(Collectors.groupingBy(o -> o.getId(), Collectors.summingInt(o -> o.getTotalSleep())));
                //.max(Comparator.comparing(g -> g.getTotalSleep()))

        int resultGuard = result.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();

        int maxSleepMinute = getMaxSleepMinute(resultGuard);
        System.out.println("Strategy 1:\nSleeping guard: " + resultGuard + " at minute of: " + maxSleepMinute + " --> " + resultGuard*maxSleepMinute);

        // STRATEGY 2

        Map<Integer,Integer> resultGuards = new HashMap<>();
        for (Guard g : guards) {
            int msm = getMaxSleep(g.getId());
            resultGuards.put(g.getId(),msm);
        }

        //resultGuards.entrySet().forEach(System.out::println);
        Map.Entry<Integer,Integer> resultGuard2 = resultGuards
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get();

        int minuteOfSleep = getMaxSleepMinute(resultGuard2.getKey());

        System.out.println("Strategy 2:\nSleeping guard: " + resultGuard2.getKey() + " at minute of: " + minuteOfSleep +
                " --> " + resultGuard2.getKey() * minuteOfSleep);
    }

    private int getMaxSleepMinute (int guardId) {
        int sumSleep[] = new int[60];
        int maxSleepMinute = 0;

        for (Guard g : guards) {
            if ( g.getId() == guardId) {
                for (int i = 0; i < g.getAsleep().length; i++) {
                    sumSleep[i] += g.getAsleep()[i];
                }
            }
        }

        int maxSleep = Arrays.stream(sumSleep)
                .max()
                .getAsInt();

        for (int i = 0; i < sumSleep.length; i++) {
            if (sumSleep[i] == maxSleep) { maxSleepMinute = i;};
        }
        return maxSleepMinute;
    }

    private int getMaxSleep(int guardId) {
        int sumSleep[] = new int[60];

        for (Guard g : guards) {
            if ( g.getId() == guardId) {
                for (int i = 0; i < g.getAsleep().length; i++) {
                    sumSleep[i] += g.getAsleep()[i];
                }
            }
        }

        int maxSleep = Arrays.stream(sumSleep)
                .max()
                .getAsInt();

        return maxSleep;
    }

    private Guard getGuard(LocalDate sh) {
        Guard ng = null;
        for (Guard g : guards) {
            if ( g.getShift().equals(sh)){
                ng = g;
            }
        }
        return ng;
    }

}

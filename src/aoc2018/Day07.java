package aoc2018;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 {
    private String dataPath = "src\\resources\\input-day7-1.txt";
    Map<String, List<String>> guide = new TreeMap<>();
    List<String> result = new ArrayList<>();
    List<String> abc = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z").collect(Collectors.toList());
    int time = 0;

    public static void main(String[] args) {
        Day07 day = new Day07();
        //day.solve1();
        day.solve2();
    }

    private void solve1() {
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();

        for (String line : data) {
            String vals[] = line.replaceAll("^.+ ([A-Z]) .* ([A-Z]) .+$", "$1,$2").split(",");
            List<String> tmp;
            if (guide.containsKey(vals[1])) {
                tmp = guide.get(vals[1]);
            } else {
                tmp = new ArrayList<>();
            }
            tmp.add(vals[0]);
            guide.put(vals[1], tmp);
            if (!guide.containsKey(vals[0])) {
                guide.put(vals[0], new ArrayList<>());
            }
        }

        while (result.size() < guide.size()) {
            for (Map.Entry<String, List<String>> entry : guide.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue().stream().sorted().collect(Collectors.toList());
                //System.out.println(key + value.toString());

                if (guide.get(key).size() == 0 && !result.contains(key)) {
                    //System.out.println("Added: " + key );
                    result.add(key);
                    removeValues(key);
                    break;
                }
            }
        }
        result.stream().forEach(System.out::print);

    }

    private void solve2() {
        FileReader fr = new FileReader(dataPath);
        List<String> data = fr.readFile();
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());

        for (String line : data) {
            String vals[] = line.replaceAll("^.+ ([A-Z]) .* ([A-Z]) .+$", "$1,$2").split(",");
            List<String> tmp;
            if (guide.containsKey(vals[1])) {
                tmp = guide.get(vals[1]);
            } else {
                tmp = new ArrayList<>();
            }
            tmp.add(vals[0]);
            guide.put(vals[1], tmp);
            if (!guide.containsKey(vals[0])) {
                guide.put(vals[0], new ArrayList<>());
            }
        }
        //guide.entrySet().stream().forEach(System.out::print);
        while (result.size() < guide.size()) {
            for (Map.Entry<String, List<String>> entry : guide.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue().stream().sorted().collect(Collectors.toList());
                //System.out.println(key + value.toString());
                if (guide.get(key).size() == 0 && !result.contains(key) && !isTaskInProgress(key, workers)) {
                    //System.out.println("Added: " + key + " " + abc.indexOf(key));
                    //result.add(key);
                    addTaskToWorker(key, time, workers);
                    //removeValues(key);

                }
            }
            //workers.stream().forEach(System.out::println);
            //System.out.println("time: " + time);
            time++;
            for (Worker w : workers) {
                w.isBusy(time);
            }
        }
        result.stream().forEach(System.out::print);
        System.out.println("\nTime: " + time);
    }

    private boolean isTaskInProgress(String key, List<Worker> wrks) {
        boolean inProgress = false;
        for (Worker w : wrks) {
            if (w.isBusy(key)){
                inProgress = true;
            }
        }
        return inProgress;
    }

    private void addTaskToWorker(String key, int time, List<Worker> wrks) {
        for (Worker w : wrks) {
            if (!w.isBusy()) {
                w.setTask(key, time);
                break;
            }
        }
    }


    private void removeValues(String key) {
        for (Map.Entry<String, List<String>> entry : guide.entrySet()) {
            if (entry.getValue().contains(key)){
                guide.get(entry.getKey()).remove(entry.getValue().indexOf(key));
            }
        }
    }


    private class Worker {
        String key;
        int endTime;
        boolean busy;

        public Worker() {
            key = "0";
            endTime = -1;
            busy = false;
        }

        public void setTask(String key, int time){
            this.key = key;
            this.endTime = time + 60 + abc.indexOf(key) + 1;
            // Test setup
            //this.endTime = time + abc.indexOf(key) + 1;
            this.busy = true;
        }

        public boolean isBusy(int currentTime) {
            if ( endTime == currentTime ) {
                result.add(key);
                removeValues(key);
                key = "0";
                endTime = -1;
                busy = false;
            }
            return busy;
        }

        public boolean isBusy() {
            return busy;
        }

        public boolean isBusy(String key) {
            boolean b = this.key.equals(key) ? true : false;
            return b;
        }

        @Override
        public String toString() {
            return "Worker{" +
                    "key='" + key + '\'' +
                    ", endTime=" + endTime +
                    ", busy=" + busy +
                    '}';
        }
    }
}


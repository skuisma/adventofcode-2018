package aoc2018;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

    public static void main(String[] args) {
        Long curTime = System.nanoTime();
        Day09 day = new Day09();
        day.solve(1);
        day.solve(100);
        Long delta = System.nanoTime() - curTime;
        System.out.println("Elapsed time: " + delta / 1000000 + "ms");
    }

    private void solve(int multiplier) {
        String dataPath = "src\\resources\\input-day9-1.txt";
        FileReader fr = new FileReader(dataPath);
        String[] data = fr.readFirstRow().replaceAll("[a-z ]", "").split(";");

        int players = Integer.parseInt(data[0]);
        int currentPlayer = 0;
        List<Long> scores = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            scores.add(i, 0L);
        }
        int maxMarble = Integer.parseInt(data[1])*multiplier;

        List<Integer> game = new LinkedList<>();
        ListIterator<Integer> currentMarble = game.listIterator();
        // Initialize game
        currentMarble.add(0);
        currentMarble.add(1);
        currentMarble.previous();
        currentMarble.add(2);

        for (int i = 3; i <= maxMarble; i++) {
            if ( i % 23 == 0) { // Points round
                Long roundScore = scores.get(currentPlayer) + i;
                int value = 0;

                for (int j = 0; j < 8; j++) {
                    if (currentMarble.hasPrevious()) {
                        value = currentMarble.previous();
                    } else {
                        currentMarble = game.listIterator(game.size());
                        value = currentMarble.previous();
                    }
                }
                currentMarble.remove();
                currentMarble.next();
                scores.set(currentPlayer, roundScore + value);
            } else { // Normal round
                if (currentMarble.hasNext()) {
                    currentMarble.next();
                } else {
                    currentMarble = game.listIterator(0);
                    currentMarble.next();
                }
                currentMarble.add(i);
            }

            //System.out.println(currentPlayer + " " + game);
            currentPlayer = changeTurn(currentPlayer, players);
        }
        System.out.println(scores.stream().mapToLong(Long::longValue).max().getAsLong());
    }

    private int changeTurn(int t, int max) {
        t++;
        return t == max ? 0 : t;
    }
}
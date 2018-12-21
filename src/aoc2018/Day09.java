package aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

    public static void main(String[] args) {
        Long curTime = System.nanoTime();
        Day09 day = new Day09();
        day.solve();
        Long delta = System.nanoTime() - curTime;
        System.out.println("Elapsed time: " + delta / 1000000 + "ms");
    }

    private void solve() {
        String dataPath = "src\\resources\\input-day9-1.txt";
        FileReader fr = new FileReader(dataPath);
        String[] data = fr.readFirstRow().replaceAll("[a-z ]", "").split(";");

        int players = Integer.parseInt(data[0]);
        int currentPlayer = 0;
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            scores.add(i, 0);
        }
        int maxMarble = Integer.parseInt(data[1]);
        int marble = 1;
        //List<Integer> game = new ArrayList<>();
        List<Integer> game = new LinkedList<>();
        // Initialize game
        game.add(0);
        game.add(1, 1);
        game.add(1, 2);
        int currentMarble = 2;

        for (int i = 3; i <= maxMarble; i++) {
            if ( i % 23 == 0) { // Points round
                int roundScore = scores.get(currentPlayer) + i;

                int index = getNextIndex(game, currentMarble, 7, false);
                roundScore += game.remove(index);
                currentMarble = game.get(index);

                scores.set(currentPlayer, roundScore);
                //System.out.println(i);
            } else { // Normal round
                int place = getNextIndex(game, currentMarble, 2, true);
                game.add(place, i);
                currentMarble = i;
            }

            //System.out.println(currentPlayer + " " + currentMarble + " " + game);
            currentPlayer = changeTurn(currentPlayer, players);
        }
        //scores.stream().forEach(System.out::println);
        System.out.println(scores.stream().mapToInt(Integer::intValue).max().getAsInt());
    }

    private int getNextIndex(List<Integer> game, int currentMarble, int move, boolean clockwise){
        int index = 0;
        if (clockwise) {
            int nextIndex = game.indexOf(currentMarble) + move;
            index = game.size() >= nextIndex ? nextIndex : nextIndex - game.size();
        } else {
            int nextIndex = game.indexOf(currentMarble) - move;
            index = nextIndex > 0 ? nextIndex : game.size() + nextIndex;
        }
        return index;
    }


    private int changeTurn(int t, int max) {
        t++;
        return t == max ? 0 : t;
    }
}


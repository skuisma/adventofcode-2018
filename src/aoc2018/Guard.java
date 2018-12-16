package aoc2018;

import java.time.LocalDate;
import java.util.Arrays;

public class Guard {
    private int id;
    private LocalDate shift;
    private int[] asleep;
    private int totalSleep;

    public Guard() {
        asleep = new int[60];
    }

    public Guard(int id, LocalDate shift) {
        this.id = id;
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "Guard{" +
                "id=" + id +
                ", shift=" + shift +
                ", asleep=" + Arrays.toString(asleep) +
                ", totalSleep=" + totalSleep +
                '}';
    }

    public void setSleep(int min, int sleep) {
        for (int i = min; i < asleep.length; i++) {
            asleep[i] = sleep;
        }
    }

    public int[] getAsleep() {
        return asleep;
    }

    public LocalDate getShift() {
        return shift;
    }

    public void setShift(LocalDate shift) {
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalSleep() {
        return totalSleep;
    }

    public void setTotalSleep(int totalSleep) {
        this.totalSleep = totalSleep;
    }
}

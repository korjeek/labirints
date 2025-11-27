package academy.maze.dto;

import java.util.Arrays;
import java.util.Random;

/** Направления движения в лабиринте. */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);

    public static final Random RANDOM = new Random();
    private static final Direction[] VALUES = values();
    public final int deltaX;
    public final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Возвращает все направления в случайном порядке.
     *
     * @return массив направлений в случайном порядке
     */
    public static Direction[] getShuffledDirections() {
        Direction[] shuffledDirections = getDirections();
        for (int i = shuffledDirections.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            Direction temp = shuffledDirections[i];
            shuffledDirections[i] = shuffledDirections[j];
            shuffledDirections[j] = temp;
        }

        return shuffledDirections;
    }

    public static Direction[] getDirections() {
        return Arrays.copyOf(VALUES, VALUES.length);
    }
}

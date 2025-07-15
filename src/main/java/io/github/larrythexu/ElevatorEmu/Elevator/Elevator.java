package io.github.larrythexu.ElevatorEmu.Elevator;

import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import lombok.Data;

import java.util.Comparator;
import java.util.PriorityQueue;

@Data
public class Elevator {

    private final int id;
    private int currFloor = 0;
    private Direction direction = Direction.NEUTRAL;
    private PriorityQueue<Integer> upList = new PriorityQueue<>();
    private PriorityQueue<Integer> downList = new PriorityQueue<>(Comparator.reverseOrder());
    // Potentially add a separate field for STOPS (so that it's not immediate)

    public int moveUp() {
        return ++currFloor;
    }

    public int moveDown() {
        return --currFloor;
    }

    public boolean isNeutral() {
        return direction == Direction.NEUTRAL;
    }

    /**
     * Updates the elevator's state based on its direction and destinations.
     * If it reaches a floor, it removes that floor in its queue.
     * If it completes all floors in one direction, it'll switch directions or go neutral
     * depending on the remaining floors.
     */
    public void updateState() {
        removeOnSameFloor();

        // If no more destinations, set to Neutral
        if (upList.isEmpty() && downList.isEmpty()) {
            direction = Direction.NEUTRAL;
            return;
        }

        // Assuming either upList or downList isn't empty, we go Up or Down
        switch (direction) {
            case NEUTRAL:
                direction = !upList.isEmpty() ? Direction.UP : Direction.DOWN;
                break;

            case UP:
                if (upList.isEmpty()) {
                    direction = Direction.DOWN;
                } else {
                    moveUp();
                }
                break;

            case DOWN:
                if (downList.isEmpty()) {
                    direction = Direction.UP;
                } else {
                    moveDown();
                }
                break;
        }
    }

    private void removeOnSameFloor() {
        if (Integer.valueOf(currFloor).equals(upList.peek())) {
            upList.remove();
        }
        if (Integer.valueOf(currFloor).equals(downList.peek())) {
            downList.remove();
        }
    }

    public void addFloor(int targetFloor) {
        if (currFloor == targetFloor) {
            return;
        }

        if (currFloor > targetFloor) {
            downList.add(targetFloor);
        } else {
            upList.add(targetFloor);
        }
    }

    /**
     * Returns the next target destination floor, depending on direction
     * If no floors in current direction, returns -1
     */
    public int getNextFloor() {
        return switch (direction) {
            case UP -> (upList.isEmpty()) ? -1 : upList.poll();
            case DOWN -> (downList.isEmpty()) ? -1 : downList.poll();
            default -> -1;
        };
    }
}

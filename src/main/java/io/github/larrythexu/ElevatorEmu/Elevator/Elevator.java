package io.github.larrythexu.ElevatorEmu.Elevator;

import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Elevator {

  private final int id;
  private int currFloor = 0;
  private Direction direction = Direction.NEUTRAL;
  private PriorityQueue<Integer> upList = new PriorityQueue<>();
  private PriorityQueue<Integer> downList = new PriorityQueue<>(Comparator.reverseOrder());

  // Potentially add a separate field for STOPS (so that it's not immediate)

  public int moveUp() {
    currFloor += 1;
    log.debug("Elevator {}: Moving UP to floor {}", id, currFloor);
    return currFloor;
  }

  public int moveDown() {
    currFloor -= 1;
    log.debug("Elevator {}: Moving DOWN to floor {}", id, currFloor);
    return currFloor;
  }

  /**
   * Updates the elevator's state based on its direction and destinations. If it reaches a floor, it
   * removes that floor in its queue. If it completes all floors in one direction, it'll switch
   * directions or go neutral depending on the remaining floors.
   */
  public void updateState() {
    removeOnSameFloor();

    // If no more destinations, set to Neutral
    if (upList.isEmpty() && downList.isEmpty()) {
      //      log.info("Elevator {}: No more destinations, going neutral", id);
      direction = Direction.NEUTRAL;
      return;
    }

    // Assuming either upList or downList isn't empty, we go Up or Down
    switch (direction) {
        // Current behavior: When NEUTRAL->UP/DOWN, it takes 1 turn
      case NEUTRAL:
        // TODO: should movement be processed immediately?
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
      log.info("Elevator {}: arrived at queued floor {}", id, currFloor);
      upList.remove();
    }
    if (Integer.valueOf(currFloor).equals(downList.peek())) {
      log.info("Elevator {}: arrived at queued floor {}", id, currFloor);
      downList.remove();
    }
  }

  public void addFloor(int targetFloor) {
    if (currFloor == targetFloor) {
      log.info("Already on floor {}, doing nothing", targetFloor);
      return;
    }
    if (upList.contains(targetFloor) || downList.contains(targetFloor)) {
      log.info("Floor already queued, doing nothing");
      return;
    }

    if (currFloor > targetFloor) {
      downList.add(targetFloor);
    } else {
      upList.add(targetFloor);
    }
  }

  public ElevatorStateDTO getState() {
    List<Integer> destinations = new ArrayList<>();

    if (direction == Direction.UP || direction == Direction.NEUTRAL) {
      destinations.addAll(upList);
      destinations.addAll(downList);
    } else {
      destinations.addAll(downList);
      destinations.addAll(upList);
    }

    return new ElevatorStateDTO(id, currFloor, direction, destinations);
  }

  //  /**
  //   * Returns the next target destination floor, depending on direction If no floors in current
  //   * direction, returns -1
  //   */
  //  public int getNextTargetFloor() {
  //    return switch (direction) {
  //      case UP -> (upList.isEmpty()) ? -1 : upList.peek();
  //      case DOWN -> (downList.isEmpty()) ? -1 : downList.peek();
  //      default -> -1;
  //    };
  //  }
}

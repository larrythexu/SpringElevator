package io.github.larrythexu.ElevatorEmu.ElevatorManager.Selector;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;
import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProximitySelector implements SelectorStrategy {

  // Proximity Selector chooses based on which elevator is closest AND going in the correct
  // direction
  @Override
  public Elevator chooseElevator(ElevatorRepository elevatorRepository, int requestFloor) {
    List<Elevator> elevatorList = elevatorRepository.getAllElevators();

    // Do one-pass, check min distance based on directionality
    int minDistance = Integer.MAX_VALUE;
    Elevator chosenElevator = null;

    for (Elevator elevator : elevatorList) {
      int floorDiff = requestFloor - elevator.getCurrFloor();
      int distance = Math.abs(floorDiff);
      // Don't even consider if diff is already too great
      if (distance >= minDistance) {
        continue;
      }

      // If Pos direction, check if elevator is going UP or NEUTRAL
      // If Neg direction, check if elevator is going DOWN or NEUTRAL
      if ((elevator.getDirection() == Direction.NEUTRAL)
          || (floorDiff >= 0 && elevator.getDirection() == Direction.UP)
          || (floorDiff < 0 && elevator.getDirection() == Direction.DOWN)) {
        minDistance = distance;
        chosenElevator = elevator;
      }
    }

    return chosenElevator;
  }

  @Override
  public String name() {
    return this.getClass().getSimpleName();
  }
}

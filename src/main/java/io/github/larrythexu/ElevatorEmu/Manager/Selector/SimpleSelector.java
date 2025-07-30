package io.github.larrythexu.ElevatorEmu.Manager.Selector;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SimpleSelector implements SelectorStrategy {

  // Simple selector has basic logic.
  // It just chooses the next elevator in the list
  private int counter = 0;

  @Override
  public Elevator chooseElevator(ElevatorRepository elevatorRepository, int requestFloor) {
    List<Elevator> elevatorList = elevatorRepository.getAllElevators();
    // Reset counter back to beginning if end of list
    if (counter >= elevatorList.size()) {
      counter = 0;
    }

    return elevatorList.get(counter++);
  }

  @Override
  public String name() {
    return this.getClass().getSimpleName();
  }
}

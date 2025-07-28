package io.github.larrythexu.ElevatorEmu.Manager;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;
import io.github.larrythexu.ElevatorEmu.Manager.Selector.SelectorStrategy;
import jakarta.annotation.PostConstruct;
import java.util.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * The primary elevator manager class Handles the elevator updates, manages which elevators take
 * which request
 */
@Getter
@Service
public class ElevatorManager {

  // In-memory list of elevators and their states!!
  private final ElevatorRepository elevatorRepository;
  private SelectorStrategy activeStrategy;
  private final Map<String, SelectorStrategy> selectionStrategies;

  public ElevatorManager(
      ElevatorRepository elevatorRepository, Map<String, SelectorStrategy> selectionStrategies) {
    this.elevatorRepository = elevatorRepository;
    this.selectionStrategies = selectionStrategies;

    // TODO: temporary only creating a single elevator
    //        this.elevatorList.add(new Elevator(1));
    //        for (int i = 1; i < 3; i++) {
    //            elevatorList.add(new Elevator(i));
    //        }
  }

  @PostConstruct
  public void init() {
    // Set default selection strategy
    this.activeStrategy = selectionStrategies.get("SimpleSelector");
  }

  /** Adds a new elevator into the system */
  public Elevator addElevator() {
    // TODO: update ID generation - will cause clashes when we introduce removals
    Elevator newElevator = new Elevator(elevatorRepository.getSize() + 1);
    elevatorRepository.addElevator(newElevator);
    return newElevator;
  }

  public Optional<Elevator> getElevator(int id) {
    return elevatorRepository.getElevator(id);
  }

  public void stepElevators() {
    elevatorRepository.getAllElevators().forEach(Elevator::updateState);
  }

  public void handleFloorRequest(int floor) {
    Elevator chosenElevator = activeStrategy.chooseElevator(elevatorRepository, floor);
    chosenElevator.addFloor(floor);
  }

  public void setSelectionStrategy(String newStrategy) {
    SelectorStrategy strategy = selectionStrategies.get(newStrategy);

    if (strategy == null) {
      throw new IllegalArgumentException("No such selection strategy " + newStrategy);
    }

    this.activeStrategy = strategy;
  }
}

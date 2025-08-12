package io.github.larrythexu.ElevatorEmu.ElevatorManager;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.Elevator.ElevatorStateDTO;
import io.github.larrythexu.ElevatorEmu.ElevatorManager.Selector.SelectorStrategy;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;
import io.github.larrythexu.ElevatorEmu.Exceptions.ElevatorNotFoundException;
import jakarta.annotation.PostConstruct;
import java.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The primary elevator manager class Handles the elevator updates, manages which elevators take
 * which request
 */
@Getter
@Service
@Slf4j
public class ElevatorManager {

  // In-memory list of elevators and their states!!
  private final ElevatorRepository elevatorRepository;
  private SelectorStrategy activeStrategy;
  private final Map<String, SelectorStrategy> selectionStrategies;

  public ElevatorManager(
      ElevatorRepository elevatorRepository, List<SelectorStrategy> strategyList) {
    this.elevatorRepository = elevatorRepository;

    // Put all selector strategies into an accessible map
    // K:V - strategy name (lowercase) : strategy
    this.selectionStrategies = new HashMap<>();
    for (SelectorStrategy strategy : strategyList) {
      selectionStrategies.put(strategy.name().toLowerCase(), strategy);
    }
  }

  @PostConstruct
  public void init() {
    // Set default selection strategy TODO: there's gotta be a better way to set this
    this.activeStrategy = selectionStrategies.get("simpleselector");
  }

  /** Adds a new elevator into the system */
  public Elevator addElevator() {
    // TODO: update ID generation - will cause clashes when we introduce removals
    Elevator newElevator = new Elevator(elevatorRepository.getSize() + 1);
    elevatorRepository.addElevator(newElevator);
    return newElevator;
  }

  public Elevator getElevator(int id) {
    return elevatorRepository.getElevator(id).orElseThrow(() -> new ElevatorNotFoundException(id));
  }

  public List<Elevator> getAllElevators() {
    return elevatorRepository.getAllElevators();
  }

  public List<Elevator> stepElevators() {
    elevatorRepository.getAllElevators().forEach(Elevator::updateState);
    return elevatorRepository.getAllElevators();
  }

  public Elevator handleFloorRequest(int floor) {
    Elevator chosenElevator = activeStrategy.chooseElevator(elevatorRepository, floor);
    chosenElevator.addFloor(floor);
    return chosenElevator;
  }

  public SelectorStrategy setSelectionStrategy(String newStrategy) {
    SelectorStrategy strategy = selectionStrategies.get(newStrategy);

    if (strategy == null) {
      throw new IllegalArgumentException("No such selection strategy " + newStrategy);
    }

    this.activeStrategy = strategy;
    return strategy;
  }

  public List<ElevatorStateDTO> getStates() {
    List<ElevatorStateDTO> elevatorStates = new ArrayList<>();
    elevatorRepository.getAllElevators().forEach(elevator -> {
      elevatorStates.add(elevator.getState());
    });

    return elevatorStates;
  }
}

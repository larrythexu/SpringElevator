package io.github.larrythexu.ElevatorEmu.Manager.Selector;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;

public interface SelectorStrategy {
  Elevator chooseElevator(ElevatorRepository elevatorRepository, int requestFloor);
}

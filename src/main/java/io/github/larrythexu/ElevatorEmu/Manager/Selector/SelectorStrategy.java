package io.github.larrythexu.ElevatorEmu.Manager.Selector;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;

import java.util.List;

public interface SelectorStrategy {
    Elevator chooseElevator(List<Elevator> elevatorList, int requestFloor);
}

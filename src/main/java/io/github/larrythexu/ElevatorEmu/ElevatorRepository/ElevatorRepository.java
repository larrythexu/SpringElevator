package io.github.larrythexu.ElevatorEmu.ElevatorRepository;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import java.util.List;
import java.util.Optional;

public interface ElevatorRepository {
  List<Elevator> getAllElevators();

  Optional<Elevator> getElevator(int id);

  void addElevator(Elevator elevator);

  void removeElevator(Elevator elevator);

  int getSize();
}

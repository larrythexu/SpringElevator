package io.github.larrythexu.ElevatorEmu.ElevatorRepository;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryElevatorRepository implements ElevatorRepository {
  // In memory
  private final List<Elevator> elevatorList = new ArrayList<>();

  @Override
  public List<Elevator> getAllElevators() {
    return List.copyOf(elevatorList);
  }

  @Override
  public Optional<Elevator> getElevator(int id) {
    return elevatorList.stream().filter(e -> e.getId() == id).findFirst();
  }

  @Override
  public void addElevator(Elevator elevator) {
    elevatorList.add(elevator);
  }

  @Override
  public void removeElevator(Elevator elevator) {
    elevatorList.remove(elevator);
  }

  @Override
  public int getSize() {
    return elevatorList.size();
  }

  @Override
  public void clear() {
    elevatorList.clear();
  }
}

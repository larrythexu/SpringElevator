package io.github.larrythexu.ElevatorEmu.Manager;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.Manager.Selector.SelectorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ElevatorManagerController {

  private final ElevatorManager elevatorManager;

  @GetMapping("/elevators/{id}")
  public Elevator getElevator(
          @PathVariable("id") int id
  ) {
    return elevatorManager.getElevator(id);
  }

  @GetMapping("/elevators")
  public List<Elevator> getElevators() {
    return elevatorManager.getAllElevators();
  }

  @PostMapping("/elevators/add-elevator")
  public Elevator addElevator() {
    return elevatorManager.addElevator();
  }

//  Potentially have a way to add customized/specific elevators?
//  - maybe dto implementation needed
//  public Elevator addElevatorClass()

  @PostMapping("/elevators/request-floor/{floor}")
  public void requestFloor(
          @PathVariable("floor") int floor
  ) {
    elevatorManager.handleFloorRequest(floor);
  }

  @GetMapping("/strategy")
  public String getSelectionStrategy() {
    return elevatorManager.getActiveStrategy().name();
  }

  @PostMapping("/strategy/{strategy-name}")
  public String setSelectionStrategy(
          @PathVariable("strategy-name") String strategy
  ) {
    return elevatorManager.setSelectionStrategy(strategy.toLowerCase()).name();
  }

}

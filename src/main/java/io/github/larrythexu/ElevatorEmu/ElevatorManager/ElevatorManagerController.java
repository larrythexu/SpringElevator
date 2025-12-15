package io.github.larrythexu.ElevatorEmu.ElevatorManager;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ElevatorManagerController {

  private final ElevatorManager elevatorManager;

  @GetMapping("/elevators/{id}")
  public Elevator getElevator(@PathVariable("id") int id) {
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

  @PostMapping("/elevators/add-elevator/{num-elevators}")
  public List<Elevator> addElevators(@PathVariable("num-elevators") int numElevators) {
    List<Elevator> elevatorList = new ArrayList<>();

    for (int i = 0; i < numElevators; i++) {
      elevatorList.add(elevatorManager.addElevator());
    }
    return elevatorList;
  }

  @PostMapping("/elevators/remove-elevator/{elevator-id}")
  public void removeElevator(@PathVariable("elevator-id") int id) {
    elevatorManager.removeElevator(id);
  }

  @PostMapping("/elevators/reset")
  public void resetEmulator() {
    elevatorManager.resetElevators();
  }

  // FOR DEBUGGING
  @PostMapping("/elevators/step")
  public List<Elevator> stepElevators() {
    return elevatorManager.stepElevators();
  }

  //  Potentially have a way to add customized/specific elevators?
  //  - maybe dto implementation needed
  //  public Elevator addElevatorClass()

  @PostMapping("/elevators/request-floor/{floor}")
  public Elevator requestFloor(@PathVariable("floor") int floor) {
    return elevatorManager.handleFloorRequest(floor);
  }

  @GetMapping("/strategy")
  public String getSelectionStrategy() {
    return elevatorManager.getActiveStrategy().name();
  }

  @PostMapping("/strategy/{strategy-name}")
  public String setSelectionStrategy(@PathVariable("strategy-name") String strategy) {
    return elevatorManager.setSelectionStrategy(strategy.toLowerCase()).name();
  }
}

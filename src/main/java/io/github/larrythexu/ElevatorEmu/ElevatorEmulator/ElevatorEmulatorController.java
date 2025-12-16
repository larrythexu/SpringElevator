package io.github.larrythexu.ElevatorEmu.ElevatorEmulator;

import io.github.larrythexu.ElevatorEmu.Enums.EmulatorState;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ElevatorEmulatorController {

  private final ElevatorEmulatorService elevatorEmulatorService;

  @GetMapping("/emulator")
  public EmulatorState getEmulatorState() {
    return elevatorEmulatorService.getEmulatorState();
  }

  @PostMapping("/emulator/start")
  public void startEmulator() {
    elevatorEmulatorService.start();
  }

  @PostMapping("/emulator/stop")
  public void stopEmulator() {
    elevatorEmulatorService.stop();
  }

  @GetMapping("/emulator/delay")
  public long getDelay() {
    return elevatorEmulatorService.getStepDelay();
  }

  @PostMapping("/emulator/delay/{new-delay}")
  public long setDelay(@PathVariable("new-delay") long delay) {
    elevatorEmulatorService.setStepDelay(delay);
    return delay;
  }
}

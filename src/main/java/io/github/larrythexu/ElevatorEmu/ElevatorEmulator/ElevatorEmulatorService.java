package io.github.larrythexu.ElevatorEmu.ElevatorEmulator;

import io.github.larrythexu.ElevatorEmu.Elevator.ElevatorStateDTO;
import io.github.larrythexu.ElevatorEmu.ElevatorManager.ElevatorManager;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.github.larrythexu.ElevatorEmu.Enums.EmulatorState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
/**
 * This class acts as logic for the overall emulator service a user is exposed to.
 * Here, there is logic to start + stop the service, and for requesting floors.
 */
public class ElevatorEmulatorService {

  private final ElevatorManager elevatorManager;
  private final ScheduledExecutorService elevatorScheduler;
  private final SimpMessagingTemplate messagingTemplate; // Bean for sending elevator states via WebSocket

  // WebSocket topic URL
  private String ELEVATOR_STATE_TOPIC = "/topic/elevator/states";

  @Setter @Getter private long stepDelay = 2000;

  private ScheduledFuture<?> emulationTask;

  public ElevatorEmulatorService(
      ElevatorManager elevatorManager, ScheduledExecutorService elevatorScheduler, SimpMessagingTemplate messagingTemplate) {
    this.elevatorManager = elevatorManager;
    this.elevatorScheduler = elevatorScheduler;
    this.messagingTemplate = messagingTemplate;
  }

  public void start() {
    if (emulationTask == null || emulationTask.isCancelled()) {
      emulationTask =
          elevatorScheduler.scheduleAtFixedRate(() -> {
            // Update elevators and send state to websocket
            elevatorManager.stepElevators();
            sendStateUpdates();
          }, 0, stepDelay, TimeUnit.MILLISECONDS);
    } else {
      log.debug("Emulation task already started, ignoring request");
    }
  }

  public void stop() {
    if (emulationTask != null && !emulationTask.isCancelled()) {
      emulationTask.cancel(false);
    } else {
      log.debug("Emulation task is not running, ignoring request");
    }
  }

  public EmulatorState getEmulatorState() {
    return emulationTask == null || emulationTask.isCancelled() ?
            EmulatorState.NOT_RUNNING : EmulatorState.RUNNING;
  }

  private void sendStateUpdates() {
    List<ElevatorStateDTO> states = elevatorManager.getStates();

    messagingTemplate.convertAndSend(ELEVATOR_STATE_TOPIC, states);
  }
}

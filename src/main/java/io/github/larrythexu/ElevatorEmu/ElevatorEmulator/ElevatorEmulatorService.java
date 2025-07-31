package io.github.larrythexu.ElevatorEmu.ElevatorEmulator;

import io.github.larrythexu.ElevatorEmu.ElevatorManager.ElevatorManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ElevatorEmulatorService {

    private final ElevatorManager elevatorManager;
    private final ScheduledExecutorService elevatorScheduler;

    @Setter
    @Getter
    private long stepDelay = 2000;

    private ScheduledFuture<?> emulationTask;

    public ElevatorEmulatorService(
            ElevatorManager elevatorManager,
            ScheduledExecutorService elevatorScheduler
    ) {
      this.elevatorManager = elevatorManager;
      this.elevatorScheduler = elevatorScheduler;
    }

  // todo: consider synchronized?
  public void start() {
      if (emulationTask == null || emulationTask.isCancelled()) {
        emulationTask = elevatorScheduler.scheduleAtFixedRate(
                elevatorManager::stepElevators,
                0,
                stepDelay,
                TimeUnit.MILLISECONDS
        );
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

}

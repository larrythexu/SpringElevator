package io.github.larrythexu.ElevatorEmu.ElevatorEmulator;

import io.github.larrythexu.ElevatorEmu.ElevatorManager.ElevatorManager;
import io.github.larrythexu.ElevatorEmu.Enums.EmulatorState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ElevatorEmulatorServiceTest {

  private ElevatorEmulatorService elevatorEmulatorService;

  @Mock private ElevatorManager elevatorManager;

  @Mock private ScheduledExecutorService elevatorScheduler;

  @Mock private ScheduledFuture<?> mockFuture;

  long TEST_STEP_DELAY = 2000;
  long TEST_START_DELAY = 0;

  @BeforeEach
  void setup() {
    elevatorEmulatorService = new ElevatorEmulatorService(elevatorManager, elevatorScheduler);
  }

  @Test
  void testStart() {
    elevatorEmulatorService.start();
    verify(elevatorScheduler).scheduleAtFixedRate(any(Runnable.class), eq(TEST_START_DELAY), eq(TEST_STEP_DELAY), eq(TimeUnit.MILLISECONDS));
  }
}

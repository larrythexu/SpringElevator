package io.github.larrythexu.ElevatorEmu.ElevatorManager.Selector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class SimpleSelectorTest extends AbstractSelectorTest {

  @Test
  void testSequentialChoose() {
    SimpleSelector simpleSelector = new SimpleSelector();

    // Sequential
    assertEquals(test1, simpleSelector.chooseElevator(elevatorRepository, 10));
    assertEquals(test2, simpleSelector.chooseElevator(elevatorRepository, 0));
    assertEquals(test3, simpleSelector.chooseElevator(elevatorRepository, 5));
    assertEquals(test4, simpleSelector.chooseElevator(elevatorRepository, 5));
    assertEquals(test5, simpleSelector.chooseElevator(elevatorRepository, 5));

    // Resets to 0
    assertEquals(test1, simpleSelector.chooseElevator(elevatorRepository, 0));
  }
}

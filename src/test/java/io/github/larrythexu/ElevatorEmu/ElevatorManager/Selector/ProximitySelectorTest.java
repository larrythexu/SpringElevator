package io.github.larrythexu.ElevatorEmu.ElevatorManager.Selector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import org.junit.jupiter.api.Test;

public class ProximitySelectorTest extends AbstractSelectorTest {

  private final ProximitySelector proximitySelector = new ProximitySelector();

  @Test
  void testSameFloor() {
    assertEquals(test2, proximitySelector.chooseElevator(elevatorRepository, 10));
  }

  @Test
  void testClosestFloor() {
    // Target: F7
    // test1 (F0) vs test3 (F4) vs test5 (F5) .. vs test2 (F10)
    test5.setDirection(Direction.UP);
    assertEquals(test5, proximitySelector.chooseElevator(elevatorRepository, 7));

    // Target: F3
    // test2 (F10) vs test4 (F6) vs test5 (F5) .. vs test1 (F0)
    test5.setDirection(Direction.DOWN);
    assertEquals(test5, proximitySelector.chooseElevator(elevatorRepository, 3));
  }

  @Test
  void testClosestFloorNeutral() {
    test5.setDirection(Direction.DOWN);
    // Target: F8
    // test2 (F10) vs test3 (F4)
    assertEquals(test2, proximitySelector.chooseElevator(elevatorRepository, 8));

    test5.setDirection(Direction.UP);
    // Target: F2
    // test1 (F0) vs test4 (F6)
    assertEquals(test1, proximitySelector.chooseElevator(elevatorRepository, 2));
  }

  @Test
  void testSameDist() {
    // Target: F2
    // test1 (F0) vs test3 (F4)
    test3.setDirection(Direction.DOWN);
    assertEquals(test1, proximitySelector.chooseElevator(elevatorRepository, 2));

    // Target: F8
    // test2 (F10) vs test4 (F6)
    test4.setDirection(Direction.UP);
    assertEquals(test2, proximitySelector.chooseElevator(elevatorRepository, 8));
  }
}

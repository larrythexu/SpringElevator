package io.github.larrythexu.ElevatorEmu.Elevator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class ElevatorTest {

  private Elevator elevator;
  private final int TEST_ID = 1;

  @BeforeEach
  void setUp() {
    elevator = new Elevator(1);
  }

  @Test
  void testConstruction() {
    assertEquals(TEST_ID, elevator.getId());
    assertEquals(Direction.NEUTRAL, elevator.getDirection());
    assertEquals(0, elevator.getCurrFloor());
    assertEquals(0, elevator.getUpList().size());
    assertEquals(0, elevator.getDownList().size());
  }

  @Test
  void testUpFloor() {
    elevator.addFloor(5);
    assertEquals(1, elevator.getUpList().size());
  }

  @Test
  void testDownFloor() {
    elevator.addFloor(-1);
    assertEquals(1, elevator.getDownList().size());
  }

  @Test
  void testUpdateUp() {
    elevator.addFloor(2);
    // Receive Command
    elevator.updateState();
    //        log.info("Floor: {}, upList: {}, dir: {}", elevator.getCurrFloor(),
    // elevator.getUpList(), elevator.getDirection());
    assertEquals(0, elevator.getCurrFloor());
    assertEquals(Direction.UP, elevator.getDirection());
    assertEquals(1, elevator.getUpList().size());

    // Move up
    elevator.updateState();
    assertEquals(1, elevator.getCurrFloor());
    assertEquals(Direction.UP, elevator.getDirection());
    assertEquals(1, elevator.getUpList().size());

    // Move up
    elevator.updateState();
    assertEquals(2, elevator.getCurrFloor());
    assertEquals(Direction.UP, elevator.getDirection());
    assertEquals(1, elevator.getUpList().size());

    // In target floor, remove floor
    elevator.updateState();
    assertEquals(2, elevator.getCurrFloor());
    assertEquals(Direction.NEUTRAL, elevator.getDirection());
    assertEquals(0, elevator.getUpList().size());
  }

  @Test
  void testUpdateDown() {
    elevator.setCurrFloor(2);

    elevator.addFloor(0);
    elevator.updateState();
    assertEquals(2, elevator.getCurrFloor());
    assertEquals(Direction.DOWN, elevator.getDirection());
    assertEquals(1, elevator.getDownList().size());

    elevator.updateState();
    assertEquals(1, elevator.getCurrFloor());
    assertEquals(Direction.DOWN, elevator.getDirection());
    assertEquals(1, elevator.getDownList().size());

    elevator.updateState();
    assertEquals(0, elevator.getCurrFloor());
    assertEquals(Direction.DOWN, elevator.getDirection());
    assertEquals(1, elevator.getDownList().size());

    elevator.updateState();
    assertEquals(0, elevator.getCurrFloor());
    assertEquals(Direction.NEUTRAL, elevator.getDirection());
    assertEquals(0, elevator.getDownList().size());
  }

  @Test
  void testUpdateMix() {
    elevator.setCurrFloor(5);

    elevator.addFloor(7);
    elevator.updateState(); // Set new direction
    assertEquals(5, elevator.getCurrFloor());
    assertEquals(Direction.UP, elevator.getDirection());
    assertEquals(1, elevator.getUpList().size());

    elevator.addFloor(1);
    elevator.updateState();
    assertEquals(6, elevator.getCurrFloor());
    assertEquals(Direction.UP, elevator.getDirection());
    assertEquals(7, elevator.getUpList().peek());
    assertEquals(1, elevator.getDownList().peek());

    elevator.updateState(); // Reach floor
    elevator.updateState(); // Remove target floor, switch directions
    assertEquals(0, elevator.getUpList().size());
    assertEquals(1, elevator.getDownList().size());
    assertEquals(7, elevator.getCurrFloor());
    assertEquals(Direction.DOWN, elevator.getDirection());

    elevator.updateState();
    assertEquals(6, elevator.getCurrFloor());
  }

  @Test
  void testAddFloorUp() {
    elevator.addFloor(5);
    assertEquals(5, elevator.getUpList().peek());

    elevator.addFloor(3);
    assertEquals(3, elevator.getUpList().peek());

    elevator.addFloor(11);
    assertEquals(3, elevator.getUpList().peek());

    elevator.addFloor(-1);
    assertEquals(3, elevator.getUpList().peek());
    assertEquals(-1, elevator.getDownList().peek());
  }

  @Test
  void testAddFloorDown() {
    elevator.setCurrFloor(5);

    elevator.addFloor(1);
    assertEquals(1, elevator.getDownList().peek());

    elevator.addFloor(3);
    assertEquals(3, elevator.getDownList().peek());

    elevator.addFloor(0);
    assertEquals(3, elevator.getDownList().peek());

    elevator.addFloor(10);
    assertEquals(3, elevator.getDownList().peek());
    assertEquals(10, elevator.getUpList().peek());
  }

  @Test
  void testAddFloorInterrupt() {
    elevator.addFloor(2);
    elevator.updateState(); // Set direction
    elevator.addFloor(1);
    elevator.updateState(); // Moves to 1

    elevator.updateState(); // Arrives at 1, removes floor and selects next target
    assertEquals(2, elevator.getUpList().peek());

    // Testing adding on same floor
    elevator.addFloor(1);
    assertEquals(2, elevator.getUpList().peek());
  }
}

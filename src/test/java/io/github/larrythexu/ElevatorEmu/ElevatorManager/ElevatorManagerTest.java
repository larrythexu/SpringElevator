package io.github.larrythexu.ElevatorEmu.ElevatorManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.ElevatorManager.Selector.SelectorStrategy;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;
import io.github.larrythexu.ElevatorEmu.Exceptions.ElevatorNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ElevatorManagerTest {

  private ElevatorManager elevatorManager;

  @Mock private ElevatorRepository elevatorRepository;

  @Mock private SelectorStrategy Strategy1;

  @Mock private SelectorStrategy Strategy2;

  @Mock private Elevator elevator1;

  @Mock private Elevator elevator2;

  int TEST_FLOOR = 1;
  int TEST_ID_1 = 1;
  int TEST_ID_2 = 2;
  String STRATEGY_1 = "simpleselector";
  String STRATEGY_2 = "complexselector";

  @BeforeEach
  void setUp() {
    when(Strategy1.name()).thenReturn(STRATEGY_1);
    when(Strategy2.name()).thenReturn(STRATEGY_2);

    List<SelectorStrategy> strategies = List.of(Strategy1, Strategy2);
    elevatorManager = new ElevatorManager(elevatorRepository, strategies);
    elevatorManager.init();
  }

  @Test
  void testConstruction() {
    assertEquals(Strategy1, elevatorManager.getActiveStrategy());
    assertEquals(elevatorRepository, elevatorManager.getElevatorRepository());
  }

  @Test
  void testSetSelectionStrategy() {
    elevatorManager.setSelectionStrategy(STRATEGY_2);
    assertEquals(Strategy2, elevatorManager.getActiveStrategy());

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          elevatorManager.setSelectionStrategy("PEWGFSelector");
        });
  }

  @Test
  void testAddElevator() {
    ArgumentCaptor<Elevator> elevatorCaptor = ArgumentCaptor.forClass(Elevator.class);
    // Test standard incrementation
    elevatorManager.addElevator();
    elevatorManager.addElevator();
    verify(elevatorRepository, times(2)).addElevator(elevatorCaptor.capture());

    List<Elevator> capturedElevator = elevatorCaptor.getAllValues();
    assertEquals(TEST_ID_1, capturedElevator.get(0).getId());
    assertEquals(TEST_ID_2, capturedElevator.get(1).getId());
  }

  @Test
  void testAddElevatorCounter() {
    ArgumentCaptor<Elevator> elevatorCaptor = ArgumentCaptor.forClass(Elevator.class);
    when(elevatorRepository.getElevator(TEST_ID_1)).thenReturn(Optional.of(elevator1));

    // Test incrementation after deletion (Delete ID_1, keep ID_2, ID_3)
    elevatorManager.addElevator();
    elevatorManager.addElevator();
    elevatorManager.removeElevator(TEST_ID_1);
    elevatorManager.addElevator();
    // Test incrementation after reset
    elevatorManager.resetElevators();
    elevatorManager.addElevator();
    verify(elevatorRepository, times(4)).addElevator(elevatorCaptor.capture());

    List<Elevator> capturedElevator = elevatorCaptor.getAllValues();
    assertEquals(3, capturedElevator.get(2).getId());
    assertEquals(TEST_ID_1, capturedElevator.get(3).getId());
  }

  @Test
  void testGetElevator() {
    when(elevatorRepository.getElevator(TEST_ID_2)).thenReturn(Optional.of(elevator2));
    elevatorManager.getElevator(TEST_ID_2);
    verify(elevatorRepository).getElevator(TEST_ID_2);
  }

  @Test
  void testGetElevatorNotFound() {
    when(elevatorRepository.getElevator(TEST_ID_2)).thenReturn(Optional.empty());
    assertThrows(
        ElevatorNotFoundException.class,
        () -> {
          elevatorManager.getElevator(TEST_ID_2);
        });
  }

  @Test
  void testRemoveElevator() {
    when(elevatorRepository.getElevator(TEST_ID_2)).thenReturn(Optional.of(elevator2));
    elevatorManager.removeElevator(TEST_ID_2);
    verify(elevatorRepository).removeElevator(elevator2);
  }

  @Test
  void testReset() {
    elevatorManager.resetElevators();
    verify(elevatorRepository).clear();
  }

  @Test
  void testStep() {
    List<Elevator> ELEVATOR_LIST = List.of(elevator1, elevator2);
    when(elevatorRepository.getAllElevators()).thenReturn(ELEVATOR_LIST);

    List<Elevator> returnList = elevatorManager.stepElevators();

    verify(elevator1).updateState();
    verify(elevator2).updateState();
    assertEquals(ELEVATOR_LIST, returnList);

    elevatorManager.stepElevators();
    verify(elevator1, times(2)).updateState();
    verify(elevator2, times(2)).updateState();
    assertEquals(ELEVATOR_LIST, returnList);
  }

  @Test
  void testHandleFloor() {
    when(Strategy1.chooseElevator(elevatorRepository, TEST_FLOOR)).thenReturn(elevator1);

    Elevator chosen = elevatorManager.handleFloorRequest(TEST_FLOOR);
    verify(elevator1).addFloor(TEST_FLOOR);
    assertEquals(elevator1, chosen);
  }
}

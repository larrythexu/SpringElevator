package io.github.larrythexu.ElevatorEmu.ElevatorManager.Selector;

import static org.mockito.Mockito.when;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.ElevatorRepository.ElevatorRepository;
import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AbstractSelectorTest {
  @Mock protected ElevatorRepository elevatorRepository;

  protected Elevator test1 = new Elevator(1);
  protected Elevator test2 = new Elevator(2);
  protected Elevator test3 = new Elevator(3);
  protected Elevator test4 = new Elevator(4);
  protected Elevator test5 = new Elevator(5);

  @BeforeEach
  void setUp() {
    test1.setDirection(Direction.NEUTRAL);
    test1.setCurrFloor(0);

    test2.setDirection(Direction.NEUTRAL);
    test2.setCurrFloor(10);

    test3.setDirection(Direction.UP);
    test3.setCurrFloor(4);

    test4.setDirection(Direction.DOWN);
    test4.setCurrFloor(6);

    test5.setCurrFloor(5);

    when(elevatorRepository.getAllElevators())
        .thenReturn(List.of(test1, test2, test3, test4, test5));
  }
}

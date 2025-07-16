package io.github.larrythexu.ElevatorEmu.Manager;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import io.github.larrythexu.ElevatorEmu.Manager.Selector.SelectorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class) // Hooks up mockito and init mocks
public class ElevatorManagerTest {

    private ElevatorManager elevatorManager;

    @Mock
    private SelectorStrategy Strategy1;

    @Mock
    private SelectorStrategy Strategy2;

    @Mock
    private Elevator elevator1;

    @Mock
    private Elevator elevator2;

    @BeforeEach
    void setUp() {
        Map<String, SelectorStrategy> strategies = Map.of(
                "SimpleSelector", Strategy1,
                "ComplexSelector", Strategy2
        );

        elevatorManager = new ElevatorManager(strategies);
        elevatorManager.init();
    }

    @Test
    void testConstruction() {
        assertEquals(Strategy1, elevatorManager.getActiveStrategy());
        assertEquals(1, elevatorManager.getElevatorList().size());
    }

    @Test
    void testSetSelectionStrategy() {
        elevatorManager.setSelectionStrategy("ComplexSelector");
        assertEquals(Strategy2, elevatorManager.getActiveStrategy());

        assertThrows(IllegalArgumentException.class, () -> {
            elevatorManager.setSelectionStrategy("PEWGFSelector");
        });
    }

    @Test
    void testAddElevator() {
        Elevator newElevator = elevatorManager.addElevator();
        assertEquals(2, newElevator.getId());
        assertEquals(2, elevatorManager.getElevatorList().size());
    }

}

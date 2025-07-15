package io.github.larrythexu.ElevatorEmu.Elevator;

import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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


    // TODO: more unit tests
}

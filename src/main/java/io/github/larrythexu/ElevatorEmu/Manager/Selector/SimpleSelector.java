package io.github.larrythexu.ElevatorEmu.Manager.Selector;

import io.github.larrythexu.ElevatorEmu.Elevator.Elevator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleSelector implements SelectorStrategy {

    // Simple selector has basic logic.
    // It just chooses the next elevator in the list
    private int counter = 0;

    @Override
    public Elevator chooseElevator(List<Elevator> elevatorList, int requestFloor) {
        // Reset counter back to beginning if end of list
        if (counter >= elevatorList.size()) {
            counter = 0;
        }

        return elevatorList.get(counter++);
    }
}

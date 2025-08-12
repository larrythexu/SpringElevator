package io.github.larrythexu.ElevatorEmu.Elevator;

import io.github.larrythexu.ElevatorEmu.Enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ElevatorStateDTO {
  private int id;
  private int currFloor;
  private Direction direction;
  private List<Integer> destinationFloors;
}

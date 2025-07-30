package io.github.larrythexu.ElevatorEmu.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElevatorNotFoundException extends RuntimeException {
  public ElevatorNotFoundException(int id) {
    super("Elevator not found: " + id);
  }
}

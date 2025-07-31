package io.github.larrythexu.ElevatorEmu.ElevatorEmulator;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@Getter
public class ElevatorEmulatorConfig {

  @Bean
  public ScheduledExecutorService elevatorScheduler() {
    return Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r);
      t.setName("elevator-scheduler");
      return t;
    });
  }
}

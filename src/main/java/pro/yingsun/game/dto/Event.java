package pro.yingsun.game.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import pro.yingsun.game.enumeration.EventEntity;

@Jacksonized
@Builder
@Value
public class Event {

  EventEntity entity;
  String description;
  Instant createdAt;
}

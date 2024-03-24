package pro.yingsun.game.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class GameCreation {

  @Min(1)
  @Max(10)
  int shoeSize;
}

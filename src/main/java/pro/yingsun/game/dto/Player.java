package pro.yingsun.game.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class Player implements Serializable {

  private String playerId;
  @NotEmpty
  private String playerName;
  private List<Card> cards;
}

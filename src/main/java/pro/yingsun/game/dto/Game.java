package pro.yingsun.game.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class Game implements Serializable {

  private String gameId;
  private List<Player> players;
  private LinkedList<Card> shoe;
}

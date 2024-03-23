package pro.yingsun.game.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

  @GetMapping("{gameId}")
  public String getGame(
      @PathVariable("gameId") String gameId) {

    return """
        {"gameId":1}
        """;
  }
}

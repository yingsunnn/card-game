package pro.yingsun.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pro.yingsun.game.dto.ErrorResponse;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.GameCreation;
import pro.yingsun.game.service.GameService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

  private final GameService gameService;

  @PostMapping
  @Operation(
      tags = "Game",
      summary = "Create a new game",
      description = "Create a game with shoe size. The default value of shoe size is 1.",
      responses = {
          @ApiResponse(
              description = "The game is created successfully",
              responseCode = "200",
              content = @Content(schema = @Schema(implementation = Game.class))
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public Game createGame(
      @RequestBody GameCreation gameCreation
  ) {
    return this.gameService.createGame(gameCreation);
  }

  @DeleteMapping("{gameId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      tags = "Game",
      summary = "Delete a game",
      description = "Delete a game by game Id.",
      responses = {
          @ApiResponse(
              description = "The game is deleted successfully",
              responseCode = "204"
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public void deleteGame(
      @PathVariable("gameId") String gameId
  ) {
    this.gameService.deleteGame(gameId);
  }
}

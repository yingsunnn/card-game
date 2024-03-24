package pro.yingsun.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yingsun.game.dto.ErrorResponse;
import pro.yingsun.game.dto.Player;
import pro.yingsun.game.dto.PlayerSummary;
import pro.yingsun.game.service.PlayerService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "games", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

  private final PlayerService playerService;

  @PostMapping(value = "{gameId}/players", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      tags = "Player",
      summary = "Add a player to the game by game Id",
      responses = {
          @ApiResponse(
              description = "Player is added successfully. Returns all players of the game",
              responseCode = "200",
              content = @Content(schema = @Schema(implementation = Player.class))
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public Player addPlayerToGame(
      @PathVariable("gameId") String gameId,
      @RequestBody Player player) {
    return this.playerService.addPlayerToGame(gameId, player);
  }

  @DeleteMapping("{gameId}/players/{playerId}")
  @Operation(
      tags = "Player",
      summary = "Delete a player from the game by player Id",
      responses = {
          @ApiResponse(
              description = "Player is deleted successfully. Returns all players of the game",
              responseCode = "200",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = Player.class)))
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public List<Player> deletePlayerFromGame(
      @PathVariable("gameId") String gameId,
      @PathVariable("playerId") String playerId
  ) {
    return this.playerService.deletePlayerFromGame(gameId, playerId);
  }

  @GetMapping("{gameId}/players")
  @Operation(
      tags = "Player",
      summary = "Get all players of the game",
      responses = {
          @ApiResponse(
              description = "Get players successfully. Returns all players' summary of the game",
              responseCode = "200",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerSummary.class)))
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public List<PlayerSummary> getPlayers(
      @PathVariable("gameId") String gameId
  ) {
    return this.playerService.getPlayers(gameId);
  }
}

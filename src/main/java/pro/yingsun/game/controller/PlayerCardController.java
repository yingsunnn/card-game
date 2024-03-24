package pro.yingsun.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yingsun.game.dto.Card;
import pro.yingsun.game.dto.ErrorResponse;
import pro.yingsun.game.dto.PlayerSummary;
import pro.yingsun.game.service.PlayerCardService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "games", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerCardController {

  private final PlayerCardService playerCardService;

  @GetMapping("{gameId}/players/{playerId}/cards")
  @Operation(
      tags = "Player Card",
      summary = "Get all player's cards",
      responses = {
          @ApiResponse(
              description = "Get player's card successfully. Returns all player's cards",
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
  public List<Card> getPlayerCards(
      @PathVariable("gameId") String gameId,
      @PathVariable("playerId") String playerId
  ) {
    return this.playerCardService.getPlayerCards(gameId, playerId);
  }

  @PostMapping("{gameId}/players/{playerId}/cards")
  @Operation(
      tags = "Player Card",
      summary = "Deal a card to the player",
      responses = {
          @ApiResponse(
              description = "Dealt a card successfully. Returns the card",
              responseCode = "200",
              content = @Content(schema = @Schema(implementation = Card.class))
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public Card dealCardToPlayer (
      @PathVariable("gameId") String gameId,
      @PathVariable("playerId") String playerId
  ) {
    return this.playerCardService.dealCardToPlayer(gameId, playerId);
  }
}

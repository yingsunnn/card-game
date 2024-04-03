package pro.yingsun.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pro.yingsun.game.dto.ErrorResponse;
import pro.yingsun.game.dto.ShoeSummary;
import pro.yingsun.game.service.ShoeService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "games", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoeController {

  private final ShoeService shoeService;

  @PostMapping("{gameId}/shoe/increment/{incrementSize:\\d+}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      tags = "Shoe",
      summary = "Add decks to the shoe of the game",
      responses = {
          @ApiResponse(
              description = "Decks is added successfully",
              responseCode = "204"
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public void addDecksToShoe(
      @PathVariable("gameId") String gameId,
      @PathVariable("incrementSize") int incrementSize
  ) {
    this.shoeService.addDecksToShoe(gameId, incrementSize);
  }

  @PostMapping("{gameId}/shoe/shuffle")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      tags = "Shoe",
      summary = "Shuffle the shoe of the game by game Id",
      responses = {
          @ApiResponse(
              description = "Shuffled successfully.",
              responseCode = "204"
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public void shuffleShoe(
      @PathVariable("gameId") String gameId
  ) {
    this.shoeService.shuffleShoe(gameId);
  }

  @GetMapping("{gameId}/shoe")
  @Operation(
      tags = "Shoe",
      summary = "Get the count of how many cards per suit are left undealt in the game deck",
      responses = {
          @ApiResponse(
              description = "Get cards successfully. Returns all undealt cards summary",
              responseCode = "200",
              content = @Content(schema = @Schema(implementation = ShoeSummary.class))
          ),
          @ApiResponse(
              description = "Invalid request",
              responseCode = "400",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
      }
  )
  public ShoeSummary getShoeSummary(
      @PathVariable("gameId") String gameId
  ) {
    return this.shoeService.getShoeSummary(gameId);
  }
}

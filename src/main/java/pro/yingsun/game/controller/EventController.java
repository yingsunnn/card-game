package pro.yingsun.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.yingsun.game.dto.ErrorResponse;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.enumeration.EventEntity;
import pro.yingsun.game.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

  private final EventService eventService;

  @GetMapping
  @Operation(
      tags = "Event",
      summary = "Get events",
      responses = {
          @ApiResponse(
              description = "Events retrieved successfully",
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
  public List<Event> getEvents (
      @RequestParam(value = "entity", required = false) EventEntity entity,
      @RequestParam(value = "entityId", required = false) String entityId
  ) {
    return this.eventService.getEvents(entity, entityId);
  }
}

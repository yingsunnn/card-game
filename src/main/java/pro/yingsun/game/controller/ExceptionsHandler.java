package pro.yingsun.game.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.yingsun.game.dto.ErrorResponse;
import pro.yingsun.game.exception.DataNotFoundException;
import pro.yingsun.game.exception.DuplicateDataException;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(IllegalMonitorStateException.class)
  public ErrorResponse handleIllegalMonitorStateException(
      HttpServletResponse response,
      IllegalMonitorStateException e
  ) {
    response.setStatus(HttpStatus.TOO_EARLY.value());
    return buildErrorResponse(e, response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorResponse handleIllegalArgumentException(
      HttpServletResponse response,
      IllegalArgumentException e
  ) {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    return buildErrorResponse(e, response);
  }

  @ExceptionHandler(DuplicateDataException.class)
  public ErrorResponse handleDuplicateDataException(
      HttpServletResponse response,
      DuplicateDataException e
  ) {
    response.setStatus(HttpStatus.CONFLICT.value());
    return buildErrorResponse(e, response);
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ErrorResponse handleDataNotFoundException(
      HttpServletResponse response,
      DataNotFoundException e
  ) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return buildErrorResponse(e, response);
  }

  @ExceptionHandler(Exception.class)
  public ErrorResponse handleUnknownException(
      HttpServletResponse response,
      Exception e
  ) {
    log.error("Error message: {}", e.getMessage(), e);

    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return buildErrorResponse(e, response);
  }

  private static ErrorResponse buildErrorResponse(Exception e, HttpServletResponse response) {
    final String errorMessage = Optional.ofNullable(ExceptionUtils.getRootCause(e))
        .map(Throwable::getMessage)
        .orElse(e.getMessage());

    return ErrorResponse.builder()
        .status(response.getStatus())
        .message(errorMessage)
        .build();
  }
}

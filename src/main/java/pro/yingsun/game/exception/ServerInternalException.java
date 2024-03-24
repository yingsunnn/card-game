package pro.yingsun.game.exception;

public class ServerInternalException extends RuntimeException{

  public ServerInternalException(String message, Throwable throwable) {
    super(message, throwable);
  }
}

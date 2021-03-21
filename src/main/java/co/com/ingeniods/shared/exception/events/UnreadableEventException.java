package co.com.ingeniods.shared.exception.events;

import co.com.ingeniods.shared.exception.base.BusinessException;
import co.com.ingeniods.shared.exception.base.ExceptionCode;

public class UnreadableEventException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public UnreadableEventException(String message) {
    super(ExceptionCode.MALFORMED_MESSAGE, message);
  }

}

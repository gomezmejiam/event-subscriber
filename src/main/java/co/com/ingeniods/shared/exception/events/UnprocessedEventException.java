package co.com.ingeniods.shared.exception.events;

import co.com.ingeniods.shared.exception.base.BusinessException;
import co.com.ingeniods.shared.exception.base.ExceptionCode;

public class UnprocessedEventException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public UnprocessedEventException(String message) {
    super(ExceptionCode.UNPROCESSED_EVENT, message);
  }

}

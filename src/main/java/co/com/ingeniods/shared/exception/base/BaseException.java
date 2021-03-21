package co.com.ingeniods.shared.exception.base;

import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
  
  private static final long serialVersionUID = -824690113517284691L;
  private final ExceptionCode exceptionCode;
  private final String id = UUID.randomUUID().toString();
  
  public BaseException(ExceptionCode exceptionCode) {
    super(exceptionCode.getType());
    this.exceptionCode = exceptionCode;
  }
  
  public BaseException(ExceptionCode exceptionCode, String message) {
    super(message);
    this.exceptionCode = exceptionCode;
  }
  
  public BaseException(ExceptionCode exceptionCode, Throwable message) {
    super(message);
    this.exceptionCode = exceptionCode;
  }
  
  public ExceptionCode getExceptionCode() {
    return exceptionCode;
  }

}

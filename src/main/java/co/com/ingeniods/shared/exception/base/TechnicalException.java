package co.com.ingeniods.shared.exception.base;

public abstract class TechnicalException extends BaseException {

  private static final long serialVersionUID = -8946415577270136466L;
  
  public TechnicalException(ExceptionCode codigoException, Throwable message) {
    super(codigoException, message);
  }

  
}

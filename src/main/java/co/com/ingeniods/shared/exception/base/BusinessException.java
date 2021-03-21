package co.com.ingeniods.shared.exception.base;

public abstract class BusinessException extends BaseException {

  private static final long serialVersionUID = 1L;
  
  public BusinessException(ExceptionCode codigoException, String message) {
    super(codigoException, message);
  }
  
}

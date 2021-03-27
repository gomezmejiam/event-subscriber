package co.com.ingeniods.shared.validator.domain;

public enum ValidationErrorCode {
  VALUE_CANT_BE_ZERO("VBE-001","VALUE_CANT_BE_ZERO"), 
  ENTITY_ALREADY_REGISTRED("VBE-002","ENTITY_ALREADY_REGISTRED");
  
  private String code;
  private String type;

  private ValidationErrorCode(String code, String type) {
    this.code = code;
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public String getType() {
    return type;
  }

}

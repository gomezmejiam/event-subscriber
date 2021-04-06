package co.com.ingeniods.shared.exception.base;

public enum ExceptionCode {
    UNCONTROLLED("BAS-001", "EXCEPCION_NO_CONTROLADA"),
    BUSINESS("BAS-002", "EXCEPCION_NEGOCIO"),
    TECHNICAL("BAS-003", "EXCEPCION_TECNICA"),
    EVENT_TYPE("EVT-004","NULL_OR_EMPTY_EVENT_TYPE"),
    INACTIVE_FUNCTION("EVT-005","INACTIVE_FUNCTION"), 
    MALFORMED_MESSAGE("EVT-006","MALFORMED_MESSAGE_RECEIVED"), 
    REQUIRED_ENTITY_PROPERTY("DMN-007","NULL_OR_EMPTY_REQUIRED_ENTITY_PROPERTY"),
    VALIDATION_EXCEPTION("BUS-008","VALIDATION_EXCEPTION"), 
    UNPROCESSED_EVENT("EVT-009","VALIDATION_EXCEPTION");
  
	private String code;
	private String type;

	ExceptionCode(String code, String type) {
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

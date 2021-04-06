package co.com.ingeniods.shared.infrastructure.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class ErrorResponse {
    private String message;
    private String errorCode;
    private List<String> details;
}

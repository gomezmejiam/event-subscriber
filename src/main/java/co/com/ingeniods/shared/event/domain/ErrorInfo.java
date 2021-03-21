package co.com.ingeniods.shared.event.domain;

import lombok.Value;

@Value
public class ErrorInfo{
    private String type;
    private String description;
    private String code;
}

package co.com.ingeniods.shared.infrastructure.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventResponse {

  private String id;
  private String data;
  private String error;
  
}

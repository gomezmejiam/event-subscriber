package co.com.ingeniods.shared.infrastructure.api.resource;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventResource {
  private String id;
  private String data;
  private HashMap<String, String> headers;
}

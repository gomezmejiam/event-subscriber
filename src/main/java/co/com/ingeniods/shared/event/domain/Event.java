package co.com.ingeniods.shared.event.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;

import co.com.ingeniods.shared.exception.events.InvalidEventException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event<T> {

  private static final String EVENT_TYPE = "EVENT_TYPE";
  private static final String CREATION_DATE = "CREATION_DATE";

  private static DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("America/Bogota"));

  private HashMap<String, String> headers;
  private T data;
  private final String id;

  public Event(String type, T data) {
    this(type, new HashMap<String, String>(), data);
  }

  public Event(String type, HashMap<String, String> headers, T data) {
    if (Optional.ofNullable(type).orElse("").isEmpty()) {
      throw new InvalidEventException(String.valueOf(type));
    }
    this.id = UUID.randomUUID().toString();
    this.headers = headers;
    this.data = data;
    if (this.headers == null) {
      this.headers = new HashMap<>();
    }
    this.headers.put(CREATION_DATE, now());
    this.headers.put(EVENT_TYPE, type);
  }
  
  private String now() {
    return FORMATTER.format(LocalDateTime.now());
  }

  public String getType() {
    return this.headers.get(EVENT_TYPE);
  }

  public void addHeader(String name, String value) {
    this.headers.putIfAbsent(name, value);
  }

  public void addHeader(Entry<String, String> header) {
    this.headers.putIfAbsent(header.getKey(), header.getValue());
  }

  public void addHeaders(Set<Entry<String, String>> entrySet) {
    entrySet.forEach(this::addHeader);
  }

}

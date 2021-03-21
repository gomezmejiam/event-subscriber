package co.com.ingeniods.shared.infrastructure.serializer;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ValueObjectDeserializer<T> extends StdDeserializer<T> {
	 
	private static final long serialVersionUID = 7295446569319494483L;
	private final Function<String, T> constructor;
 
	public ValueObjectDeserializer(Class<T> vc, Function<String, T> constructor) {
		super(vc);
		this.constructor = constructor;
	}
 
	@Override
	public T deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JsonProcessingException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
 
		String value = node.asText();
 
		return constructor.apply(value);
	}
 
}
package co.com.ingeniods.shared.infrastructure.serializer;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public abstract class EntityDeserializer<T> extends StdDeserializer<T> {

	private static final long serialVersionUID = -6750077145049934399L;
	
	private transient JsonParser jsonParser;

	protected EntityDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
		this.jsonParser=jp;
		return process(jp, ctx);
	}
	
	public abstract T process(JsonParser jp, DeserializationContext ctx)  throws IOException;

	protected Optional<JsonNode> getNodeOptional(JsonNode node, String name) {
		return Optional.ofNullable(node.get(name));
	}

	protected JsonNode getNode(JsonNode node, String name) throws JsonMappingException {
		return getNodeOptional(node, name)
				.orElseThrow(() -> new JsonMappingException(this.jsonParser, "Missing '"+name+"' property"));
	}
	
	protected <V> V getNodeAs(JsonNode node, String name, Class<V> valueType) throws IOException {
		return getNode(node, name).traverse(jsonParser.getCodec()).readValueAs(valueType);
	}

}
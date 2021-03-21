package co.com.ingeniods.shared.infrastructure.serializer;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ValueObjectSerializer<T> extends StdSerializer<T> {

	private static final long serialVersionUID = 6257272957048795557L;

	private final Function<T, String> valueExtractor;

	public ValueObjectSerializer(Class<T> vc, Function<T, String> valueExtractor) {
		super(vc);
		this.valueExtractor = valueExtractor;
	}

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(valueExtractor.apply(value));
	}

}
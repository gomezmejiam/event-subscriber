package co.com.ingeniods.config.jackson;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.Serializers;

import co.com.ingeniods.humantalent.domain.model.DocumentType;
import co.com.ingeniods.shared.infrastructure.serializer.ValueObjectDeserializer;
import co.com.ingeniods.shared.infrastructure.serializer.ValueObjectSerializer;
import co.com.ingeniods.shared.modules.domain.DomainEnum;

public class CustomJacksonModule extends Module {

	@SuppressWarnings("rawtypes")
	private final List<Class> ENUMS = Arrays.asList(DocumentType.class);

	@Override
	public String getModuleName() {
		return "Custom-Jackson-IngenioDS-Module";
	}

	@Override
	public Version version() {
		return new Version(1, 0, 0, "outsource", "co.com.ingeniods", "co.com.ingeniods.outsource");
	}

	@Override
	public void setupModule(SetupContext context) {
		context.addSerializers(createSerializers(ENUMS));
		context.addDeserializers(createDeserializers(ENUMS));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Serializers createSerializers(List<Class> enums) {
		SimpleSerializers serializers = new SimpleSerializers();
		enums.stream().forEach(c -> serializers.addSerializer(enumSerializer(c)));
		return serializers;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Deserializers createDeserializers(List<Class> enums) {
		SimpleDeserializers deserializers = new SimpleDeserializers();
		enums.stream().forEach(c -> deserializers.addDeserializer(c, enumDeserializer(c)));
		return deserializers;
	}

	private <T extends Enum<?>> ValueObjectSerializer<T> enumSerializer(Class<T> enumClass) {
		return new ValueObjectSerializer<>(enumClass, value -> value.name());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends DomainEnum> ValueObjectDeserializer<T> enumDeserializer(Class<T> enumClass) {
		return new ValueObjectDeserializer<T>(enumClass, v -> {
			return Arrays.asList(enumClass.getEnumConstants()).stream().filter(e -> e.isEnum(v)).findFirst()
					.orElse(null);
		});
	}

}

package co.com.ingeniods.humantalent.infrestructure.repository.assembler;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import co.com.ingeniods.humantalent.domain.model.DocumentType;

@Converter(autoApply = true)
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {

  @Override
  public String convertToDatabaseColumn(DocumentType tipo) {
    if (tipo == null) {
      return null;
  }
  return tipo.name();
  }

  @Override
  public DocumentType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
  }

  return Stream.of(DocumentType.values()).filter(c -> c.name().equals(code)).findFirst()
          .orElseThrow(IllegalArgumentException::new);
  }

}

package medical.education.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ClassStatusEnumConverter implements AttributeConverter<ClassStatusEnum, Short> {

  @Override
  public Short convertToDatabaseColumn(ClassStatusEnum attribute) {
    if (attribute != null) {
      return attribute.getValue();
    }
    return null;
  }

  @Override
  public ClassStatusEnum convertToEntityAttribute(Short dbData) {
    if (dbData != null) {

      for (ClassStatusEnum e : ClassStatusEnum.values()) {
        if (e.getValue() == dbData) {
          return e;
        }
      }
    }
    return null;
  }
}

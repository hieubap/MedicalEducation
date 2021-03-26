package medical.education.enums;

import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Short> {

  @Override
  public Short convertToDatabaseColumn(Gender gender) {
    if (gender != null) {
      return gender.getValue();
    }
    return null;
  }

  @Override
  public Gender convertToEntityAttribute(Short aShort) {
    if (aShort != null) {
      Gender gender = Arrays.stream(Gender.values()).filter(e -> e.getValue() == aShort).findFirst()
          .get();
      return gender;
    }
    return null;
  }
}
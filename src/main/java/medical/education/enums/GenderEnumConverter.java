package medical.education.enums;

import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderEnumConverter implements AttributeConverter<GenderEnum, Short> {

  @Override
  public Short convertToDatabaseColumn(GenderEnum gender) {
    if (gender != null) {
      return gender.getValue();
    }
    return null;
  }

  @Override
  public GenderEnum convertToEntityAttribute(Short aShort) {
    if (aShort != null) {
      GenderEnum gender = Arrays.stream(GenderEnum.values()).filter(e -> e.getValue() == aShort).findFirst()
          .get();
      return gender;
    }
    return null;
  }
}
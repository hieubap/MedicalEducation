package medical.education.research.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum GenderEnum {
  MALE((short) 1),
  FEMALE((short) 2),
  OTHER((short) 3),
  UNKNOWN((short) 4);

  private Short value;

  GenderEnum(Short value) {
    this.value = value;
  }

  public Short getValue() {
    return value;
  }

  public static String getString(Short value){
    switch (value){
      case 1: return "male";
      case 2: return "female";
      case 3: return "other";
      case 4: return "unknown";
    }
    return null;
  }
}

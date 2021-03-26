package medical.education.research.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public enum ContactPointEnum {
  PHONE((short) 1),
  FAX((short) 2),
  EMAIL((short) 3),
  PAGER((short) 4),
  URL((short) 5),
  SMS((short) 6),
  OTHER((short) 7);

  private Short value;

  ContactPointEnum(Short value) {
    this.value = value;
  }

  public static String getString(Short value){
    switch (value){
      case 1: return "phone";
      case 2: return "fax";
      case 3: return "email";
      case 4: return "pager";
      case 5: return "url";
      case 6: return "sms";
      case 7: return "other";
    }
    return null;
  }
}

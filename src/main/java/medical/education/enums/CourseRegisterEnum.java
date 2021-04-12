package medical.education.enums;

public enum CourseRegisterEnum {
  WAIT_APPROVE((short) 0),
  APPROVED((short) 1),
  NOT_APPROVE((short) 2),
  DONE((short) 3);

  private Short value;

  CourseRegisterEnum(Short value) {
    this.value = value;
  }

  public Short getValue(){
    return value;
  }

  public static String getName(Short value) {
    if (value == null) {
      return null;
    }
    switch (value) {
      case 0:
        return "WAIT_APPROVE";
      case 1:
        return "APPROVED";
      case 2:
        return "NOT_APPROVE";
      case 3:
        return "DONE";
    }
    return null;
  }
}

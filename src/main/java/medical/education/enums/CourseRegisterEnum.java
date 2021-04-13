package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum CourseRegisterEnum implements IEnum {
  WAIT_APPROVE((short) 0),
  APPROVED((short) 1),
  NOT_APPROVE((short) 2),
  DONE((short) 3);

  private Short value;

  CourseRegisterEnum(Short value) {
    this.value = value;
  }

  public short getValue() {
    return value;
  }

  public void setValue(Short value) {
    this.value = value;
  }
}

package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum RoleEnum implements IEnum {
  ADMIN((short) 1),
  TEACHER((short) 2),
  STUDENT((short) 3);

  public short value;

  RoleEnum(short value) {
    this.value = value;
  }

  @Override
  public short getValue() {
    return value;
  }

  public void setValue(short value) {
    this.value = value;
  }
}

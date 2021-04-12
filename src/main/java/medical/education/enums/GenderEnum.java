package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum GenderEnum implements IEnum {

  NAM((short) 0),
  NU((short) 1);

  private short value;

  private GenderEnum(short value) {
    this.value = value;
  }

  public short getValue() {
    return value;
  }

  public void setValue(short value) {
    this.value = value;
  }

}

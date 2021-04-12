package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum ClassRegisterEnum implements IEnum {

  THANH_CONG((short) 0),
  LOP_BI_HUY((short) 1);

  private short value;

  ClassRegisterEnum(short value) {
    this.value = value;
  }

  public short getValue() {
    return value;
  }

  public void setValue(short value) {
    this.value = value;
  }
}

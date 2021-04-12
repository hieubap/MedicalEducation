package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum NotificationEnum implements IEnum {

  CHUA_DOC((short) 1),

  DA_DOC((short) 2);
  private short value;

  NotificationEnum(short value) {
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

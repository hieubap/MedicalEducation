package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum CourseRegisterEnum implements IEnum {

  WAIT_APPROVE((short) 0, "Chờ xác nhận"),
  APPROVED((short) 1, "Đã xác nhận"),
  NOT_APPROVE((short) 2, "Không được xác nhận"),
  DONE((short) 3, "Đã hoàn thành");

  private Short value;
  private String name;


  CourseRegisterEnum(Short value, String name) {
    this.value = value;
    this.name = name;
  }

  public short getValue() {
    return value;
  }

  public void setValue(Short value) {
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

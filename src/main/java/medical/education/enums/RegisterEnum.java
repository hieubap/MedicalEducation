package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum RegisterEnum implements IEnum {

  REGISTER_DONED((short) 3, "Đăng ký thành công"),
  STUDYING((short) 0, "Đang học"),
  DONED((short) 1, "Hoàn thành"),
  FAIL((short) 2, "Không hoàn thành");

  private Short value;
  private String name;


  RegisterEnum(Short value, String name) {
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

package medical.education.enums;

public enum GenderEnum {

  NAM((short) 0), NU((short) 1);

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

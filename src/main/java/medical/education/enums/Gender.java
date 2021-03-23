package medical.education.enums;

public enum Gender {

  NAM((short) 1), NU((short) 2);

  private short value;

  private Gender(short value) {
    this.value = value;
  }

  public short getValue() {
    return value;
  }

  public void setValue(short value) {
    this.value = value;
  }

}

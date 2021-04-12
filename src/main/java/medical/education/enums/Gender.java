package medical.education.enums;

public enum Gender {

  NAM((short) 0), NU((short) 1);

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

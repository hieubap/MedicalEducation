package medical.education.enums;

public enum ClassStatusEnum {

  CHO_DANG_KY_LOP((short) 1),
  DA_DU_SO_LUONG((short) 2),
  XAC_NHAN((short) 3),
  HUY_LOP((short) 4);

  private short value;

  ClassStatusEnum(short value) {
    this.value = value;
  }

  public short getValue() {
    return value;
  }

  public void setValue(short value) {
    this.value = value;
  }

}

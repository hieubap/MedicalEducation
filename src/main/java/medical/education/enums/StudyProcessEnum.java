package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum StudyProcessEnum implements IEnum {

  TOT((short) 1),
  KHA((short) 2),
  TRUNG_BINH((short) 3),
  TRUOT((short) 4),
  DANG_HOC((short) 5),
  DINH_CHI((short) 6);

  public short value;


  StudyProcessEnum(short value) {
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

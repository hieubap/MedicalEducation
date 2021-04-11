  package medical.education.enums;

  import com.fasterxml.jackson.annotation.JsonCreator;
  import com.fasterxml.jackson.annotation.JsonValue;
  import java.util.stream.Stream;

  public enum ClassStatusEnum {

    CHO_DANG_KY_LOP((short) 1),
    DA_DU_SO_LUONG((short) 2),
    XAC_NHAN((short) 3),
    HUY_LOP((short) 4);

    //  private Short value;
  //
  //  private ClassStatusEnum(Short value) {
  //    this.value = value;
  //  }
  //
  //  @JsonValue
  //  public Short getValue() {
  //    return value;
  //  }
    private short value;

    ClassStatusEnum(short value) {
      this.value = value;
    }

    @JsonValue
    public short getValue() {
      return value;
    }
    @JsonCreator
    public static ClassStatusEnum decode(final Short value) {
      return Stream.of(ClassStatusEnum.values()).filter(targetEnum -> targetEnum.getValue()==value).findFirst().orElse(null);
    }
  }

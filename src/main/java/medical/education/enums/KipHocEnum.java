package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum KipHocEnum implements IEnum {

    KIP_1((short) 0, "Kíp 1"),
    KIP_2((short) 1, "Kíp 2"),
    KIP_3((short) 2, "Kíp 3"),
    KIP_4((short) 3, "Kíp 4");

    private short value;
    private String name;

    KipHocEnum(short value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }
}

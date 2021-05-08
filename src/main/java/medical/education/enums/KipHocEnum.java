package medical.education.enums;

import spring.backend.library.enums.IEnum;

public enum KipHocEnum implements IEnum {
    KIP1((short) 1, "Kíp 1"),
    KIP2((short) 2, "Kíp 2"),
    KIP3((short) 3, "Kíp 3"),
    KIP4((short) 4, "Kíp 4");

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

    @Override
    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }
}

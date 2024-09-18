package edu.iuh.fit.backEnd.enums;

public enum EmloyeeStatus {
//    Employee có status: 1- đang làm việc, 0 - tạm nghỉ, -1 – nghỉ việc
    ACTIVE((byte) 1),
    REST((byte) 0),
    QUIT((byte) -1);

    private final byte value;

    EmloyeeStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}

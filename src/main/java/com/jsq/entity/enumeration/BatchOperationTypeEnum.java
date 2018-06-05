package com.jsq.entity.enumeration;

/**
 * @author baochao.chen@hand-china.com
 */
public enum BatchOperationTypeEnum implements SysEnum {
    BUDGETJOURNAL(1001),
    BUDGETRESERVEADJUST(1002),
    STUDENT(1003),
    BUDGETITEMMAPPING(1004);

    private int id;

    BatchOperationTypeEnum(int id) {
        this.id = id;
    }

    public static BatchOperationTypeEnum parse(int id) {
        for (BatchOperationTypeEnum batchOperationTypeEnum : BatchOperationTypeEnum.values()) {
            if (batchOperationTypeEnum.getID() == id) {
                return batchOperationTypeEnum;
            }
        }
        return null;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}

package com.jsq.entity.enumeration;

/**
 * @author baochao.chen@hand-china.com
 */
public enum TransactionStatus implements SysEnum {
    PARSING_FILE(1001), PROCESS_DATA(1002), DONE(1003), ERROR(1004), CANCELLED(1005);

    private int id;

    TransactionStatus(int id) {
        this.id = id;
    }

    public static TransactionStatus parse(int id) {
        for (TransactionStatus transactionStatus : TransactionStatus.values()) {
            if (transactionStatus.getID() == id) {
                return transactionStatus;
            }
        }
        return null;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}

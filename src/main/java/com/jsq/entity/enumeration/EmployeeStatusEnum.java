package com.jsq.entity.enumeration;

/**
 * @description:
 * @version: 1.0
 * @author: wenzhou.tang@hand-china.com
 * @date: 2017/8/22 17:41
 */
public enum EmployeeStatusEnum implements SysEnum {
    //正常
    NORMAL(1001),
    //待离职
    LEAVING(1002),
    //已离职
    LEAVED(1003);
    private Integer id;

    EmployeeStatusEnum(Integer id) {
        this.id = id;
    }

    public static EmployeeStatusEnum parse(Integer id) {
        for (EmployeeStatusEnum employeeStatusEnum : EmployeeStatusEnum.values()) {
            if (employeeStatusEnum.getID().equals(id)) {
                return employeeStatusEnum;
            }
        }
        return null;
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}

package com.jsq.entity.enumeration;

/**
 * @description:
 * @version: 1.0
 * @author: wenzhou.tang@hand-china.com
 * @date: 2017/8/22 17:41
 */
public enum UserLockedEnum implements SysEnum  {

    //未锁定
    UNLOCKED(2001),
    //锁定
    LOCKED(2002);

    private Integer id;

    UserLockedEnum(Integer id) {
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

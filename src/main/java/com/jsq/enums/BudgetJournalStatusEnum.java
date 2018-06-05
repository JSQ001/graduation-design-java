package com.jsq.enums;

/**
 * Created by 刘亮 on 2017/10/30.
 * 预算日记账状态：
     * NEW：新建
     * SUBMIT：提交审批
     * SUBMIT_RETURN  提交撤回
     * REJECT：拒绝
     * CHECKED:审批完成
     * CHECKING: 审批中
     * POSTED:复核(过账)
     * BACKLASH_SUBMIT:反冲提交
     * BACKLASH_CHECKED:反冲审核
 */
public enum BudgetJournalStatusEnum implements SysEnum {
    NEW(1001),
    SUBMIT(1002),
    SUBMIT_RETURN(1003),
    REJECT(1004),
    CHECKED(1005),
    POSTED(1006),
    BACKLASH_SUBMIT(1007),
    BACKLASH_CHECKED(1008),
    CHECKING(1009);
    private Integer id;
    BudgetJournalStatusEnum(Integer id){
        this.id = id;
    }

    public static BudgetJournalStatusEnum parse(Integer id) {
        for (BudgetJournalStatusEnum budgetJournalStatus : BudgetJournalStatusEnum.values()) {
            if (budgetJournalStatus.getID().equals(id)) {
                return budgetJournalStatus;
            }
        }
        return null;
    }


    @Override
    public Integer getID() {
        return this.id;
    }
}

package com.jsq.entity.enumeration;

/**
 * 预算日记账导入编码
 * Created by cbc on 17/11/17.
 */
public final class BudgetJournalImportCode {

    public final static int EXCEL_BASEROW = 1;  // 起始行

    public final static String IMPORT_TEMPLATE_PATH_PERIOD_YEAR = "/templates/BudgetJournalImport_period_year.xlsx";

    public final static String IMPORT_TEMPLATE_PATH_PERIOD_QUARTER="/templates/BudgetJournalImport_period_quarter.xlsx";

    public final static String IMPORT_TEMPLATE_PATH_PERIOD_MONTH="/templates/BudgetJournalImport_period_month.xlsx";

    public final static String ERROR_TEMPLATE_PATH = "/templates/BudgetJournalError.xlsx";

    public final static int COMPANY_CODE = 0;   // 公司代码

    public final static int DEPARTMENT_CODE = 1;  // 部门代码

    public final static int EMP_CODE = 2; //员工工号

    public final static int ITEM_CODE = 3; // 预算项目代码

    public final static int PERIOD_NAME = 3; // 期间名称

    public final static int AMOUNT = 4; //金额

    public final static int COMPANY_NAME = 5;//公司名称

    public final static int CURRENCY = 6; //币种

    public final static String IMPORT_ERROR_REMARK = "错误说明";

    //    public final static String NAME_LENGTH_REGEX = "^[\\u4e00-\\u9fa5|a-zA-Z|0-9]{1,200}$";
    public final static String NAME_LENGTH_REGEX = "^.{0,200}$";

    public final static String VALUE_LENGTH_REGEX = "^[a-zA-Z|0-9]{1,100}$";

    public final static String REMARK_LENGTH_REGEX = "^.{0,200}$";

    public final static String YEAR_REGEX = "/^(19|20)\\d{2}$/";
}

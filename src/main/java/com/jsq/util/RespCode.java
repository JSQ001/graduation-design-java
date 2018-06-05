package com.jsq.util;

public interface RespCode {

    /**
     * 成功
     */
    String RES_0000 = "0000";

    /** 参数{0}格式错误: {1} */
    String RES_0010 = "0010";
    /** 参数{0}必须为有效的枚举值 */
    String RES_0011 = "0011";
    /** 参数{0}不能为空 */
    String RES_0012 = "0012";
    /** 参数{0}取值不在范围内 */
    String RES_0013 = "0013";

    /** 请求速度过快! */
    String RES_9960 = "9960";
    /** 您无权请求该资源! */
    String RES_1000 = "1000";

    /*通用*/
    String COLUMN_SHOULD_BE_EMPTY = "00001"; // 列应该为空
    String COLUMN_SHOULD_NOT_BE_EMPTY = "00002"; // 列不应该为空
    String DATASOURCE_CANNOT_FIND_OBJECT = "00003"; //数据库找不到对象
    String DATA_EXISTS = "00004";  //数据已存在
    String DATA_NOT_EXISTS = "00005";  //数据不存在
    String ID_NULL="00006";// id 不应该为空!
    String ID_NOT_NULL="00007";// id 应该为空!
    String DATA_ASSIGN="00008";  //已分配该数据
    String STRING_IS_NULL="00009"; //格式化string为空
    String DATE_FORMAT_NOT_TRUE="00010";//格式不正确
    String OPERATION_DATA_NOT_BE_EMPTY = "00011";  //参与运算的数据不能为空
    String ATTACHMENT_TYPE_NOT_NULL = "00012";//附件类型为空
    String VERSION_NUMBER_CHANGED = "00013";   //版本号不一致
    String READ_FILE_FAILED = "00014"; //读取文件失败
	String PERIOD_NOT_FOUND = "00015";//获取期间详细信息失败
    /**用户相关*/
    String TENANT_MESSAGE_NOT_EXISTS = "00101";   //租户信息为空
    String USER_MESSAGE_NOT_EXISTS = "00102";    //用户信息为空
    String COMPANY_MESSAGE_NOT_EXISTS = "00103";     //公司信息为空
    String EMPLOYEE_MESSAGE_NOT_EXISTS = "00104";     //员工信息为空
    /** 预算场景 */
    String BUDGETSCENARIO_ID_NULL="10001";// 预算场景id为null
    String BUDGETSCENARIO_ID_NOT_NULL="10002";// 预算场景id不应该为null
    String BUDGETSCENARIO_NOT_FOUND_OBJECT="10003";// 数据库找不到对象!
    String BUDGETSCENARIO_CODE_REPETITION="10004";// 唯一性校验错误，当前账套下存在这个预算场景代码!
    String BUDGETSCENARIO_REPETITION="10005";// 一个组织下只允许有一个默认场景!
    /**预算组织*/
    String BUDGET_ORGANIZATION_REPETITION = "20001";
    String BUDGET_ORGANIZATION_NOT_EXISTS = "20002";//该预算组织不存在!
    String BUDGET_ORGANIZATION_NOT_ENABLED = "20003";//该预算组织尚未启用!
    String BUDGET_ORGANIZATION_NOT_DEFINED = "20004"; //账套下未定义预算组织
    /**预算日记账类型定义*/
    String BUDGETJOURNALTYPE_ID_NULL = "120001";//预算日记账类型ID为null!
    String BUDGETJOURNALTYPE_ID_NOT_NULL = "120002";//预算日记账类型ID不为null!
    String BUDGETJOURNALTYPE_NOT_FOUND = "120003";//数据库中未找到该预算日记账类型!
    String BUDGETJOURNALTYPE_CODE_NOT_UNIQUE = "120004";//预算日记账类型定义code不唯一!
    String BUDGETJOURNALTYPE_BUSINESS_TYPE_CANNOT_BE_MODIFIED = "120014";//该预算日记账类型的预算业务类型不可以修改!
    //预算日记账类型关联的预算项目表
    String BUDGETJOURNALTYPE_ASSIGN_ITEM_ID_NOT_NULL = "120005";//预算日记账类型关联的预算项目表ID不为null!
    String BUDGETJOURNALTYPE_ASSIGN_ITEM_NOT_ALLOWED_TO_REPEAT = "120006";//同一预算日记帐类型下预算项目不允许重复!
    String BUDGETJOURNALTYPE_ASSIGN_ITEM_NOT_FOUND = "120007";//数据库中未找到该预算日记账类型关联的预算项目数据!
    //预算日记账类型关联的预算表
    String BUDGETJOURNALTYPE_ASSIGN_STRUCTURE_ID_NOT_NULL = "120008";//预算日记账类型关联的预算表ID不为null!
    String BUDGETJOURNALTYPE_ASSIGN_STRUCTURE_NOT_ALLOWED_TO_REPEAT = "120009";//同一预算日记帐类型下预算表不允许重复!
    String BUDGETJOURNALTYPE_ASSIGN_STRUCTURE_NOT_FOUND = "120010";//数据库中未找到该预算日记账类型关联的预算表数据!
    //预算日记账类型关联的公司表
    String BUDGETJOURNALTYPE_ASSIGN_COMPANY_ID_NOT_NULL = "120011";//预算日记账类型关联的公司表ID不为null!
    String BUDGETJOURNALTYPE_ASSIGN_COMPANY_NOT_ALLOWED_TO_REPEAT = "120012";//同一预算日记账类型下公司不允许重复!
    String BUDGETJOURNALTYPE_ASSIGN_COMPANY_NOT_FOUND = "120013";//数据库中未找到该预算日记账类型关联的公司数据!
    /**预算表*/
    String BUDGET_STRUCTURE_ID_NOT_NULL = "40001";//预算表ID不为null!
    String BUDGET_STRUCTURE_CODE_NOT_UNIQUE = "40002";//预算表code不唯一!
    String BUDGET_STRUCTURE_NOT_FOUND = "40003";//数据库中未找到该预算表数据!
    String BUDGET_STRUCTURE_PERIOD_STRATEGY_NOT_FOUND = "40007";//未找到预算表对应的编制期!
    String BUDGET_STRUCTURE_PERIOD_STRATEGY_CANNOT_BE_MODIFIED = "40012";//当前预算表已经被预算日记账引用，其编制期段不可以修改!
    String BUDGET_STRUCTURE_ISENABLED_CANNOT_BE_MODIFIED = "40018";//当前预算表已经被预算日记账类型分配且启用，不可禁用!
    //预算表关联的公司表
    String BUDGETSTRUCTURE_ASSIGN_COMPANY_ID_NOT_NULL = "40004";//预算表关联的公司表ID不为null!
    String BUDGETSTRUCTURE_ASSIGN_COMPANY_NOT_ALLOWED_TO_REPEAT = "40005";//同一预算表下公司不允许重复!
    String BUDGETSTRUCTURE_ASSIGN_COMPANY_NOT_FOUND = "40006";//数据库中未找到该预算表关联的公司表数据!
    //预算表关联的维度表
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_ID_NOT_NULL = "40008";//预算表关联的维度表ID不为null!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_NOT_ALLOWED_TO_REPEAT = "40009";//同一预算表下维度不允许重复!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_PRIORITY_NOT_ALLOWED_TO_REPEAT = "40010";//同一预算表下，当布局位置相同时，布局顺序不可以重复!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_NOT_FOUND = "40011";//数据库中未找到该预算表关联的维度表数据!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_CANNOT_INSERT = "40013";//当前预算表已经被预算日记账引用，不可以再分配维度!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_CANNOT_DELETE = "40014";//当前预算表已经被预算日记账引用，不可以删除已经分配的维度!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_CANNOT_MODIFY_DIMENSIONId = "40015";//当前预算表已经被预算日记账引用，不可以修改其分配维度的维度id!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_CANNOT_MODIFY_LAYOUTPOSITION = "40016";//当前预算表已经被预算日记账引用，不可以修改其分配维度的布局位置!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_CANNOT_MODIFY_LAYOUTPRIORITY = "40017";//当前预算表已经被预算日记账引用，不可以修改其分配维度的布局顺序!
    String BUDGETSTRUCTURE_ASSIGN_LAYOUT_CANNOT_MODIFY_ISENABLED = "40019";//当前预算表已经被预算日记账引用，不可以修改其分配维度的启用状态!
    /**预算策略*/
    String BUDGET_CONTROL_STRATEGY_CODE_NOT_UNIQUE = "70001";//同一预算组织下的预算策略代码不能重复!
    String BUDGET_CONTROL_STRATEGY_DETAIL_CODE_NOT_UNIQUE = "70002";//同一控制策略下的明细代码不能重复!
    String BUDGET_CONTROL_STRATEGY_DETAIL_SEQUENCE_NOT_UNIQUE = "70003";//同一控制策略下的明细序号不能重复!
    /**预算版本*/
    String BUDGET_VERSION_CODE_NOT_UNIQUE = "30001";//同一预算组织下预算版本代码不能重复!
    String BUDGET_VERSION_STATUS_CURRENT = "30002";//同一个预算组织下只能有一个当前版本!
    String BUDGET_VERSION_NOT_FOUND = "30003";//数据库中未找到该预算版本数据!
    /**编码规则*/
    String BUDGET_CODING_RULE_OBJECT_CODE_NOT_UNIQUE = "130001";//同一租户下的单据类别、单据类型、公司代码的组合只能有一个!
    String BUDGET_CODING_RULE_OBJECT_NOT_ENABLED = "130002";//没有启用的编码规则定义!
    String BUDGET_CODING_RULE_ONE_ENABLED = "130003";//只能有一条启用的编码规则!
    String BUDGET_CODING_RULE_CODE_NOT_UNIQUE = "130004";//同一编码规则定义下的编码规则代码不能重复!
    String BUDGET_CODING_RULE_IS_USED = "130005";//编码规则已被引用!
    String BUDGET_CODING_RULE_ENABLED_EXCEPTION = "130006";//编码规则启用多个或没有!
    String BUDGET_CODING_RULE_DETAIL_SEQUENCE_NOT_UNIQUE = "130007";//同一编码规则下的序号不唯一!
    String BUDGET_CODING_RULE_DETAIL_NOT_FOUND = "130008";//该编码规则下无编码规则明细！
    String BUDGET_CODING_RULE_OPERATION_DATE_FORMAT_EXCEPTION = "130009";//日期格式为yyy-MM-dd!
    String BUDGET_CODING_RULE_DATE_FORMAT_EXCEPTION = "130010";//规则明细中日期参数格式不正确！
    String BUDGET_CODING_RULE_CURRENT_VALUE_OVERFLOW = "130011";//编号位数溢出!
    String BUDGET_CODING_RULE_DETAIL_SEQUENCE_NOT_FOUND = "130012";//规则明细中不存在序列号，请先添加!
    String BUDGET_CODING_RULE_ORDER_NUMBER_LENGTH_NO_MORE_THAN_30 = "130013";//单据编号不能超过30!
    String BUDGET_CODING_RULE_VALUE_COMPANY_CODE_NOT_FOUND = "130014";//公司代码不能为空!
    String BUDGET_CODING_RULE_VALUE_DOCUMENT_TYPE_NOT_FOUND = "130015";//单据类型代码不能为空!
    /**预算余额查询*/
    String BUDGET_BALANCE_QUERY_HEADER_NOT_UNIQUE = "110001";//预算余额查询方案代码已存在
    String BUDGET_BALANCE_QUERY_LINE_ID_NOT_NULL = "110002";//预算余额查询方案行参数ID不能为空
    String BUDGET_BALANCE_QUERY_SET_DIMENSION = "110003";//设置维度描述失败
    String BUDGET_BALANCE_QUERY_PERIOD_NOT_NULL = "110004";//查询参数期间不能为空
    String BUDGET_BALANCE_QUERY_PERIOD_YEAR = "110005";//查询参数期间所在的年度和查询参数年度不一致
    /*预算日记账*/
    String BUDGET_JOURNAL_HEAD_IS_NULL = "150001";//预算日记账头为空
    String BUDGET_JOURNAL_LINE_IS_NULL = "150002";//预算日记账行为空
    String BUDGET_JOURNAL_STATUS_NOT_ALLOW = "150003";//当前预算日记账状态不允！
    String BUDGET_JOURNAL_HEAD_NOT_FOUND="150004";//没有找到预算日记账头信息
    String BUDGET_JOURNAL_LINE_NOT_FOUND="150005";//没有找到预算日记账行信息
    String BUDGET_JOURNAL_LINE_ID_IS_NULL="150006";//预算日记账行id为空
    String BUDGET_CONTROL_PERIOD_IS_NULL="150007";//控制期间为空
    String BUDGET_JOURNAL_HEAD_UPLOAD_ERR="150008";//预算日记账附件上传错误
    String BUDGET_JOURNAL_HEAD_FILE_IS_NULL="150009";//预算日记账附件上传为空
    String BUDGET_JOURNAL_HEAD_DOCUMENT_OID_IS_NULL="150010";//预算日记账头单据oid为空
    String BUDGET_JOURNAL_EMP_OID_IS_NULL="150011";//员工oid为空
    String BUDGET_JOURNAL_HEAD_ID_IS_NULL="150012";//预算日记账头id为空
    String COMPANY_CANNOT_FIND="150013";//数据库找不到该公司
    String DEPARTMENT_CONNOT_FIND="150014";//数据库找不到部门
    String EMP_CONNOT_FIND="150015";//数据库找不到该员工
    String BUDGET_JOURNAL_CODE="150016";//预算日记账编号已经存在
    String BUDGET_JOURNAL_VALUE_ERROR="150017";//值列表转换或用户名称获取异常
    String PERIOD_NUM_SELECT_ERROR="150018";//期间数查询异常
    String DIMENSION_VALUE_NOT_FOUND = "150019";//维值查询异常
    String DIMENSION_NOT_FOUND="150020";//维度查询异常
    String BUDGET_PERIOD_ERROR_QUARTER="150021";//季度控制时，月度应该为空
    String BUDGET_PERIOD_ERROR_YEAR="150022";//年度控制时，季度和月度应该为空
    String BUDGET_JOURNAL_BALANCE_EXIT="150023";//此条数据已插入余额表
    /* 预算项目类型 */
    String BUDGET_ITEM_TYPE_CODE_NOT_UNIQUE = "60001"; //预算项目类型代码已经存在。

    /* 预算项目 */
    String BUDGET_ITEM_CODE_NOT_UNIQUE = "50001"; //预算项目代码已经存在。

    /* 预算项目组*/
    String BUDGET_ITEM_GROUP_CODE_NOT_UNIQUE="160001";//同一账套(组织)下已经存在相同的code！

    /*预算项目映射*/
    String BUDGET_ITEM_MAPPING_DATA_IS_NULL="170001";//待操作的数据为空
    String BUDGET_ITEM_MAPPING_NOT_EXIT="170002";//待删除的数据不存在
    String BUDGET_ITEM_NOT_EXIT="170003";//预算项目不存在
    String BUDGET_ITEM_MAPPING_NOT_EXIT_OR_MPAPPING_ERROR="170004";//映射关系不存在或映射关系错误
    String BUDGET_ITEM_MAPPING_ERR="170005";//一个明细对应一个预算项目

    /*预算校验*/
    String BUDGET_CHECK_ISSUE_MUST_MAPPING_BUDGET_ITEM = "180001";  //当前事项与预算项目未维护映射关系
    String BUDGET_CHECK_STATUS_SHOULD_BE_N = "180002";  //status 值应该为'N'!
    String BUDGET_CHECK_ITEM_NOT_EXISTS = "180003";   //预算项目未启用或不存在!
    String BGT_CHECK_STRUCTURE_NOT_EXISTS_ERROR = "180004";   //请先定义预算表
    String BGT_CHECK_SCENARIO_NOT_EXISTS_ERROR = "180005";  //请先定义预算场景
    String BGT_CHECK_VERSION_NOT_EXISTS_ERROR = "180006";  //公司当前没有预算版本
    String BGT_CHECK_CONTROL_RULE_ERROR="180007";  //预算控制规则配置有误
    String BGT_CHECK_AMOUNT_TOO_BIG = "180008";    //调整后金额不能大于原金额
    String BGT_CHECK_GET_RESERVE_FAIL = "180009";    //获取预算占用信息失败
    String BGT_CHECK_PERIOD_STRATEGY_ERROR = "180010";       //预算编制期段与控制期段不符

    /*预算控制规则*/
    String BUDGET_CONTROL_RULE_EXISTS = "190001";                //同一预算组织下的控制规则代码或优先级不能重复！
}

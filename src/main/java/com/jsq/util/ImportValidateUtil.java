package com.jsq.util;

import com.jsq.entity.BatchTransactionLog;
import com.jsq.enums.ValidateErrorType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cbc on 2017/1/12.
 */
public final class ImportValidateUtil {

    private static Pattern p = Pattern.compile("^\\d+$");

    private ImportValidateUtil() {
    }

    public static void addErrorToJSON(JSONObject error, ValidateErrorType errorType, int rowNum) {
        try {
            if (!error.has(errorType.toString())) {
                error.put(errorType.toString(), new JSONArray());
            }
            error.getJSONArray(errorType.toString()).put(rowNum + 1);
        } catch (JSONException e) {

        }
    }

    public static void addErrorToJSON2(JSONObject error, String errorType, int rowNum) {
        try {
            if (!error.has(errorType.toString())) {
                error.put(errorType.toString(), new JSONArray());
            }
            error.getJSONArray(errorType.toString()).put(rowNum);
        } catch (JSONException e) {

        }
    }

    public static boolean validateFieldValueNull(String fieldValue, BatchTransactionLog transactionLog, Row row, ValidateErrorType nullErrorType) {
        boolean isValid = true;
        if (StringUtils.isEmpty(fieldValue)) {
            addErrorToJSON(transactionLog.getErrors(), nullErrorType, row.getRowNum());
            isValid = false;
        }
        if (!isValid) {
            transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
        }
        return isValid;
    }

    public static boolean validateYear(Integer year, String regex, BatchTransactionLog transactionLog, Row row, ValidateErrorType ErrorType){
        boolean isValid = true;
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(year.toString());
        if (!match.matches()) {
            addErrorToJSON(transactionLog.getErrors(), ErrorType, row.getRowNum());
            isValid = false;
            transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
        }
        return isValid;

    }

    public static boolean validateFieldValueMaxLength(String fieldValue, BatchTransactionLog transactionLog, Row row, ValidateErrorType maxErrorType, int maxLength) {
        boolean isValid = true;
        if (fieldValue.length() > maxLength) {
            addErrorToJSON(transactionLog.getErrors(), maxErrorType, row.getRowNum());
            isValid = false;
        }
        if (!isValid) {
            transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
        }
        return isValid;
    }


    public static boolean validateFullNameFormat(String fullName, BatchTransactionLog transactionLog, Row row, ValidateErrorType errorType) {
        if(!fullName.matches("^[\\u4e00-\\u9fa5]+$")){
            //判断英文名格式中是否有/(/不在开头，不在结尾，并且中间只能出现一次)
            if(!fullName.matches("^[^/]*[^/]/{1}[^/]*[^/]$") || fullName.getBytes().length!=fullName.length() || !fullName.matches("[^\\d]+")){
                addErrorToJSON(transactionLog.getErrors(), errorType, row.getRowNum());
                int failEntities = transactionLog.getFailureEntities();
                failEntities++;
                transactionLog.setFailureEntities(failEntities);
                return false;
            }
        }
        return true;
    }


    /**
     * 验证文本是否大于对应长度
     *
     * @param text：文本
     * @param transactionLog：错误日志
     * @param row：行
     * @param validateErrorType：错误类型
     * @return
     */
    public static boolean validateTextMaxLength(String text, String regex, BatchTransactionLog transactionLog, Row row, ValidateErrorType validateErrorType) {
        boolean isValid = true;
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(text);
        if (!match.matches()) {
            addErrorToJSON(transactionLog.getErrors(), validateErrorType, row.getRowNum());
            isValid = false;
            transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
        }
        return isValid;
    }

    public static boolean validateQuarter(Integer quarter, BatchTransactionLog transactionLog, Row row, ValidateErrorType errorType){
        boolean isValid = true;
        if (quarter<0||quarter>4) {
            addErrorToJSON(transactionLog.getErrors(), errorType, row.getRowNum());
            isValid = false;
            transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
        }
        return isValid;
    }

    public static String getValidateErrorDetail(ValidateErrorType errorType) {
        String errorDetail = "";
        switch (errorType) {
            case NULL_BUDGET_ITEM_CODE:
                errorDetail = "预算项目代码不能为空！";
                break;
            case NOT_FOUND_ITEM_ASSIGN_COMPANY:
                errorDetail = "当前预算项目没有分配当前公司";
                break;
            case NOT_FOUND_JOURNAL_ASSIGN_ITEM:
                errorDetail = "当前日记帐类型没有分配当前预算项目";
                break;
            case NULL_ITEM_NAME:
                errorDetail = "预算项目名称不能为空！";
                break;
            case NULL_ITEM_TYPE_CODE:
                errorDetail = "预算项目类型不能为空！";
                break;
            case NULL_COMPANY:
                errorDetail = "公司代码不能为空";
                break;
            case NULL_AMOUNT:
                errorDetail = "金额不能为空！";
                break;
            case NULL_PERIOD:
                errorDetail = "期间不能为空！";
                break;
            case NULL_CURRENCY:
                errorDetail = "币种不能为空！";
                break;
            case NULL_SOURCE_TYPE:
                errorDetail = "来源类型不能为空！";
                break;
            case NULL_SOURCE_ITEM_CODE:
                errorDetail = "明细类型不能为空!";
                break;
            case NOT_FOUND_EMPLOYEE:
                errorDetail = "当前员工不存在！";
                break;
            case ITEM_NAME_LENGTH:
                errorDetail = "预算项目名称最多输入200个文字";
                break;
            case ITEM_CODE_LENGTH:
                errorDetail = "预算项目代码最多输入200个长度";
                break;
            case PERIOD_YEAR_FORMAT:
                errorDetail = "期间年份格式不正确！";
                break;
            case PERIOD_QUARTER_FORMAT:
                errorDetail = "期间季度格式不正确！";
                break;
            case ITEM_CODE_NOT_FOUND_OR_NAME_NOT_FOUND:
                errorDetail = "没有当前预算项目！";
                break;
            case PERIOD_NOT_EXIST:
                errorDetail = "期间不存在！";
                break;
            case COMPANY_NOT_FOUND_OR_NAME_NOT_FOUND:
                errorDetail = "公司不存在！";
                break;
            case DEPARTMENT_NOT_FOUND_OR_NAME_NOT_FOUND:
                errorDetail = "部门不存在！";
                break;
            case NOT_FOUND_DIMENSION:
                errorDetail = "当前维度不存在！";
                break;
            case NOT_FOUND_DIMENSION_VALUE_NAME:
                errorDetail = "当前维值名称不存在！";
                break;
            case NOT_FOUND_DIMENSION_VALUE_CODE:
                errorDetail = "当前维值代码不存在！";
                break;
            case NOT_FOUND_DIMENSION_VALUE:
                errorDetail = "当前维值不存在！";
                break;
            case NOT_FOUND_SOURCE_TYPE:
                errorDetail = "当前来源类型不存在";
                break;
            case NOT_FOUND_SOURCE_ITEM_CODE:
                errorDetail = "当前费用明细类型不存在";
                break;
            case NOT_FOUND_ITEM_TYPE:
                errorDetail = "当前预算项目类型不存在！";
                break;
            case NOT_UNQ_ITEM:
                errorDetail = "当前需要新建的项目项目已经存在！";
                break;
            case NOT_UNQ_MAPPING_RELATIONSHIPS:
                errorDetail = "已经存在当前映射关系";
                break;
            case UNKNOWN:
                errorDetail = "未知错误";
                break;
            case NOT_FOUND_CURRENCY:
                errorDetail = "当前币种不存在！";
                break;
            default:
                errorDetail = "未知错误";
                break;
        }
        return errorDetail;
    }

}

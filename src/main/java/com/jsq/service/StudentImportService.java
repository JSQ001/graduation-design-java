package com.jsq.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.itextpdf.text.io.StreamUtil;
import com.jsq.controller.BatchTransactionLogController;
import com.jsq.entity.*;
import com.jsq.entity.Class;
import com.jsq.entity.enumeration.StudentImportCode;
import com.jsq.entity.enumeration.BatchOperationTypeEnum;
import com.jsq.entity.enumeration.TransactionStatus;
import com.jsq.enums.ValidateErrorType;
import com.jsq.exception.BizException;
import com.jsq.exception.ObjectNotFoundException;
import com.jsq.exception.ValidationError;
import com.jsq.exception.ValidationException;
import com.jsq.mapper.*;
import com.jsq.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
@Transactional
public class StudentImportService extends BaseService<UserInfoMapper,User> {

    public  static Map<UUID,BatchTransactionLog> transactions = BatchTransactionLogController.transactions;

    public static final Map<UUID,List<StudentImportDTO>> importMap = new HashMap<>();

    public final BatchTransactionLogMapper transactionLogMapper;

    @Autowired
    private UserDeptMapper userDeptMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserClassMapper userClassMapper;

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    public StudentImportService(BatchTransactionLogMapper transactionLogMapper){
        this.transactionLogMapper = transactionLogMapper;
    }

    public byte[] exportTemplate() {
        log.debug("import excel template");
        ByteArrayOutputStream bos = null;
        InputStream inputStream = null;
        try {
            System.out.print(StudentImportCode.IMPORT_TEMPLATE_PATH);
            inputStream = StreamUtil.getResourceStream(StudentImportCode.IMPORT_TEMPLATE_PATH);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            bos = new ByteArrayOutputStream();
            workbook.write(bos);

            bos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            System.out.print(e);
            throw new BizException(RespCode.READ_FILE_FAILED);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                throw new BizException(RespCode.READ_FILE_FAILED);
            }
        }
    }

    public UUID importStudent(InputStream in) {
        try {
            log.info("start importing student reserve adjust");
            XSSFSheet sheet = new XSSFWorkbook(in).getSheetAt(0);
            BatchTransactionLog transactionLog = genraterLog(sheet, BatchOperationTypeEnum.STUDENT);
            //开启线程计算
            ExecutorService executor = Executors.newSingleThreadExecutor();
            FutureTask<Object> task = new FutureTask<>(() -> {
                parseFile(sheet, transactionLog.getTransactionLogUUID());
                return null;
            });
            System.out.print(transactionLog.getTransactionLogUUID());
            executor.execute(task);
            return transactionLog.getTransactionLogUUID();
        } catch (Exception e) {
            System.out.print(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    private void parseFile(XSSFSheet sheet, UUID transactionLogUUID) throws JSONException {
        try {
            List<StudentImportDTO> importList = new ArrayList<>();
            BatchTransactionLog transactionLog = transactions.get(transactionLogUUID);
            for (Row row : sheet) {
                try {
                    if (row.getRowNum() < StudentImportCode.EXCEL_BASE_ROW) {
                        continue;
                    }
                    if (transactionLog.getStatus() == TransactionStatus.CANCELLED.getID()) {
                        break;
                    }

                    String userNumber = ExcelUtils.getCellStringValue(row.getCell(StudentImportCode.USER_NUMBER));

                    String nickName = ExcelUtils.getCellStringValue(row.getCell(StudentImportCode.NICK_NAME));

                    String gender = ExcelUtils.getCellStringValue(row.getCell(StudentImportCode.GENDER));

                    String deptName = ExcelUtils.getCellStringValue(row.getCell(StudentImportCode.DEPT_NAME));

                    String className = ExcelUtils.getCellStringValue(row.getCell(StudentImportCode.CLASS_NAME));

                    String phone = ExcelUtils.getCellStringValue(row.getCell(StudentImportCode.PHONE));

                    StudentImportDTO importEntity = StudentImportDTO.builder()
                            .userNumber(userNumber).nickName(nickName).gender(gender)
                            .deptName(deptName).className(className).phone(phone).rowNum(row.getRowNum()).build();

                    if (!ImportValidateUtil.validateFieldValueNull(userNumber.toString(), transactionLog, row, ValidateErrorType.NULL_BUDGET_ITEM_CODE)) {
                        importEntity.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NULL_BUDGET_ITEM_CODE));
                        importMap.get(transactionLogUUID).add(importEntity);
                        continue;
                    }
                    if (!ImportValidateUtil.validateFieldValueNull(nickName, transactionLog, row, ValidateErrorType.NULL_ITEM_NAME)) {
                        importEntity.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NULL_ITEM_NAME));
                        importMap.get(transactionLogUUID).add(importEntity);
                        continue;
                    }
                    if (!ImportValidateUtil.validateFieldValueNull(gender, transactionLog, row, ValidateErrorType.NULL_ITEM_TYPE_CODE)) {
                        importEntity.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NULL_ITEM_TYPE_CODE));
                        importMap.get(transactionLogUUID).add(importEntity);
                        continue;
                    }
                    if (!ImportValidateUtil.validateFieldValueNull(deptName, transactionLog, row, ValidateErrorType.NULL_BUDGET_ITEM_CODE)) {
                        importEntity.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NULL_BUDGET_ITEM_CODE));
                        importMap.get(transactionLogUUID).add(importEntity);
                        continue;
                    }
                    if (!ImportValidateUtil.validateFieldValueNull(className, transactionLog, row, ValidateErrorType.NULL_BUDGET_ITEM_CODE)) {
                        importEntity.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NULL_BUDGET_ITEM_CODE));
                        importMap.get(transactionLogUUID).add(importEntity);
                        continue;
                    }
                    if (!ImportValidateUtil.validateFieldValueNull(phone, transactionLog, row, ValidateErrorType.NULL_BUDGET_ITEM_CODE)) {
                        importEntity.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NULL_BUDGET_ITEM_CODE));
                        importMap.get(transactionLogUUID).add(importEntity);
                        continue;
                    }

                    importList.add(importEntity);

                    if(importList.size()==20 || row.getRowNum()== sheet.getLastRowNum()){
                        parseImportData(importList,transactionLog);
                        importList.clear();
                    }

                } catch (Exception e) {
                    ImportValidateUtil.addErrorToJSON(transactionLog.getErrors(), ValidateErrorType.UNKNOWN, row.getRowNum());
                    transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
                    StudentImportDTO importDTO= new StudentImportDTO();
                    importDTO.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.UNKNOWN));
                    importDTO.setRowNum(row.getRowNum());
                    importMap.get(transactionLogUUID).add(importDTO);
                }
            }

            if (CollectionUtils.isNotEmpty(importList)) {
                parseImportData(importList,transactionLog);
                importList.clear();
            }

            if (transactionLog.getStatus() != TransactionStatus.CANCELLED.getID()) {
                transactionLog.setTransactionStatus(TransactionStatus.DONE);
            }

            transactionLog.setFinishTime(ZonedDateTime.now());
            transactionLog.setErrorDetail(JsonUtil.toJson(importMap.get(transactionLogUUID)));
            System.out.print(transactionLog.getTransactionLogUUID());
            transactionLogMapper.insert(transactionLog);
            transactions.remove(transactionLogUUID);
            importMap.remove(transactionLogUUID);
        } catch (Throwable e) {
            System.out.print(e);
            BatchTransactionLog transactionLog = transactions.get(transactionLogUUID);
            transactionLog.setTransactionStatus(TransactionStatus.ERROR);
            JSONObject errors = transactionLog.getErrors();
            errors.put("error", e.getMessage());
            transactionLog.setErrors(errors);
            transactionLogMapper.insert(transactionLog);
            transactions.remove(transactionLogUUID);
            importMap.remove(transactionLogUUID);
        }


    }

    private List<User> parseImportData(List<StudentImportDTO> importDTOList, BatchTransactionLog transactionLog) {

        JSONObject error = transactionLog.getErrors();
        List<User> itemList = new ArrayList<>();
        for (StudentImportDTO importDTO : importDTOList) {
            try {
                String userNumber = importDTO.getUserNumber();
                String nickName = importDTO.getNickName();
                String gender = importDTO.getGender();
                String deptName = importDTO.getDeptName();
                String className = importDTO.getClassName();
                String phone = importDTO.getPhone();

                User user = User.builder().userNumber(userNumber).nickName(nickName)
                        .gender(gender).password("123456").phone(phone).enabled(true).build();

                try {
                    this.insert(user);

                    //用户权限表
                    userAuthorityMapper.insert(UserAuthority.builder().userId(user.getId()).authorityId(3L).build());

                    //插入学生系部表
                    userDeptMapper.insert(UserDept.builder().userId(user.getId()).deptId(
                            departmentMapper.selectOne(Department.builder().deptName(deptName.trim()).enabled(true).build()).getId()).build());

                   //插入学生班级表
                    userClassMapper.insert(UserClass.builder().userId(user.getId()).classId(
                            classMapper.selectOne(Class.builder().className(className.trim()).build()).getId()).build());
                }catch (DuplicateKeyException e){
                    throw new ValidationException(new ValidationError("userNumber","学号重复"));
                }
                transactionLog.setSuccessEntities(transactionLog.getSuccessEntities() + 1);
                itemList.add(user);
            } catch (ValidationException e) {
                transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
                if (e.getValidationErrors().size() == 0) {
                    ImportValidateUtil.addErrorToJSON(error, ValidateErrorType.UNKNOWN, importDTO.getRowNum());
                    importDTO.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.UNKNOWN));
                }else if (e.getValidationErrors().get(0).getMessage().equals("not exist item type")){
                    ImportValidateUtil.addErrorToJSON(error,ValidateErrorType.NOT_FOUND_ITEM_TYPE,importDTO.getRowNum());
                    importDTO.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NOT_FOUND_ITEM_TYPE));
                }else if (e.getValidationErrors().get(0).getMessage().equals("Duplication of existing item")){
                    ImportValidateUtil.addErrorToJSON(error,ValidateErrorType.NOT_UNQ_ITEM,importDTO.getRowNum());
                    importDTO.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.NOT_UNQ_ITEM));
                }
                importMap.get(transactionLog.getTransactionLogUUID()).add(importDTO);

            } catch (Exception e) {
                transactionLog.setFailureEntities(transactionLog.getFailureEntities() + 1);
                ImportValidateUtil.addErrorToJSON(error, ValidateErrorType.UNKNOWN, importDTO.getRowNum());
                importDTO.setErrorDetail(ImportValidateUtil.getValidateErrorDetail(ValidateErrorType.UNKNOWN));
                importMap.get(transactionLog.getTransactionLogUUID()).add(importDTO);
            }

        }
        return itemList;

    }



    /**
     * 生成记录日志
     *
     * @param sheet
     * @return
     */

    private BatchTransactionLog genraterLog(XSSFSheet sheet, BatchOperationTypeEnum batchOperationType) {

        BatchTransactionLog transactionLog = new BatchTransactionLog(batchOperationType, sheet.getPhysicalNumberOfRows() - 1);
        transactionLog.setCreatedBy(LoginInformationUtil.getCurrentUserID());
        JSONObject error = new JSONObject();
        transactionLog.setErrors(error);
        transactionLog.setTransactionStatus(TransactionStatus.PROCESS_DATA);
        transactions.put(transactionLog.getTransactionLogUUID(), transactionLog);
        importMap.put(transactionLog.getTransactionLogUUID(), new ArrayList<>());
        return transactionLog;
    }


    public byte[] exportFailedData(UUID transactionID) {
        BatchTransactionLog logs = transactions.get(transactionID);
        if (null != logs) {
            return exportFailedDataDetail(importMap.get(transactionID));
        } else {
            logs = transactionLogMapper.selectLogByUUID(transactionID);
            if (logs != null) {
                return exportFailedDataDetail(
                        JsonUtil.fromJson(
                                logs.getErrorDetail(),
                                new TypeReference<List<StudentImportDTO>>() {
                                })
                );
            } else {
                throw new ObjectNotFoundException(BatchTransactionLog.class, transactionID.toString());
            }
        }
    }


    private byte[] exportFailedDataDetail(List<StudentImportDTO> importDTOS) {
        InputStream in = null;
        ByteArrayOutputStream bos = null;
        XSSFWorkbook workbook = null;
        try {
            in = StreamUtil.getResourceStream(StudentImportCode.IMPORT_ERROR_PATH);
            workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int startRow = StudentImportCode.EXCEL_BASE_ROW;
            Row row = null;
            Cell cell = null;
            for (StudentImportDTO importDTO : importDTOS) {
                row = sheet.createRow(startRow++);
                cell = row.createCell(StudentImportCode.USER_NUMBER);
                cell.setCellValue(importDTO.getUserNumber());
                cell = row.createCell(StudentImportCode.NICK_NAME);
                cell.setCellValue(importDTO.getNickName());
                cell = row.createCell(StudentImportCode.GENDER);
                cell.setCellValue(importDTO.getGender());
                cell = row.createCell(StudentImportCode.DEPT_NAME);
                cell.setCellValue(importDTO.getDeptName());
                cell = row.createCell(StudentImportCode.CLASS_NAME);
                cell.setCellValue(importDTO.getClassName());
                cell = row.createCell(StudentImportCode.PHONE);
                cell.setCellValue(importDTO.getPhone());
                cell = row.createCell(row.getLastCellNum());
                cell.setCellValue(importDTO.getErrorDetail());
            }
            bos = new ByteArrayOutputStream();
            //  FileOutputStream fileOutputStream = new FileOutputStream("D://cbc1.xlsx");
            //  workbook.write(fileOutputStream);
            workbook.write(bos);
            bos.flush();
            //  fileOutputStream.flush();
            //   fileOutputStream.close();
            workbook.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new BizException(RespCode.READ_FILE_FAILED);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                throw new BizException(RespCode.READ_FILE_FAILED);
            }

        }

    }
}

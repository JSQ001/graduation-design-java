package com.jsq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jsq.entity.BatchTransactionLog;
import com.jsq.entity.enumeration.TransactionStatus;
import com.jsq.service.BatchTransactionLogService;
import com.jsq.util.LoginInformationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cbc on 2017/12/4.
 */
@RestController
@Order(1)
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/student/batch/transaction/logs")
public class BatchTransactionLogController {

    private final BatchTransactionLogService transactionLogService;


    public static final Map<UUID, BatchTransactionLog> transactions = new HashMap<>();

    @GetMapping("/{transactionID}")
    public BatchTransactionLog getBatchTransactionLog(@PathVariable("transactionID") UUID transactionID) {
        BatchTransactionLog logs = transactions.get(transactionID);
        if (logs == null) {
            logs = transactionLogService.selectOne(new EntityWrapper<BatchTransactionLog>().eq("transaction_uuid",transactionID));
        }
        if (logs == null) {
            log.error("没找到信息");
        }
        return logs;
    }


    @DeleteMapping("/{transactionID}")
    public void cancelTransaction(@PathVariable UUID transactionID) {
        BatchTransactionLog log = transactions.get(transactionID);
        if (log != null && log.getCreatedBy().equals(LoginInformationUtil.getCurrentUserID())) {
            log.setStatus(TransactionStatus.CANCELLED.getID());
        }
    }
}

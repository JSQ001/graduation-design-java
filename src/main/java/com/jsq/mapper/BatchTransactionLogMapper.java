package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.BatchTransactionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Mapper
public interface BatchTransactionLogMapper extends BaseMapper<BatchTransactionLog> {

    @ResultType(BatchTransactionLog.class)
    @Select("select id," +
            "transaction_uuid transactionLogUUID," +
            "operation," +
            "total_entities totalEntities," +
            "success_entities successEntities,"+
            "failure_entities failureEntities,"+
            "start_time startTime,"+
            "finish_time finishTime,"+
            "status,"+
            "created_by createdBy,"+
            "errors,"+
            "error_detail errorDetail,"+
            "created_date createdDate"+
            " from bgt_batch_transaction_log where transaction_uuid = #{transactionLogUUID}")
    BatchTransactionLog selectLogByUUID(@Param("transactionLogUUID") UUID transactionLogUUID);
}

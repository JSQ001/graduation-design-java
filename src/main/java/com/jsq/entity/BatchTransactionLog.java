package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jsq.entity.enumeration.BatchOperationTypeEnum;
import com.jsq.entity.enumeration.TransactionStatus;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;


@TableName("tb_batch_transaction_log")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchTransactionLog implements Serializable{
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "transaction_uuid")
    private UUID transactionLogUUID;
    private int operation;
    @TableField( value = "total_entities", strategy = FieldStrategy.NOT_NULL)
    private Integer totalEntities;
    @TableField(value = "success_entities", strategy = FieldStrategy.NOT_NULL)
    private Integer successEntities;
    @TableField(value = "failure_entities", strategy = FieldStrategy.NOT_NULL)
    @NotNull
    private Integer failureEntities;

    private ZonedDateTime startTime;

    private ZonedDateTime finishTime;

    private int status;

    private Long createdBy;

    private JSONObject errors;

    @JsonIgnore
    private String errorDetail;

    private ZonedDateTime createdDate = ZonedDateTime.now();

    public BatchTransactionLog() {
    }

    public BatchTransactionLog(BatchOperationTypeEnum batchOperationTypeEnum, Integer totalEntities) {
        this.operation = batchOperationTypeEnum.getID();
        this.totalEntities = totalEntities;
        this.successEntities = 0;
        this.failureEntities = 0;
        this.transactionLogUUID=UUID.randomUUID();
        this.startTime = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public Integer getTotalEntities() {
        return totalEntities;
    }

    public void setTotalEntities(Integer totalEntities) {
        this.totalEntities = totalEntities;
    }

    public Integer getSuccessEntities() {
        return successEntities;
    }

    public void setSuccessEntities(Integer successEntities) {
        this.successEntities = successEntities;
    }

    public Integer getFailureEntities() {
        return failureEntities;
    }

    public void setFailureEntities(Integer failureEntities) {
        this.failureEntities = failureEntities;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(ZonedDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public JSONObject getErrors() {
        return errors;
    }

    public void setErrors(JSONObject errors) {
        this.errors = errors;
    }

    public BatchOperationTypeEnum getBatchOperationTypeEnum() {
        return BatchOperationTypeEnum.parse(this.operation);
    }

    public void setBatchOperationTypeEnum(BatchOperationTypeEnum batchOperationTypeEnum) {
        this.operation = batchOperationTypeEnum.getID();
    }

    public TransactionStatus getTransactionStatus() {
        return TransactionStatus.parse(this.status);
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.status = transactionStatus.getID();
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {

        this.createdDate = createdDate;
    }


    public UUID getTransactionLogUUID() {
        return transactionLogUUID;
    }

    public void setTransactionLogUUID(UUID transactionLogUUID) {
        this.transactionLogUUID = transactionLogUUID;
    }


}

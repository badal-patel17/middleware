package com.vfort.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Maps to the Oracle ORDERS table based on your current schema definition.
 */
@Entity
//@Table(name = "ORDERS", schema = "ROHIT")
//@Table(name = "tborder_Action", schema = "PRDOMS")
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Column(name = "SUB_TYPE")
    private String subType;

    @Column(name = "ORDER_UNIT_ID")
    private Long orderUnitId;

    @Column(name = "CTDB_CRE_DATETIME")
    private LocalDateTime ctdbCreDatetime;

    @Column(name = "CTDB_UPD_DATETIME")
    private LocalDateTime ctdbUpdDatetime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ACTION_TYPE")
    private String actionType;

    @Column(name = "SALES_CHANNEL")
    private String salesChannel;

    @Column(name = "REASON_ID")
    private Integer reasonId;

    @Column(name = "CANCEL_ALLOWED")
    private String cancelAllowed;

    @Column(name = "AMEND_ALLOWED_IND")
    private String amendAllowedInd;

    @Column(name = "FORM_ID")
    private String formId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "EXECUTION_DATE")
    private LocalDateTime executionDate;

    @Column(name = "COMPLETION_DATE")
    private LocalDateTime completionDate;

    @Column(name = "STEP_INSTANCE_ID")
    private Long stepInstanceId;

    @Column(name = "IS_EXCEPTION")
    private String isException;

    @Column(name = "PRODUCT_DEF_ID")
    private Long productDefId;

    @Column(name = "MAIN_IND")
    private Integer mainInd;

    @Column(name = "PARTITION_DATE")
    private LocalDateTime partitionDate;

    // ── Getters & Setters ──

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    public String getSubType() { return subType; }
    public void setSubType(String subType) { this.subType = subType; }

    public Long getOrderUnitId() { return orderUnitId; }
    public void setOrderUnitId(Long orderUnitId) { this.orderUnitId = orderUnitId; }

    public LocalDateTime getCtdbCreDatetime() { return ctdbCreDatetime; }
    public void setCtdbCreDatetime(LocalDateTime ctdbCreDatetime) { this.ctdbCreDatetime = ctdbCreDatetime; }

    public LocalDateTime getCtdbUpdDatetime() { return ctdbUpdDatetime; }
    public void setCtdbUpdDatetime(LocalDateTime ctdbUpdDatetime) { this.ctdbUpdDatetime = ctdbUpdDatetime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getSalesChannel() { return salesChannel; }
    public void setSalesChannel(String salesChannel) { this.salesChannel = salesChannel; }

    public Integer getReasonId() { return reasonId; }
    public void setReasonId(Integer reasonId) { this.reasonId = reasonId; }

    public String getCancelAllowed() { return cancelAllowed; }
    public void setCancelAllowed(String cancelAllowed) { this.cancelAllowed = cancelAllowed; }

    public String getAmendAllowedInd() { return amendAllowedInd; }
    public void setAmendAllowedInd(String amendAllowedInd) { this.amendAllowedInd = amendAllowedInd; }

    public String getFormId() { return formId; }
    public void setFormId(String formId) { this.formId = formId; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public LocalDateTime getExecutionDate() { return executionDate; }
    public void setExecutionDate(LocalDateTime executionDate) { this.executionDate = executionDate; }

    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }

    public Long getStepInstanceId() { return stepInstanceId; }
    public void setStepInstanceId(Long stepInstanceId) { this.stepInstanceId = stepInstanceId; }

    public String getIsException() { return isException; }
    public void setIsException(String isException) { this.isException = isException; }

    public Long getProductDefId() { return productDefId; }
    public void setProductDefId(Long productDefId) { this.productDefId = productDefId; }

    public Integer getMainInd() { return mainInd; }
    public void setMainInd(Integer mainInd) { this.mainInd = mainInd; }

    public LocalDateTime getPartitionDate() { return partitionDate; }
    public void setPartitionDate(LocalDateTime partitionDate) { this.partitionDate = partitionDate; }
}
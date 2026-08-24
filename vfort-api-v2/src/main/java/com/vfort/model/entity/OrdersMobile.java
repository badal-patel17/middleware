package com.vfort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tborder_Action", schema = "ROHIT")
public class OrdersMobile {

    @Id
    @Column(name = "ORDER_UNIT_ID")
    private Long orderUnitId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Column(name = "SUB_TYPE")
    private String subType;

    @Column(name = "AP_ID")
    private String apId;

    @Column(name = "ORDER_ID")
    private Long orderId;

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

    @Column(name = "MESSAGE_TEXT")
    private String messageText;

    // ── Getters & Setters ──

    public Long getOrderUnitId() { return orderUnitId; }
    public void setOrderUnitId(Long orderUnitId) { this.orderUnitId = orderUnitId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    public String getSubType() { return subType; }
    public void setSubType(String subType) { this.subType = subType; }

    public String getApId() { return apId; }
    public void setApId(String apId) { this.apId = apId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

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

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
}
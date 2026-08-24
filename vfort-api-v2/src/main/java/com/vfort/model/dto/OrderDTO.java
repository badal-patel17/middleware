package com.vfort.model.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * What the React frontend receives for an order.
 * Merges Oracle business data + Flowable process status.
 */
public class OrderDTO {

    // ── Oracle Fields ──────────────────────────────────────────────
    private String customerId;
    private String customerType;
    private String subType;
    private String apId;
    private String orderId;
    private String orderUnitId;

    private Instant createdAt;     // CTDB_CRE_DATETIME
    private Instant updatedAt;     // CTDB_UPD_DATETIME

    private String status;
    private String actionType;
    private String salesChannel;
    private String reasonId;
    private String cancelAllowed;
    private String amendAllowedInd;

    private String formId;
    private String state;
    private Instant executionDate;
    private Instant completionDate;
    private String stepInstanceId;
    private Boolean isException;
    private String messageText;

    // Additional Business Fields
    private String customerName;
    private String customerEmail;
    private Double totalAmount;

    // ── Flowable Fields ────────────────────────────────────────────
//    private String processInstanceId;
//    private String processStatus;
    private String currentTask;
    private String currentAssignee;

    // ── Fallout Details ────────────────────────────────────────────
    private List<FalloutDTO> fallouts;

    public OrderDTO() {
    }

    // ===========================
    // Getters & Setters
    // ===========================

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderUnitId() {
        return orderUnitId;
    }

    public void setOrderUnitId(String orderUnitId) {
        this.orderUnitId = orderUnitId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getCancelAllowed() {
        return cancelAllowed;
    }

    public void setCancelAllowed(String cancelAllowed) {
        this.cancelAllowed = cancelAllowed;
    }

    public String getAmendAllowedInd() {
        return amendAllowedInd;
    }

    public void setAmendAllowedInd(String amendAllowedInd) {
        this.amendAllowedInd = amendAllowedInd;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Instant getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Instant executionDate) {
        this.executionDate = executionDate;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public String getStepInstanceId() {
        return stepInstanceId;
    }

    public void setStepInstanceId(String stepInstanceId) {
        this.stepInstanceId = stepInstanceId;
    }

    public Boolean getIsException() {
        return isException;
    }

    public void setIsException(Boolean isException) {
        this.isException = isException;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public String getCustomerEmail() {
//        return customerEmail;
//    }
//
//    public void setCustomerEmail(String customerEmail) {
//        this.customerEmail = customerEmail;
//    }
//
//    public Double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(Double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getProcessInstanceId() {
//        return processInstanceId;
//    }
//
//    public void setProcessInstanceId(String processInstanceId) {
//        this.processInstanceId = processInstanceId;
//    }
//
//    public String getProcessStatus() {
//        return processStatus;
//    }

//    public void setProcessStatus(String processStatus) {
//        this.processStatus = processStatus;
//    }
//
//    public String getCurrentTask() {
//        return currentTask;
//    }
//
//    public void setCurrentTask(String currentTask) {
//        this.currentTask = currentTask;
//    }
//
//    public String getCurrentAssignee() {
//        return currentAssignee;
//    }
//
//    public void setCurrentAssignee(String currentAssignee) {
//        this.currentAssignee = currentAssignee;
//    }
//
//    public List<FalloutDTO> getFallouts() {
//        return fallouts;
//    }
//
//    public void setFallouts(List<FalloutDTO> fallouts) {
//        this.fallouts = fallouts;
//    }
}
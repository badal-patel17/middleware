package com.vfort.model.dto;

import java.time.LocalDateTime;

/**
 * What the React frontend receives for a fallout.
 * Merges Oracle fallout data + Flowable task/process status.
 */
public class FalloutDTO {

    // ── From Oracle ──
    private Long id;
    private String orderId;
    private String errorCode;
    private String errorMessage;
    private String falloutType;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    // ── From Flowable ──
    private String processInstanceId;
    private String processStatus;       // RUNNING, COMPLETED, null
    private String currentTask;         // e.g. "Manual Review", "Retry Payment"
    private String currentAssignee;     // user handling it in Flowable Task App

    // ── Getters & Setters ──
    public Long getId()                            { return id; }
    public void setId(Long id)                     { this.id = id; }
    public String getOrderId()                     { return orderId; }
    public void setOrderId(String o)               { this.orderId = o; }
    public String getErrorCode()                   { return errorCode; }
    public void setErrorCode(String e)             { this.errorCode = e; }
    public String getErrorMessage()                { return errorMessage; }
    public void setErrorMessage(String m)          { this.errorMessage = m; }
    public String getFalloutType()                 { return falloutType; }
    public void setFalloutType(String t)           { this.falloutType = t; }
    public String getStatus()                      { return status; }
    public void setStatus(String s)                { this.status = s; }
    public LocalDateTime getCreatedAt()            { return createdAt; }
    public void setCreatedAt(LocalDateTime d)      { this.createdAt = d; }
    public LocalDateTime getResolvedAt()           { return resolvedAt; }
    public void setResolvedAt(LocalDateTime d)     { this.resolvedAt = d; }
    public String getProcessInstanceId()           { return processInstanceId; }
    public void setProcessInstanceId(String p)     { this.processInstanceId = p; }
    public String getProcessStatus()               { return processStatus; }
    public void setProcessStatus(String p)         { this.processStatus = p; }
    public String getCurrentTask()                 { return currentTask; }
    public void setCurrentTask(String t)           { this.currentTask = t; }
    public String getCurrentAssignee()             { return currentAssignee; }
    public void setCurrentAssignee(String a)       { this.currentAssignee = a; }
}

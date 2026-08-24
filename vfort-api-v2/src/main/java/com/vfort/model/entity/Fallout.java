package com.vfort.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Maps to your Oracle FALLOUTS table.
 */
@Entity
@Table(name = "FALLOUTS")
public class Fallout {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fallout_seq")
    @SequenceGenerator(name = "fallout_seq", sequenceName = "FALLOUT_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    @Column(name = "FALLOUT_TYPE")
    private String falloutType;      // PAYMENT_FAIL, INVENTORY_ERR, NETWORK_ERR, UNKNOWN

    @Column(name = "STATUS")
    private String status;           // PENDING, IN_PROGRESS, RESOLVED, FAILED

    @Column(name = "PROCESS_INSTANCE_ID")
    private String processInstanceId; // Flowable process instance ID — links the two systems

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "RESOLVED_AT")
    private LocalDateTime resolvedAt;

    // ── Getters & Setters ──
    public Long getId()                            { return id; }
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
    public String getProcessInstanceId()           { return processInstanceId; }
    public void setProcessInstanceId(String p)     { this.processInstanceId = p; }
    public LocalDateTime getCreatedAt()            { return createdAt; }
    public void setCreatedAt(LocalDateTime d)      { this.createdAt = d; }
    public LocalDateTime getResolvedAt()           { return resolvedAt; }
    public void setResolvedAt(LocalDateTime d)     { this.resolvedAt = d; }
}

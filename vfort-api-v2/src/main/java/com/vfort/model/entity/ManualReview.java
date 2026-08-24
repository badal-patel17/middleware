package com.vfort.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vfort_manual_review")
public class ManualReview {

    @Id
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "step_instance_id")
    private String stepInstanceId;

    @Column(name = "fallout_type")
    private String falloutType;

    @Column(name = "attempt_count")
    private Integer attemptCount;

    @Column(name = "last_error")
    private String lastError;

    @Column(name = "first_failed_at")
    private LocalDateTime firstFailedAt;

    @Column(name = "last_failed_at")
    private LocalDateTime lastFailedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "resolved_by")
    private String resolvedBy;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "error_classification")
    private String errorClassification;

    @Column(name = "correlation_id")
    private String correlationId;

    public ManualReview() {
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStepInstanceId() {
        return stepInstanceId;
    }

    public void setStepInstanceId(String stepInstanceId) {
        this.stepInstanceId = stepInstanceId;
    }

    public String getFalloutType() {
        return falloutType;
    }

    public void setFalloutType(String falloutType) {
        this.falloutType = falloutType;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public LocalDateTime getFirstFailedAt() {
        return firstFailedAt;
    }

    public void setFirstFailedAt(LocalDateTime firstFailedAt) {
        this.firstFailedAt = firstFailedAt;
    }

    public LocalDateTime getLastFailedAt() {
        return lastFailedAt;
    }

    public void setLastFailedAt(LocalDateTime lastFailedAt) {
        this.lastFailedAt = lastFailedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public String getErrorClassification() {
        return errorClassification;
    }

    public void setErrorClassification(String errorClassification) {
        this.errorClassification = errorClassification;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
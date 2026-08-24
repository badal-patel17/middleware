package com.vfort.repository;

import java.time.Instant;
import java.time.LocalDateTime;

public interface OrdersMobileRepository {

    String getCustomerId();

    String getCustomerType();

    String getSubType();

    String getApId();

    String getOrderId();

    String getOrderUnitId();

    // Using Instant ensures Spring Data / Jackson preserves the UTC 'Z' timestamp
    Instant getCreDateTime();


    Instant getUpdDateTime();


    String getStatus();


    String getActionType();

    String getSalesChannel();


    String getReasonId();


    String getCancelAllowed();


    String getAmendAllowedInd();


    String getFormId();


    String getState();


    Instant getExecutionDate();


    Instant getCompletionDate();


    String getStepInstanceId();


    Boolean getIsException();


    String getMessageText();

    String getCustomerEmail();
}
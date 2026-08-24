package com.vfort.repository;

import com.vfort.model.entity.FailedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedOrderRepository
        extends JpaRepository<FailedOrder, Long> {
}
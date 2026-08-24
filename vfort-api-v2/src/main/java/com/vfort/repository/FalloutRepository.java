package com.vfort.repository;

import com.vfort.model.entity.Fallout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FalloutRepository extends JpaRepository<Fallout, Long> {

    List<Fallout> findByStatus(String status);

    List<Fallout> findByOrderId(String orderId);

    List<Fallout> findByStatusOrderByCreatedAtDesc(String status);
}

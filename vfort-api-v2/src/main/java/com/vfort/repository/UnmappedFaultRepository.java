package com.vfort.repository;

import com.vfort.model.entity.UnmappedFault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnmappedFaultRepository
        extends JpaRepository<UnmappedFault, Long> {
}
package com.vfort.repository;

import com.vfort.model.entity.ManualReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualReviewRepository
        extends JpaRepository<ManualReview, Integer> {
}
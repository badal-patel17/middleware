package com.vfort.controller;

import com.vfort.model.entity.ManualReview;
import com.vfort.repository.ManualReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ManualReviewController {

    @Autowired
    private ManualReviewRepository repository;

    @GetMapping("/manual-review")
    public List<ManualReview> getManualReviews() {
        return repository.findAll();
    }
}
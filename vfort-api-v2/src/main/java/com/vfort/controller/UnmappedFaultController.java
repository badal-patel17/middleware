package com.vfort.controller;

import com.vfort.model.entity.UnmappedFault;
import com.vfort.repository.UnmappedFaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UnmappedFaultController {

    @Autowired
    private UnmappedFaultRepository repository;

    @GetMapping("/unmapped-faults")
    public List<UnmappedFault> getUnmappedFaults() {
        return repository.findAll();
    }
}
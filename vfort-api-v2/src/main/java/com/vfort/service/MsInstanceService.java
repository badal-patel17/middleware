package com.vfort.service;

import com.vfort.repository.MsInstanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MsInstanceService {

    private final MsInstanceRepository repository;

    public MsInstanceService(MsInstanceRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> getAllMsInstances() {
        return repository.findAll();
    }
}
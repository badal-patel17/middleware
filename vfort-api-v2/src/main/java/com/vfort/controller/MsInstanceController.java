package com.vfort.controller;

import com.vfort.service.MsInstanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ms-instances")
public class MsInstanceController {

    private final MsInstanceService service;

    public MsInstanceController(MsInstanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> getAllMsInstances() {
        return service.getAllMsInstances();
    }
}
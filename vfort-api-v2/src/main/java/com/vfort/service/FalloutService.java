package com.vfort.service;

import com.vfort.model.dto.FalloutDTO;
import com.vfort.model.entity.Fallout;
import com.vfort.repository.FalloutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FalloutService {

    @Autowired private FalloutRepository falloutRepository;
    @Autowired private FlowableClient flowableClient;

    // ── All fallouts with Flowable status merged ──
    public List<FalloutDTO> getAllFallouts() {
        return falloutRepository.findAll()
                .stream()
                .map(this::toEnrichedDTO)
                .collect(Collectors.toList());
    }

    // ── Fallouts by status ──
    public List<FalloutDTO> getFalloutsByStatus(String status) {
        return falloutRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::toEnrichedDTO)
                .collect(Collectors.toList());
    }

    // ── Fallouts for a specific order ──
    public List<FalloutDTO> getFalloutsByOrder(String orderId) {
        return falloutRepository.findByOrderId(orderId)
                .stream()
                .map(this::toEnrichedDTO)
                .collect(Collectors.toList());
    }

    // ── Trigger Flowable process for a fallout ──
    public FalloutDTO triggerProcess(Long falloutId) {
        Fallout fallout = falloutRepository.findById(falloutId)
                .orElseThrow(() -> new RuntimeException("Fallout not found: " + falloutId));

        // Start Flowable process with fallout data as variables
        String processInstanceId = flowableClient.startProcess(
                "falloutHandler",
                Map.of(
                    "falloutId",   fallout.getId(),
                    "orderId",     fallout.getOrderId(),
                    "falloutType", fallout.getFalloutType(),
                    "errorCode",   fallout.getErrorCode()
                )
        );

        // Store the process ID back in Oracle — this is the LINK between systems
        fallout.setProcessInstanceId(processInstanceId);
        fallout.setStatus("IN_PROGRESS");
        falloutRepository.save(fallout);

        return toEnrichedDTO(fallout);
    }

    // ── Convert + merge Oracle + Flowable ──
    private FalloutDTO toEnrichedDTO(Fallout fallout) {
        FalloutDTO dto = new FalloutDTO();
        dto.setId(fallout.getId());
        dto.setOrderId(fallout.getOrderId());
        dto.setErrorCode(fallout.getErrorCode());
        dto.setErrorMessage(fallout.getErrorMessage());
        dto.setFalloutType(fallout.getFalloutType());
        dto.setStatus(fallout.getStatus());
        dto.setCreatedAt(fallout.getCreatedAt());
        dto.setResolvedAt(fallout.getResolvedAt());
        dto.setProcessInstanceId(fallout.getProcessInstanceId());

        // Enrich with Flowable data if process exists
        if (fallout.getProcessInstanceId() != null) {
            Map<String, Object> process = flowableClient
                    .getProcessById(fallout.getProcessInstanceId());

            if (process != null) {
                dto.setProcessStatus("RUNNING");

                Map<String, Object> task = flowableClient
                        .getCurrentTask(fallout.getProcessInstanceId());

                if (task != null) {
                    dto.setCurrentTask((String) task.get("name"));
                    dto.setCurrentAssignee((String) task.get("assignee"));
                }
            } else {
                Map<String, Object> historic = flowableClient
                        .getHistoricProcess(fallout.getProcessInstanceId());
                if (historic != null) {
                    dto.setProcessStatus("COMPLETED");
                    dto.setCurrentTask("Resolved");
                }
            }
        }

        return dto;
    }
}

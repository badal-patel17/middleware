package com.vfort.service;

import com.vfort.model.dto.FalloutDTO;
import com.vfort.model.dto.OrderDTO;
import com.vfort.model.entity.Fallout;
import com.vfort.model.entity.Order;
import com.vfort.model.entity.OrdersMobile;
import com.vfort.repository.FalloutRepository;
import com.vfort.repository.OrderRepository;
import com.vfort.repository.OrdersMobileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.vfort.repository.FailedOrderRepository;
import com.vfort.repository.UnmappedFaultRepository;
import com.vfort.repository.ManualReviewRepository;
@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private FalloutRepository falloutRepository;
    @Autowired private FlowableClient flowableClient;

    // ── Get all orders (list view) ──
//    public List<OrderDTO> getAllOrders() {
//        return orderRepository.findAll()
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    //Fetch Mobile Orders
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findMobileOrders()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Get orders by status ──
//    public List<OrderDTO> getOrdersByStatus(String status) {
//        return orderRepository.findByStatus(status)
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    // ── Get single order (detail view) — full merge with Flowable ──
//    public OrderDTO getOrderById(String orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
//
//        OrderDTO dto = toDTO(order);
//
//        // Fetch fallouts for this order from Oracle
//        List<Fallout> fallouts = falloutRepository.findByOrderId(orderId);
//        dto.setFallouts(fallouts.stream()
//                .map(this::falloutToDTO)
//                .collect(Collectors.toList()));
//
//        // Enrich each fallout with Flowable status
//        if (dto.getFallouts() != null) {
//            dto.getFallouts().forEach(this::enrichFalloutWithFlowable);
//        }
//
//        return dto;
//    }

    // ── Convert Order entity → OrderDTO ──
    private OrderDTO toDTO(OrdersMobileRepository orderMobile) {
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(orderMobile.getCustomerId());
        dto.setCustomerType(orderMobile.getCustomerType());
        dto.setSubType(orderMobile.getSubType());
        dto.setApId(orderMobile.getApId());
        dto.setOrderId(orderMobile.getOrderId());
        dto.setOrderUnitId(orderMobile.getOrderUnitId());
        dto.setCreatedAt(orderMobile.getCreDateTime());
        dto.setUpdatedAt(orderMobile.getUpdDateTime());
        dto.setActionType(orderMobile.getActionType());
        dto.setSalesChannel(orderMobile.getSalesChannel());
        dto.setReasonId(orderMobile.getReasonId());
        dto.setCancelAllowed(orderMobile.getCancelAllowed());
        dto.setAmendAllowedInd(orderMobile.getAmendAllowedInd());
        dto.setFormId(orderMobile.getFormId());
        dto.setState(orderMobile.getState());
        dto.setStatus(orderMobile.getStatus());
        dto.setExecutionDate(orderMobile.getExecutionDate());
        dto.setCompletionDate(orderMobile.getCompletionDate());
        dto.setStepInstanceId(orderMobile.getStepInstanceId());
        dto.setIsException(orderMobile.getIsException());
        dto.setMessageText(orderMobile.getMessageText());
        return dto;
    }

    // ── Convert Fallout entity → FalloutDTO ──
    private FalloutDTO falloutToDTO(Fallout fallout) {
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
        return dto;
    }

    @Autowired
    private FailedOrderRepository failedOrderRepository;

    @Autowired
    private UnmappedFaultRepository unmappedFaultRepository;

    @Autowired
    private ManualReviewRepository manualReviewRepository;
    /**
     * Enriches a FalloutDTO with live Flowable process/task status.
     * This is the MERGE step — Oracle data + Flowable data combined.
     */
    private void enrichFalloutWithFlowable(FalloutDTO dto) {
        if (dto.getProcessInstanceId() == null) return;

        // Try active process first
        Map<String, Object> process = flowableClient
                .getProcessById(dto.getProcessInstanceId());

        if (process != null) {
            dto.setProcessStatus("RUNNING");

            // Get current task (where in BPMN flow is it now?)
            Map<String, Object> task = flowableClient
                    .getCurrentTask(dto.getProcessInstanceId());

            if (task != null) {
                dto.setCurrentTask((String) task.get("name"));
                dto.setCurrentAssignee((String) task.get("assignee"));
            }
        } else {
            // Check historic (completed process)
            Map<String, Object> historic = flowableClient
                    .getHistoricProcess(dto.getProcessInstanceId());

            if (historic != null) {
                dto.setProcessStatus("COMPLETED");
                dto.setCurrentTask("Process Completed");
            }
        }
    }
}

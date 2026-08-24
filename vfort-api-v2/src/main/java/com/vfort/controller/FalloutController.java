package com.vfort.controller;

import com.vfort.model.dto.FalloutDTO;
import com.vfort.service.FalloutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Fallouts.
 * Base URL: http://localhost:8090/vfort/api/fallouts
 */
@RestController
@RequestMapping("/api/fallouts")
@CrossOrigin(origins = "http://localhost:85")
public class FalloutController {

    @Autowired
    private FalloutService falloutService;

    /**
     * GET /api/fallouts
     * GET /api/fallouts?status=PENDING
     *
     * Returns all fallouts merged with Flowable process status.
     * Used for the fallout dashboard in React.
     */
    @GetMapping
    public ResponseEntity<List<FalloutDTO>> getFallouts(
            @RequestParam(required = false) String status) {

        List<FalloutDTO> fallouts = (status != null)
                ? falloutService.getFalloutsByStatus(status)
                : falloutService.getAllFallouts();

        return ResponseEntity.ok(fallouts);
    }

    /**
     * GET /api/fallouts/order/{orderId}
     *
     * Returns all fallouts for a specific order.
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<FalloutDTO>> getFalloutsByOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(falloutService.getFalloutsByOrder(orderId));
    }

    /**
     * POST /api/fallouts/{falloutId}/trigger
     *
     * Manually trigger a Flowable process for a fallout.
     * Useful for manual retry from the React UI.
     */
    @PostMapping("/{falloutId}/trigger")
    public ResponseEntity<FalloutDTO> triggerProcess(
            @PathVariable Long falloutId) {

        return ResponseEntity.ok(falloutService.triggerProcess(falloutId));
    }
}

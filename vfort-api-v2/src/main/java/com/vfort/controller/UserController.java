package com.vfort.controller;

import com.vfort.model.dto.CreateUserRequest;
import com.vfort.model.dto.UserDTO;
import com.vfort.service.FlowableClient;
import com.vfort.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * User REST endpoints.
 * Base URL: http://localhost:8090/vfort/api/users
 *
 * Data sources:
 *   - Flowable IDM  → primary (user CRUD, groups, task assignments)
 *   - Oracle DB     → read-only enrichment (role, status, business fields)
 *                     Oracle CRUD is handled by the client's existing system.
 *
 * All endpoints require:  Authorization: Bearer <jwt-token>
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:85")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private FlowableClient flowableClient;

    // ══════════════════════════════════════
    //  READ
    // ══════════════════════════════════════

    /**
     * GET /api/users
     * GET /api/users?group=payment-team
     *
     * Returns all users from Flowable IDM,
     * optionally enriched with Oracle business fields (role, status).
     *
     * Used for: User management table in React.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(
            @RequestParam(required = false) String group) {

        if (group != null) {
            return ResponseEntity.ok(userService.getUsersByGroup(group));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET /api/users/{username}
     *
     * Full user detail:
     *   - Flowable IDM profile
     *   - Active tasks currently assigned to this user
     *   - Groups the user belongs to
     *   - Oracle business fields (commented — uncomment to enable)
     *
     * Used for: User detail / profile page in React.
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    // ══════════════════════════════════════
    //  CREATE
    // ══════════════════════════════════════

    /**
     * POST /api/users
     *
     * Creates user in Flowable IDM.
     * Oracle user creation is the client's responsibility.
     *
     * Body: {
     *   "username": "jdoe",
     *   "fullName": "John Doe",
     *   "email": "john@example.com",
     *   "role": "AGENT",
     *   "password": "secret123"
     * }
     *
     * Used for: Create user form in React.
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    // ══════════════════════════════════════
    //  UPDATE
    // ══════════════════════════════════════

    /**
     * PUT /api/users/{username}
     *
     * Updates user in Flowable IDM.
     * Oracle update is the client's responsibility.
     *
     * Body (partial — only send fields to change):
     * { "email": "newemail@example.com", "firstName": "Johnny" }
     *
     * Used for: Edit user form in React.
     */
    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String username,
            @RequestBody Map<String, String> updates) {
        return ResponseEntity.ok(userService.updateUser(username, updates));
    }

    // ══════════════════════════════════════
    //  DELETE
    // ══════════════════════════════════════

    /**
     * DELETE /api/users/{username}
     *
     * Removes user from Flowable IDM.
     * Oracle delete is the client's responsibility.
     *
     * Used for: Delete user action in React.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of(
                "message", "User removed from Flowable IDM: " + username));
    }

    // ══════════════════════════════════════
    //  GROUP MANAGEMENT
    // ══════════════════════════════════════

    /**
     * GET /api/users/groups
     *
     * Returns all Flowable groups.
     * Used for: Group assignment dropdown in React.
     * Examples: "payment-team", "inventory-team", "managers"
     */
    @GetMapping("/groups")
    public ResponseEntity<List<Map<String, Object>>> getAllGroups() {
        return ResponseEntity.ok(flowableClient.getAllGroups());
    }

    /**
     * POST /api/users/{username}/groups/{groupId}
     *
     * Assigns user to a Flowable group.
     * This controls which BPMN tasks get routed to this user.
     *
     * Used for: Assign to group button in React.
     */
    @PostMapping("/{username}/groups/{groupId}")
    public ResponseEntity<Map<String, String>> assignToGroup(
            @PathVariable String username,
            @PathVariable String groupId) {
        userService.assignToGroup(username, groupId);
        return ResponseEntity.ok(Map.of(
                "message", username + " assigned to group: " + groupId));
    }

    /**
     * DELETE /api/users/{username}/groups/{groupId}
     *
     * Removes user from a Flowable group.
     *
     * Used for: Remove from group action in React.
     */
    @DeleteMapping("/{username}/groups/{groupId}")
    public ResponseEntity<Map<String, String>> removeFromGroup(
            @PathVariable String username,
            @PathVariable String groupId) {
        userService.removeFromGroup(username, groupId);
        return ResponseEntity.ok(Map.of(
                "message", username + " removed from group: " + groupId));
    }
}

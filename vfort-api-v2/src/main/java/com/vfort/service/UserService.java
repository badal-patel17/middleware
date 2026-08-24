package com.vfort.service;

import com.vfort.model.dto.CreateUserRequest;
import com.vfort.model.dto.UserDTO;
import com.vfort.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User Service — Architecture:
 *
 *   Oracle DB  →  Read-only (client already handles their own CRUD)
 *                 Commented out sections show WHERE to wire Oracle if needed.
 *
 *   Flowable IDM → Primary source for user management in this API:
 *                  create, update, delete, groups, tasks
 *
 *   Merge       →  Oracle business fields + Flowable task/group data
 *                  combined into one clean UserDTO for the frontend.
 */
@Service
public class UserService {

    @Autowired private FlowableClient flowableClient;

    // ─────────────────────────────────────────────────────────────
    // Oracle UserRepository is available but intentionally NOT used
    // for writes — the client's existing system handles Oracle CRUD.
    //
    // Uncomment below if you need to READ from Oracle:
    // @Autowired private UserRepository userRepository;
    // ─────────────────────────────────────────────────────────────


    // ══════════════════════════════════════
    //  READ — Get users
    // ══════════════════════════════════════

    /**
     * Get all users.
     * Primary source: Flowable IDM.
     *
     * To also merge Oracle business data, uncomment the Oracle block below.
     */
    @SuppressWarnings("unchecked")
    public List<UserDTO> getAllUsers() {
        List<Map<String, Object>> flowableUsers = flowableClient.getAllFlowableUsers();

        return flowableUsers.stream()
                .map(this::fromFlowableUser)
                // ── Optional: enrich with Oracle data ──────────────────────
                // .map(dto -> enrichWithOracleData(dto))
                // ───────────────────────────────────────────────────────────
                .collect(Collectors.toList());
    }

    /**
     * Get users by Flowable group (e.g. "payment-team", "managers").
     */
    public List<UserDTO> getUsersByGroup(String groupId) {
        List<Map<String, Object>> members = flowableClient.getUsersInGroup(groupId);
        return members.stream()
                .map(this::fromFlowableUser)
                .collect(Collectors.toList());
    }

    /**
     * Get full user detail — Flowable IDM + active tasks + groups merged.
     * Optionally enriched with Oracle data.
     */
    @SuppressWarnings("unchecked")
    public UserDTO getUserByUsername(String username) {
        // Fetch from Flowable IDM
        Map<String, Object> flowableUser = flowableClient.getFlowableUser(username);
        if (flowableUser == null) {
            throw new RuntimeException("User not found in Flowable IDM: " + username);
        }

        UserDTO dto = fromFlowableUser(flowableUser);

        // Merge active tasks assigned to this user
        List<Map<String, Object>> tasks = flowableClient.getTasksForUser(username);
        if (tasks != null && !tasks.isEmpty()) {
            dto.setActiveTaskCount(tasks.size());
            dto.setActiveTasks(tasks.stream().map(t -> {
                UserDTO.TaskSummary s = new UserDTO.TaskSummary();
                s.setTaskId((String) t.get("id"));
                s.setTaskName((String) t.get("name"));
                s.setCreatedDate((String) t.get("createTime"));
                return s;
            }).collect(Collectors.toList()));
        }

        // Merge Flowable groups
        List<Map<String, Object>> groups = flowableClient.getGroupsForUser(username);
        if (groups != null) {
            dto.setFlowableGroups(groups.stream()
                    .map(g -> (String) g.get("id"))
                    .collect(Collectors.toList()));
        }

        // ── Optional: enrich with Oracle data ──────────────────────────────
        // userRepository.findByUsername(username).ifPresent(oracleUser -> {
        //     dto.setRole(oracleUser.getRole());
        //     dto.setStatus(oracleUser.getStatus());
        //     dto.setCreatedAt(oracleUser.getCreatedAt());
        // });
        // ────────────────────────────────────────────────────────────────────

        return dto;
    }


    // ══════════════════════════════════════
    //  CREATE — Flowable IDM only
    //  (Oracle create is client's responsibility)
    // ══════════════════════════════════════

    /**
     * Creates user in Flowable IDM so they can:
     *  - Be assigned to BPMN tasks
     *  - Appear in Flowable Task App
     *  - Be part of groups for task routing
     *
     * Oracle user record is managed by the client's existing system.
     * If you need to also write to Oracle, uncomment the block below.
     */
    public UserDTO createUser(CreateUserRequest request) {
        // Create in Flowable IDM
        flowableClient.createFlowableUser(
                request.getUsername(),
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );

        // ── Optional: also create in Oracle ────────────────────────────────
        // User user = new User();
        // user.setUsername(request.getUsername());
        // user.setEmail(request.getEmail());
        // user.setFullName(request.getFullName());
        // user.setRole(request.getRole());
        // user.setStatus("ACTIVE");
        // user.setPassword(passwordEncoder.encode(request.getPassword()));
        // user.setCreatedAt(LocalDateTime.now());
        // userRepository.save(user);
        // ────────────────────────────────────────────────────────────────────

        // Return the created user merged from Flowable
        return getUserByUsername(request.getUsername());
    }


    // ══════════════════════════════════════
    //  UPDATE — Flowable IDM only
    // ══════════════════════════════════════

    /**
     * Updates user in Flowable IDM.
     * Oracle updates are the client's responsibility.
     */
    public UserDTO updateUser(String username, Map<String, String> updates) {
        flowableClient.updateFlowableUser(username, updates);

        // ── Optional: also update Oracle ───────────────────────────────────
        // userRepository.findByUsername(username).ifPresent(user -> {
        //     if (updates.containsKey("email"))    user.setEmail(updates.get("email"));
        //     if (updates.containsKey("role"))     user.setRole(updates.get("role"));
        //     if (updates.containsKey("status"))   user.setStatus(updates.get("status"));
        //     user.setUpdatedAt(LocalDateTime.now());
        //     userRepository.save(user);
        // });
        // ────────────────────────────────────────────────────────────────────

        return getUserByUsername(username);
    }


    // ══════════════════════════════════════
    //  DELETE — Flowable IDM only
    // ══════════════════════════════════════

    /**
     * Removes user from Flowable IDM.
     * Oracle delete is the client's responsibility.
     */
    public void deleteUser(String username) {
        flowableClient.deleteFlowableUser(username);

        // ── Optional: also delete from Oracle ──────────────────────────────
        // userRepository.findByUsername(username).ifPresent(userRepository::delete);
        // ────────────────────────────────────────────────────────────────────
    }


    // ══════════════════════════════════════
    //  GROUP MANAGEMENT
    // ══════════════════════════════════════

    public void assignToGroup(String username, String groupId) {
        flowableClient.addUserToGroup(username, groupId);
    }

    public void removeFromGroup(String username, String groupId) {
        flowableClient.removeUserFromGroup(username, groupId);
    }


    // ══════════════════════════════════════
    //  HELPER — Map Flowable user → UserDTO
    // ══════════════════════════════════════

    private UserDTO fromFlowableUser(Map<String, Object> f) {
        UserDTO dto = new UserDTO();
        dto.setUsername((String) f.get("id"));
        dto.setEmail((String) f.get("email"));

        String first = (String) f.getOrDefault("firstName", "");
        String last  = (String) f.getOrDefault("lastName", "");
        dto.setFullName((first + " " + last).trim());

        // ── Optional: pull role/status from Oracle by username ─────────────
        // userRepository.findByUsername(dto.getUsername()).ifPresent(u -> {
        //     dto.setRole(u.getRole());
        //     dto.setStatus(u.getStatus());
        //     dto.setCreatedAt(u.getCreatedAt());
        // });
        // ────────────────────────────────────────────────────────────────────

        return dto;
    }
}

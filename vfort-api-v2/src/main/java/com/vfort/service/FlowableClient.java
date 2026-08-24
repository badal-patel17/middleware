package com.vfort.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class FlowableClient {

    private final WebClient webClient;

    public FlowableClient(
            @Value("${flowable.rest.base-url}") String baseUrl,
            @Value("${flowable.rest.username}") String username,
            @Value("${flowable.rest.password}") String password) {

        String credentials = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes());

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + credentials)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // ── Process Instances ──

    @SuppressWarnings("unchecked")
    public Map<String, Object> getProcessById(String id) {
        try { return webClient.get().uri("/process-api/repository/process-definitions/" + id).retrieve().bodyToMono(Map.class).block(); }
        catch (Exception e) { return null; }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getHistoricProcess(String id) {
        try { return webClient.get().uri("/history/historic-process-instances/" + id).retrieve().bodyToMono(Map.class).block(); }
        catch (Exception e) { return null; }
    }

    @SuppressWarnings("unchecked")
    public String startProcess(String processKey, Map<String, Object> variables) {
        List<Map<String, Object>> vars = variables.entrySet().stream()
                .map(e -> Map.of("name", e.getKey(), "value", e.getValue())).toList();
        Map<String, Object> resp = webClient.post().uri("/runtime/process-instances")
                .bodyValue(Map.of("processDefinitionKey", processKey, "variables", vars))
                .retrieve().bodyToMono(Map.class).block();
        return resp != null ? (String) resp.get("id") : null;
    }

    // ── Tasks ──

    @SuppressWarnings("unchecked")
    public Map<String, Object> getCurrentTask(String processInstanceId) {
        try {
            Map<String, Object> resp = webClient.get().uri("/runtime/tasks?processInstanceId=" + processInstanceId)
                    .retrieve().bodyToMono(Map.class).block();
            if (resp != null) { List<Map<String, Object>> data = (List<Map<String, Object>>) resp.get("data"); if (data != null && !data.isEmpty()) return data.get(0); }
        } catch (Exception e) { System.out.println("[Flowable] No task for: " + processInstanceId); }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getTasksForUser(String username) {
        try {
            Map<String, Object> resp = webClient.get().uri("/runtime/tasks?assignee=" + username).retrieve().bodyToMono(Map.class).block();
            if (resp != null) return (List<Map<String, Object>>) resp.get("data");
        } catch (Exception e) { System.out.println("[Flowable] No tasks for: " + username); }
        return List.of();
    }

    // ── IDM Users ──

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAllFlowableUsers() {
        try { Map<String, Object> r = webClient.get().uri("/identity/users").retrieve().bodyToMono(Map.class).block(); if (r != null) return (List<Map<String, Object>>) r.get("data"); }
        catch (Exception e) { System.out.println("[Flowable IDM] Cannot fetch users"); }
        return List.of();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getFlowableUser(String username) {
        try { return webClient.get().uri("/identity/users/" + username).retrieve().bodyToMono(Map.class).block(); }
        catch (Exception e) { return null; }
    }

    @SuppressWarnings("unchecked")
    public void createFlowableUser(String username, String fullName, String email, String password) {
        String[] p = fullName != null ? fullName.split(" ", 2) : new String[]{"", ""};
        webClient.post().uri("/identity/users")
                .bodyValue(Map.of("id", username, "firstName", p[0], "lastName", p.length > 1 ? p[1] : "", "email", email, "password", password))
                .retrieve().bodyToMono(Map.class).block();
        System.out.println("[Flowable IDM] Created: " + username);
    }

    @SuppressWarnings("unchecked")
    public void updateFlowableUser(String username, Map<String, String> updates) {
        webClient.put().uri("/identity/users/" + username).bodyValue(updates).retrieve().bodyToMono(Map.class).block();
        System.out.println("[Flowable IDM] Updated: " + username);
    }

    public void deleteFlowableUser(String username) {
        try { webClient.delete().uri("/identity/users/" + username).retrieve().bodyToMono(Void.class).block(); System.out.println("[Flowable IDM] Deleted: " + username); }
        catch (Exception e) { System.out.println("[Flowable IDM] Delete failed: " + username); }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getGroupsForUser(String username) {
        try { Map<String, Object> r = webClient.get().uri("/identity/users/" + username + "/groups").retrieve().bodyToMono(Map.class).block(); if (r != null) return (List<Map<String, Object>>) r.get("data"); }
        catch (Exception e) { System.out.println("[Flowable IDM] No groups for: " + username); }
        return List.of();
    }

    // ── IDM Groups ──

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAllGroups() {
        try { Map<String, Object> r = webClient.get().uri("/identity/groups").retrieve().bodyToMono(Map.class).block(); if (r != null) return (List<Map<String, Object>>) r.get("data"); }
        catch (Exception e) { System.out.println("[Flowable IDM] Cannot fetch groups"); }
        return List.of();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUsersInGroup(String groupId) {
        try { Map<String, Object> r = webClient.get().uri("/identity/groups/" + groupId + "/members").retrieve().bodyToMono(Map.class).block(); if (r != null) return (List<Map<String, Object>>) r.get("data"); }
        catch (Exception e) { System.out.println("[Flowable IDM] Cannot fetch members of: " + groupId); }
        return List.of();
    }

    @SuppressWarnings("unchecked")
    public void addUserToGroup(String username, String groupId) {
        webClient.post().uri("/identity/groups/" + groupId + "/members").bodyValue(Map.of("userId", username)).retrieve().bodyToMono(Map.class).block();
        System.out.println("[Flowable IDM] " + username + " added to: " + groupId);
    }

    public void removeUserFromGroup(String username, String groupId) {
        webClient.delete().uri("/identity/groups/" + groupId + "/members/" + username).retrieve().bodyToMono(Void.class).block();
        System.out.println("[Flowable IDM] " + username + " removed from: " + groupId);
    }
}

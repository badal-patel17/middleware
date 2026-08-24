package com.vfort.model.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * What the React frontend receives for a user.
 * Password is NEVER included here — only safe fields.
 *
 * Also merges Flowable IDM data:
 * - flowableGroups: groups the user belongs to in Flowable
 * - activeTasks: tasks currently assigned to this user in Flowable
 */
public class UserDTO {

    // ── From Oracle (business user data) ──
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ── From Flowable IDM (process user data) ──
    private List<String> flowableGroups;      // groups in Flowable IDM
    private List<TaskSummary> activeTasks;    // tasks assigned to this user
    private int activeTaskCount;

    // ── Inner class for task summary ──
    public static class TaskSummary {
        private String taskId;
        private String taskName;
        private String processName;
        private String createdDate;

        public String getTaskId()       { return taskId; }
        public void setTaskId(String t) { this.taskId = t; }
        public String getTaskName()       { return taskName; }
        public void setTaskName(String n) { this.taskName = n; }
        public String getProcessName()       { return processName; }
        public void setProcessName(String p) { this.processName = p; }
        public String getCreatedDate()       { return createdDate; }
        public void setCreatedDate(String d) { this.createdDate = d; }
    }

    // ── Getters & Setters ──
    public Long getId()                              { return id; }
    public void setId(Long id)                       { this.id = id; }
    public String getUsername()                      { return username; }
    public void setUsername(String u)                { this.username = u; }
    public String getEmail()                         { return email; }
    public void setEmail(String e)                   { this.email = e; }
    public String getFullName()                      { return fullName; }
    public void setFullName(String f)                { this.fullName = f; }
    public String getRole()                          { return role; }
    public void setRole(String r)                    { this.role = r; }
    public String getStatus()                        { return status; }
    public void setStatus(String s)                  { this.status = s; }
    public LocalDateTime getCreatedAt()              { return createdAt; }
    public void setCreatedAt(LocalDateTime d)        { this.createdAt = d; }
    public LocalDateTime getUpdatedAt()              { return updatedAt; }
    public void setUpdatedAt(LocalDateTime d)        { this.updatedAt = d; }
    public List<String> getFlowableGroups()          { return flowableGroups; }
    public void setFlowableGroups(List<String> g)    { this.flowableGroups = g; }
    public List<TaskSummary> getActiveTasks()        { return activeTasks; }
    public void setActiveTasks(List<TaskSummary> t)  { this.activeTasks = t; }
    public int getActiveTaskCount()                  { return activeTaskCount; }
    public void setActiveTaskCount(int c)            { this.activeTaskCount = c; }
}

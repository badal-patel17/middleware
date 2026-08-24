package com.vfort.model.dto;

/**
 * Request body for creating/updating a user.
 *
 * Oracle CRUD is handled by the client's existing implementation.
 * This is used only for Flowable IDM operations and read-only Oracle queries.
 */
public class CreateUserRequest {

    private String username;
    private String email;
    private String fullName;
    private String role;       // ADMIN, MANAGER, AGENT
    private String password;   // Plain text — hashed before storing

    public String getUsername()          { return username; }
    public void setUsername(String u)    { this.username = u; }
    public String getEmail()             { return email; }
    public void setEmail(String e)       { this.email = e; }
    public String getFullName()          { return fullName; }
    public void setFullName(String f)    { this.fullName = f; }
    public String getRole()              { return role; }
    public void setRole(String r)        { this.role = r; }
    public String getPassword()          { return password; }
    public void setPassword(String p)    { this.password = p; }
}

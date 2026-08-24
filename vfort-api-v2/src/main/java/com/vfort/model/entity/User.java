package com.vfort.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Maps to your Oracle USERS table (business users).
 *
 * Oracle SQL:
 * CREATE TABLE USERS (
 *     ID          NUMBER DEFAULT USER_SEQ.NEXTVAL PRIMARY KEY,
 *     USERNAME    VARCHAR2(50) UNIQUE NOT NULL,
 *     EMAIL       VARCHAR2(100) UNIQUE NOT NULL,
 *     FULL_NAME   VARCHAR2(100),
 *     ROLE        VARCHAR2(30),       -- ADMIN, MANAGER, AGENT
 *     STATUS      VARCHAR2(20) DEFAULT 'ACTIVE',
 *     PASSWORD    VARCHAR2(255),      -- BCrypt hashed
 *     CREATED_AT  TIMESTAMP DEFAULT SYSTIMESTAMP,
 *     UPDATED_AT  TIMESTAMP DEFAULT SYSTIMESTAMP
 * );
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "ROLE")
    private String role;                 // ADMIN, MANAGER, AGENT

    @Column(name = "STATUS")
    private String status;               // ACTIVE, INACTIVE, SUSPENDED

    @Column(name = "PASSWORD")
    private String password;             // BCrypt hashed — never sent to frontend

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    // ── Getters & Setters ──
    public Long getId()                        { return id; }
    public String getUsername()                { return username; }
    public void setUsername(String u)          { this.username = u; }
    public String getEmail()                   { return email; }
    public void setEmail(String e)             { this.email = e; }
    public String getFullName()                { return fullName; }
    public void setFullName(String f)          { this.fullName = f; }
    public String getRole()                    { return role; }
    public void setRole(String r)              { this.role = r; }
    public String getStatus()                  { return status; }
    public void setStatus(String s)            { this.status = s; }
    public String getPassword()                { return password; }
    public void setPassword(String p)          { this.password = p; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public void setCreatedAt(LocalDateTime d)  { this.createdAt = d; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
    public void setUpdatedAt(LocalDateTime d)  { this.updatedAt = d; }
}

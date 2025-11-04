package com.example.eduquiz.model;

import org.jetbrains.annotations.NotNull;

public class User {
    private int id;
    @NotNull private String username;
    @NotNull private String email;
    @NotNull private String passwordHash;
    private int currentLevel;

    public User(int id, @NotNull String username, @NotNull String email, @NotNull String passwordHash, int currentLevel) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.currentLevel = currentLevel;
    }

    public User(@NotNull String username, @NotNull String email, @NotNull String passwordHash) {
        this(0, username, email, passwordHash, 1);
    }

    public int getId() { return id; }
    public @NotNull String getUsername() { return username; }
    public @NotNull String getEmail() { return email; }
    public @NotNull String getPasswordHash() { return passwordHash; }
    public int getCurrentLevel() { return currentLevel; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", currentLevel=" + currentLevel +
                '}';
    }
}

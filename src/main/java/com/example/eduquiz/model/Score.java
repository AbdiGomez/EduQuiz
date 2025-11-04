package com.example.eduquiz.model;

import java.time.LocalDateTime;

public class Score {
    private int id;
    private int userId;
    private int levelId;
    private int score;
    private LocalDateTime createdAt;

    public Score(int id, int userId, int levelId, int score, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.levelId = levelId;
        this.score = score;
        this.createdAt = createdAt;
    }

    public Score(int userId, int levelId, int score) {
        this(0, userId, levelId, score, LocalDateTime.now());
    }

    public int getUserId() { return userId; }
    public int getLevelId() { return levelId; }
    public int getScore() { return score; }
}

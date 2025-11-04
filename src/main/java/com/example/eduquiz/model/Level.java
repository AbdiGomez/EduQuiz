package com.example.eduquiz.model;

public class Level {
    private int id;
    private String name;
    private String description;
    private int difficulty;

    public Level(int id, String name, String description, int difficulty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public Level(String name, String description, int difficulty) {
        this(0, name, description, difficulty);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getDifficulty() { return difficulty; }

    @Override
    public String toString() {
        return name + " (Dif. " + difficulty + ")";
    }
}

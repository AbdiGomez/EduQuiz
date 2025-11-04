package com.example.eduquiz.model;

public class Question {
    private int id;
    private int levelId;
    private String text;
    private String correctAnswer;
    private String optionA, optionB, optionC, optionD;

    public Question(int id, int levelId, String text, String correctAnswer,
                    String optionA, String optionB, String optionC, String optionD) {
        this.id = id;
        this.levelId = levelId;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public int getId() { return id; }
    public int getLevelId() { return levelId; }
    public String getText() { return text; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
}


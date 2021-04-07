package com.example.schooltips;

public class Question {
    private final int id; // correspond à l'index dans l'ArrayList (QUIZZ_LIST dans Quizz.java)
    private final String question;
    private final String[] propositions; // la 1ère est toujours la bonne réponse
    private final String difficulty;

    public Question(int id, String question, String[] propositions, String difficulty) {
        this.id = id;
        this.question = question;
        this.propositions = propositions;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getPropositions() {
        return propositions;
    }
}

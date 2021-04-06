package com.example.schooltips;

import java.util.ArrayList;

public class Quizz {
    private ThemeQuizz theme;
    private int nbQuestions;
    private int[] listeQuestions;
    private ArrayList<Integer> listeErreurs;
    private String solution;
    private int questionNb = 0;
    private int questionID;

    public Quizz(ThemeQuizz theme, int nbQuestions) {
        this.theme = theme;
        this.nbQuestions = nbQuestions;
        listeErreurs = new ArrayList<>();
    }

    public Quizz(ThemeQuizz theme, int[] listeQuestions) {
        this.theme = theme;
        this.nbQuestions = listeQuestions.length;
        this.listeQuestions = listeQuestions;
        listeErreurs = new ArrayList<>();
    }

    public boolean nextQuestion(String previousAnswer) {
        if (previousAnswer != null) {
            questionNb++;
            if (!previousAnswer.equals(solution)) {
                listeErreurs.add(questionID);
            }
        }

        if (questionNb < nbQuestions) {
            if (listeQuestions != null) {
                questionID = listeQuestions[questionNb];
            } else {
                //TODO : aller chercher dans la DB la question
            }

            //solution = DB[questionID].solution

            return true;
        } else {
            return false;
        }
    }
}

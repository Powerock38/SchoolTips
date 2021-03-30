package com.example.schooltips;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Calculs {
    private static final Random random = new Random();
    private final int op1Format;
    private final int op2Format;
    private final Operation operation;
    private final int nbQuestions;
    private final ArrayList<CoupleOperandes> listeErreurs;
    private int op1;
    private int op2;
    private int solution;
    private int questionNb = 0;
    private ArrayList<CoupleOperandes> listeQuestions;

    public Calculs(Operation operation, ArrayList<CoupleOperandes> listeQuestions) {
        this.operation = operation;
        this.listeQuestions = listeQuestions;
        this.nbQuestions = listeQuestions.size();
        this.op1Format = 1;
        this.op2Format = 1;
        listeErreurs = new ArrayList<>();
    }

    public Calculs(int op1Format, int op2Format, Operation operation, int nbQuestions) {
        this.op1Format = op1Format;
        this.op2Format = op2Format;
        this.operation = operation;
        this.nbQuestions = nbQuestions;
        listeErreurs = new ArrayList<>();
    }

    public boolean nextCalcul(Integer previousAnswer) {
        if (previousAnswer != null) {
            questionNb++;
            if (previousAnswer != solution) {
                listeErreurs.add(new CoupleOperandes(op1, op2));
            }
        }

        if(questionNb < nbQuestions) {
            if (listeQuestions != null) {
                op1 = listeQuestions.get(questionNb).getA();
                op2 = listeQuestions.get(questionNb).getB();
            } else {
                op1 = random.nextInt(op1Format * 10 - 1) + 1;
                op2 = random.nextInt(op2Format * 10 - 1) + 1;
            }

            switch (operation) {
                case add:
                    solution = op1 + op2;
                    break;

                case subtract:
                    solution = op1 - op2;
                    break;

                case multiply:
                    solution = op1 * op2;
                    break;

                case divide:
                    solution = op1 / op2;
                    break;
            }

            return true;
        } else {
            return false;
        }
    }

    public int getOp1() {
        return op1;
    }

    public int getOp2() {
        return op2;
    }

    public int getNbQuestions() {
        return nbQuestions;
    }

    public ArrayList<CoupleOperandes> getListeErreurs() {
        return listeErreurs;
    }
}

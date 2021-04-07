package com.example.schooltips;

import android.util.Log;

import java.util.Random;

public class Calculs {
    private static final Random random = new Random();
    private final int op1Format;
    private final int op2Format;
    private int op1;
    private int op2;
    private final Operation operation;
    private int solution;
    private int bonnesRep = 0;
    private final int nbQuestions;
    private int questionNb = 0;


    public Calculs(int op1Format, int op2Format, Operation operation, int nbQuestions) {
        this.op1Format = op1Format;
        this.op2Format = op2Format;
        this.operation = operation;
        this.nbQuestions = nbQuestions + 1;
    }

    public boolean nextCalcul(Integer previousAnswer) {
        if (previousAnswer != null) {
            if (previousAnswer == solution) {
                bonnesRep++;
            }
        }

        op1 = random.nextInt(op1Format * 10 - 1) + 1;
        op2 = random.nextInt(op2Format * 10 - 1) + 1;

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

        questionNb++;

        // true tant qu'il reste des questions
        return questionNb < nbQuestions;
    }

    public int getOp1() {
        return op1;
    }

    public int getOp2() {
        return op2;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getBonnesRep() {
        return bonnesRep;
    }

    public boolean toutCorrect() {return  (bonnesRep == nbQuestions-1);}

}

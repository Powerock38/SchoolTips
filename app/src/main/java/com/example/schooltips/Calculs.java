package com.example.schooltips;

import java.util.Locale;
import java.util.Random;

public class Calculs {
    private static final Random random = new Random();
    private int op1Format;
    private int op2Format;
    private int op1;
    private int op2;
    private Operation operation;
    private int solution;


    public Calculs(int op1Format, int op2Format, Operation operation) {
        this.op1Format = op1Format;
        this.op2Format = op2Format;
        this.operation = operation;

        nextCalcul();
    }

    public void nextCalcul() {
        op1 = random.nextInt(op1Format * 10 - 1) + 1;
        op2 = random.nextInt(op2Format * 10 - 1) + 1;

        switch(operation) {
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

    public boolean checkSolution(int answer) {
        return answer == solution;
    }
}

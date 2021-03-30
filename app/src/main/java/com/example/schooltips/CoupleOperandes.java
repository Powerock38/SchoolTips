package com.example.schooltips;

import java.io.Serializable;

public class CoupleOperandes implements Serializable {
    private final int a;
    private final int b;

    CoupleOperandes(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}


package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculsActivity extends AppCompatActivity {
    static public final String OPERATION_KEY = "OPERATION_KEY";
    static public final String FORMAT_A_KEY = "FORMAT_A_KEY";
    static public final String FORMAT_B_KEY = "FORMAT_B_KEY";

    private int formatA;
    private int formatB;
    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculs);

        Intent intent = getIntent();
        formatA = intent.getIntExtra(FORMAT_A_KEY, 1);
        formatB = intent.getIntExtra(FORMAT_B_KEY, 1);
        operation = (Operation) intent.getSerializableExtra(OPERATION_KEY);

        TextView question = findViewById(R.id.question);
        EditText answer = findViewById(R.id.answer);
        Button next = findViewById(R.id.next);

        Calculs calculs = new Calculs(formatA, formatB, operation);
    }
}
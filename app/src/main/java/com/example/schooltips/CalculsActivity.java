package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculs);

        TextView question = findViewById(R.id.question);
        EditText answer = findViewById(R.id.answer);
        Button next = findViewById(R.id.next);

        int op1Format = 1;
        int op2Format = 10;
        Operation operation = Operation.add;

        Calculs calculs = new Calculs(op1Format, op2Format, operation);

    }
}
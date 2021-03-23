package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class CalculsParamActivity extends AppCompatActivity {
    private int formatA;
    private int formatB;
    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculs_param);

        NumberPicker nbPickerNbOp = findViewById(R.id.nb_op);
        String[] possibleSeries = {"5", "10", "15", "20"};
        nbPickerNbOp.setDisplayedValues(possibleSeries);

        RadioGroup formatAGroup = findViewById(R.id.format_a);
        formatAGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (formatAGroup.getCheckedRadioButtonId()) {
                    case R.id.a_1:
                        formatA = 1;
                        break;
                    case R.id.a_10:
                        formatA = 10;
                        break;
                    case R.id.a_100:
                        formatA = 100;
                        break;
                }
            }
        });

        RadioGroup formatBGroup = findViewById(R.id.format_b);
        formatAGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (formatBGroup.getCheckedRadioButtonId()) {
                    case R.id.b_1:
                        formatB = 1;
                        break;
                    case R.id.b_10:
                        formatB = 10;
                        break;
                    case R.id.b_100:
                        formatB = 100;
                        break;
                }
            }
        });

        RadioGroup operationGroup = findViewById(R.id.operation);
        formatAGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (operationGroup.getCheckedRadioButtonId()) {
                    case R.id.plus:
                        operation = Operation.add;
                        break;
                    case R.id.moins:
                        operation = Operation.subtract;
                        break;
                    case R.id.mul:
                        operation = Operation.multiply;
                        break;
                    case R.id.div:
                        operation = Operation.divide;
                        break;
                }
            }
        });

        Button go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculsParamActivity.this, CalculsActivity.class);
                intent.putExtra(CalculsActivity.FORMAT_A_KEY, formatA);
                intent.putExtra(CalculsActivity.FORMAT_B_KEY, formatB);
                intent.putExtra(CalculsActivity.OPERATION_KEY, operation);
                startActivity(intent);
            }
        });
    }
}
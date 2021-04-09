package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class CalculsParamActivity extends AppCompatActivity {
    private int formatA = 1;
    private int formatB = 1;
    private Operation operation = Operation.add;
    private NumberPicker timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculs_param);

        timer = findViewById(R.id.timer);
        timer.setMinValue(0); // disabled, 30s, 1min, 1min30, 2min, 2min30
        timer.setMaxValue(5);

        int steps = timer.getMaxValue() - timer.getMinValue() + 1;
        String[] displayedValues = new String[steps];

        for (int i = 0; i < steps; i++) {
            if (i == 0) {
                displayedValues[i] = getString(R.string.notime);
            } else if (i == 1) {
                displayedValues[i] = getString(R.string.seconde, 30);
            } else if (i % 2 > 0) { // 2, 4
                displayedValues[i] = getString(R.string.minute_30secondes, i / 2);
            } else { // 3, 5
                displayedValues[i] = getString(R.string.minute, (i + 1) / 2);
            }
        }
        timer.setDisplayedValues(displayedValues);

        NumberPicker nbPickerNbOp = findViewById(R.id.nb_op);
        nbPickerNbOp.setMinValue(5);
        nbPickerNbOp.setMaxValue(20);

        RadioGroup formatAGroup = findViewById(R.id.format_a);
        formatAGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
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
        });

        RadioGroup formatBGroup = findViewById(R.id.format_b);
        formatBGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
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
        });

        RadioGroup operationGroup = findViewById(R.id.operation);
        operationGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
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
        });

        Button go = findViewById(R.id.go);
        go.setOnClickListener(v -> {
            Intent intent = new Intent(CalculsParamActivity.this, CalculsActivity.class);
            intent.putExtra(CalculsActivity.FORMAT_A_KEY, formatA);
            intent.putExtra(CalculsActivity.FORMAT_B_KEY, formatB);
            intent.putExtra(CalculsActivity.OPERATION_KEY, operation);
            intent.putExtra(CalculsActivity.NB_QUESTION_KEY, nbPickerNbOp.getValue());
            intent.putExtra(CalculsActivity.TIMER_KEY, timer.getValue() * 30);
            startActivity(intent);
        });

        Button retour = findViewById(R.id.retour);
        retour.setOnClickListener(v -> {
            Intent intent = new Intent(CalculsParamActivity.this, ChoixActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
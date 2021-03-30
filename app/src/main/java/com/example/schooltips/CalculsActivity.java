package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.parseInt;

public class CalculsActivity extends AppCompatActivity {
    static public final String OPERATION_KEY = "OPERATION_KEY";
    static public final String FORMAT_A_KEY = "FORMAT_A_KEY";
    static public final String FORMAT_B_KEY = "FORMAT_B_KEY";
    static public final String NB_QUESTION_KEY = "NB_QUESTION_KEY";

    private String op;
    private Calculs calculs;
    private TextView question;
    private EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculs);

        Intent intent = getIntent();
        int formatA = intent.getIntExtra(FORMAT_A_KEY, 1);
        int formatB = intent.getIntExtra(FORMAT_B_KEY, 1);
        Operation operation = (Operation) intent.getSerializableExtra(OPERATION_KEY);
        int nbQuestions = intent.getIntExtra(NB_QUESTION_KEY, 1);

        Toast.makeText(this, "operation : " + operation.name(), Toast.LENGTH_SHORT).show();

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);

        calculs = new Calculs(formatA, formatB, operation, nbQuestions);

        switch (operation) {
            case subtract:
                op = getString(R.string.calculs_op_moins);
                break;
            case multiply:
                op = getString(R.string.calculs_op_mul);
                break;
            case divide:
                op = getString(R.string.calculs_op_div);
                break;
            default: // add
                op = getString(R.string.calculs_op_plus);
        }

        nextCalculView(null);
    }

    public void nextCalculView(View v) {
        boolean shouldAskMore;
        if (v == null) {
            shouldAskMore = calculs.nextCalcul(null);
        } else {
            int rep;
            try {
                rep = parseInt(answer.getText().toString());
            } catch (NumberFormatException e) {
                rep = 0;
            }
            shouldAskMore = calculs.nextCalcul(rep);
        }

        if (shouldAskMore) {
            answer.getText().clear();
            question.setText(getString(R.string.calculs, calculs.getOp1(), op, calculs.getOp2()));
        } else {
            Toast.makeText(this, "vous avez " + calculs.getBonnesRep() + " bonnes réponses", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
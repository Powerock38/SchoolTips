package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CalculsActivity extends AppCompatActivity {
    static public final String OPERATION_KEY = "OPERATION_KEY";
    static public final String FORMAT_A_KEY = "FORMAT_A_KEY";
    static public final String FORMAT_B_KEY = "FORMAT_B_KEY";
    static public final String NB_QUESTION_KEY = "NB_QUESTION_KEY";
    static public final String LISTE_QUESTION_KEY = "LISTE_QUESTION_KEY";

    private String op;
    private Calculs calculs;
    private TextView question;
    private EditText answer;
    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculs);

        Intent intent = getIntent();
        int formatA = intent.getIntExtra(FORMAT_A_KEY, 1);
        int formatB = intent.getIntExtra(FORMAT_B_KEY, 1);
        operation = (Operation) intent.getSerializableExtra(OPERATION_KEY);
        int nbQuestions = intent.getIntExtra(NB_QUESTION_KEY, 1);

        if (intent.getExtras().containsKey(LISTE_QUESTION_KEY)) {
            ArrayList<CoupleOperandes> listeQuestions = (ArrayList<CoupleOperandes>) intent.getSerializableExtra(LISTE_QUESTION_KEY);
            calculs = new Calculs(operation, listeQuestions);
        } else {
            calculs = new Calculs(formatA, formatB, operation, nbQuestions);
        }

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);

        switch (operation) {
            case add:
                op = getString(R.string.calculs_op_plus);
                break;
            case subtract:
                op = getString(R.string.calculs_op_moins);
                break;
            case multiply:
                op = getString(R.string.calculs_op_mul);
                break;
            case divide:
                op = getString(R.string.calculs_op_div);
                break;
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
            Intent intent = new Intent(this, ResultatsActivity.class);
            intent.putExtra(ResultatsActivity.NB_QUESTIONS_KEY, calculs.getNbQuestions());
            ArrayList<CoupleOperandes> erreurs = calculs.getListeErreurs();
            intent.putExtra(ResultatsActivity.NB_ERREURS_KEY, erreurs.size());
            if (!erreurs.isEmpty()) {
                intent.putExtra(ResultatsActivity.LISTE_ERREURS_KEY, erreurs);
                intent.putExtra(ResultatsActivity.OPERATION_KEY, operation);
            }
            startActivity(intent);
            finish();
        }
    }
}
package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CalculsActivity extends AppCompatActivity {
    static public final String OPERATION_KEY = "OPERATION_KEY";
    static public final String FORMAT_A_KEY = "FORMAT_A_KEY";
    static public final String FORMAT_B_KEY = "FORMAT_B_KEY";
    static public final String NB_QUESTION_KEY = "NB_QUESTION_KEY";
    static public final String LISTE_QUESTION_KEY = "LISTE_QUESTION_KEY";
    static public final String TIMER_KEY = "TIMER_KEY";

    private String op;
    private Calculs calculs;
    private TextView question;
    private EditText answer;
    private Operation operation;
    private TextView questionCounter;
    private boolean isCorrection;
    private CountDownTimer timerObject;
    private boolean shouldAskMore;

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
            isCorrection = true;
        } else {
            calculs = new Calculs(formatA, formatB, operation, nbQuestions);
            isCorrection = false;
        }

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        questionCounter = findViewById(R.id.questionNb);

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

        TextView timer = findViewById(R.id.timer);
        int time = intent.getIntExtra(TIMER_KEY, 0);

        if (time > 0) {
            timerObject = new CountDownTimer(time * 1000, 1000) {
                public void onTick(long t) {
                    timer.setText(getString(R.string.seconde, 1 + (t / 1000)));
                }

                public void onFinish() {
                    while (shouldAskMore) {
                        shouldAskMore = calculs.nextCalcul(Integer.MAX_VALUE);
                    }
                    byebye();
                }
            };
            timerObject.start();
        }

        Button abandon = findViewById(R.id.abandon);
        abandon.setOnClickListener(v -> {
            Intent intent_abandon = new Intent(CalculsActivity.this, CalculsParamActivity.class);
            startActivity(intent_abandon);
            finish();
        });

        nextCalculView(null);
    }

    public void nextCalculView(View v) {
        if (v == null) {
            shouldAskMore = calculs.nextCalcul(null);
        } else {
            int rep;
            try {
                rep = parseInt(answer.getText().toString());
            } catch (NumberFormatException e) {
                rep = Integer.MAX_VALUE;
            }
            shouldAskMore = calculs.nextCalcul(rep);
        }

        if (shouldAskMore) {
            answer.getText().clear();
            question.setText(getString(R.string.calculs, calculs.getOp1(), op, calculs.getOp2()));
            questionCounter.setText(getString(R.string.xsurx, calculs.getQuestionNb() + 1, calculs.getNbQuestions()));
        } else {
            byebye();
        }
    }

    private void byebye() {
        Intent intent = new Intent(this, ResultatsActivity.class);
        intent.putExtra(ResultatsActivity.NB_QUESTIONS_KEY, calculs.getNbQuestions());
        ArrayList<CoupleOperandes> erreurs = calculs.getListeErreurs();
        intent.putExtra(ResultatsActivity.NB_ERREURS_KEY, erreurs.size());
        if (!erreurs.isEmpty()) {
            intent.putExtra(ResultatsActivity.LISTE_ERREURS_KEY, erreurs);
            intent.putExtra(ResultatsActivity.OPERATION_KEY, operation);
        }
        intent.putExtra(ResultatsActivity.ISCORRECTION_KEY, isCorrection);
        intent.putExtra(ResultatsActivity.EXERCICE_KEY, 0);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (timerObject != null) timerObject.cancel();
    }
}
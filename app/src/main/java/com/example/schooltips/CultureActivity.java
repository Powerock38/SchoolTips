package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CultureActivity extends AppCompatActivity {
    static public final String LISTE_QUESTION_KEY = "LISTE_QUESTION_KEY";
    static public final String TIMER_KEY = "TIMER_KEY";
    static public String NB_QUESTIONS_KEY = "NB_QUESTIONS_KEY";
    static public String THEME_KEY = "THEME_KEY";
    private Button[] answers;
    private TextView questionTextView;
    private TextView questionCounter;
    private Quizz quizz;
    private boolean isCorrection;
    private CountDownTimer timerObject;
    private boolean shouldAskMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);

        Intent intent = getIntent();
        ThemeQuizz theme = (ThemeQuizz) intent.getSerializableExtra(THEME_KEY);
        int nbQuestions = intent.getIntExtra(NB_QUESTIONS_KEY, 1);

        if (intent.getExtras().containsKey(LISTE_QUESTION_KEY)) {
            int[] listeQuestions = intent.getIntArrayExtra(LISTE_QUESTION_KEY);
            quizz = new Quizz(theme, listeQuestions);
            isCorrection = true;
        } else {
            quizz = new Quizz(theme, nbQuestions);
            isCorrection = false;
        }

        questionTextView = findViewById(R.id.question);
        questionCounter = findViewById(R.id.questionNb);

        answers = new Button[4];
        answers[0] = findViewById(R.id.answer1);
        answers[1] = findViewById(R.id.answer2);
        answers[2] = findViewById(R.id.answer3);
        answers[3] = findViewById(R.id.answer4);

        TextView timer = findViewById(R.id.timer);
        int time = intent.getIntExtra(TIMER_KEY, 0);

        if (time > 0) {
            timerObject = new CountDownTimer(time * 1000, 1000) {
                public void onTick(long t) {
                    timer.setText(getString(R.string.seconde, 1 + (t / 1000)));
                }

                public void onFinish() {
                    while (shouldAskMore) {
                        shouldAskMore = quizz.nextQuestion("");
                    }
                    byebye();
                }
            };
            timerObject.start();
        }


        Button abandon = findViewById(R.id.abandon);
        abandon.setOnClickListener(v -> {
            Intent intent_abandon = new Intent(CultureActivity.this, CultureParamActivity.class);
            startActivity(intent_abandon);
            finish();
        });

        nextQuestionView(null);
    }

    public void nextQuestionView(View v) {
        Button answer = (Button) v;

        if (v == null) {
            shouldAskMore = quizz.nextQuestion(null);
        } else {
            shouldAskMore = quizz.nextQuestion(answer.getText().toString());
        }

        if (shouldAskMore) {
            Question question = quizz.getQuestion();
            questionTextView.setText(question.getQuestion());

            ArrayList<String> propositions = new ArrayList<>(Arrays.asList(question.getPropositions()));
            Collections.shuffle(propositions);

            for (int i = 0; i < answers.length; i++) {
                answers[i].setText(propositions.get(i));
            }

            questionCounter.setText(getString(R.string.xsurx, quizz.getQuestionNb() + 1, quizz.getNbQuestions()));
        } else {
            byebye();
        }
    }

    private void byebye() {
        Intent intent = new Intent(this, ResultatsActivity.class);
        intent.putExtra(ResultatsActivity.NB_QUESTIONS_KEY, quizz.getNbQuestions());
        int[] erreurs = quizz.getListeErreurs();
        intent.putExtra(ResultatsActivity.NB_ERREURS_KEY, erreurs.length);
        if (erreurs.length > 0) {
            intent.putExtra(ResultatsActivity.LISTE_ERREURS_KEY, erreurs);
            intent.putExtra(ResultatsActivity.THEME_KEY, quizz.getTheme());
        }
        intent.putExtra(ResultatsActivity.ISCORRECTION_KEY, isCorrection);
        intent.putExtra(ResultatsActivity.EXERCICE_KEY, 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (timerObject != null) timerObject.cancel();
    }
}
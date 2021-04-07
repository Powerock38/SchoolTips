package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CultureActivity extends AppCompatActivity {
    static public String NB_QUESTIONS_KEY = "NB_QUESTIONS_KEY";
    static public String THEME_KEY = "THEME_KEY";
    static public final String LISTE_QUESTION_KEY = "LISTE_QUESTION_KEY";

    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Quizz quizz;

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
        } else {
            quizz = new Quizz(theme, nbQuestions);
        }

        Button abandon = findViewById(R.id.abandon);
        abandon.setOnClickListener(v -> {
            Intent intent_abandon = new Intent(CultureActivity.this, CultureParamActivity.class);
            startActivity(intent_abandon);
            finish();
        });
    }

    public void nextQuestionView(View v) {

    }
}
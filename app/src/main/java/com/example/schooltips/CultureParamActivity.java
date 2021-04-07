package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class CultureParamActivity extends AppCompatActivity {

    private ThemeQuizz theme = ThemeQuizz.francais;
    private NumberPicker nbQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_param);

        nbQuestions = findViewById(R.id.nb_questions);
        nbQuestions.setMinValue(5);
        nbQuestions.setMaxValue(20);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(CultureParamActivity.this, ChoixActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void startQuizz(View v) {
        switch (v.getId()) {
            case R.id.theme1:
                theme = ThemeQuizz.francais;
                break;
            case R.id.theme2:
                theme = ThemeQuizz.histoire;
                break;
            case R.id.theme3:
                theme = ThemeQuizz.geographie;
                break;
        }

        Intent intent = new Intent(this, CultureActivity.class);
        intent.putExtra(CultureActivity.NB_QUESTIONS_KEY, nbQuestions.getValue());
        intent.putExtra(CultureActivity.THEME_KEY, theme);
        startActivity(intent);
        finish();
    }
}
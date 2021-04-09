package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class CultureParamActivity extends AppCompatActivity {

    private ThemeQuizz theme = ThemeQuizz.francais;
    private NumberPicker nbQuestions;
    private NumberPicker timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_param);

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

        nbQuestions = findViewById(R.id.nb_questions);
        nbQuestions.setMinValue(5);
        nbQuestions.setMaxValue(20);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(CultureParamActivity.this, ChoixActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        intent.putExtra(CultureActivity.TIMER_KEY, timer.getValue() * 30);
        startActivity(intent);
        finish();
    }
}
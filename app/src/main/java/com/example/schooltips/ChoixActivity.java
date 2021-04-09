package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChoixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        Button maths = findViewById(R.id.maths);
        Button culture = findViewById(R.id.culture);
        Button changeUser = findViewById(R.id.change_user);

        maths.setOnClickListener(v -> {
            Intent intent = new Intent(ChoixActivity.this, CalculsParamActivity.class);
            startActivity(intent);
        });

        culture.setOnClickListener(v -> {
            Intent intent = new Intent(ChoixActivity.this, CultureParamActivity.class);
            startActivity(intent);
        });

        changeUser.setOnClickListener(v -> {
            Intent intent = new Intent(ChoixActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
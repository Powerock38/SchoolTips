package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
            //TODO : disconnect user ?
            Intent intent = new Intent(ChoixActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
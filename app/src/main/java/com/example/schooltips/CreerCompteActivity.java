package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreerCompteActivity extends AppCompatActivity {
    //Views
    private Button valider, retour;
    private EditText name, firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        name = findViewById(R.id.Compte_name);
        firstname = findViewById(R.id.Compte_firstname);

        retour = findViewById(R.id.back_inscription);
        valider = findViewById(R.id.valider_inscription);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreerCompteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instanciation de la BDD
                DatabaseUser userDataBase = DatabaseUser.getInstance(getApplicationContext());

                User newUser = new User();
                newUser.setNom(name.getText().toString());
                newUser.setPrenom(firstname.getText().toString());
                Log.d("Before put user ", "true");

                userDataBase.getAppDatabase().userDao().insert(newUser);

                Log.d("put user ok ? ", newUser.toString());

                Intent intent = new Intent(CreerCompteActivity.this, CalculsParamActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
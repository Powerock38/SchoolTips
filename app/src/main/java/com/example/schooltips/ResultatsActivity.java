package com.example.schooltips;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultatsActivity extends AppCompatActivity {
    static public final String NB_QUESTIONS_KEY = "NB_QUESTIONS_KEY";
    static public final String NB_ERREURS_KEY = "NB_ERREURS_KEY";
    static public final String LISTE_ERREURS_KEY = "LISTE_ERREURS_KEY";
    static public final String OPERATION_KEY = "OPERATION_KEY";

    private int nbQuestions;
    private User user;

    // Database
    private DatabaseUser userDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        // Instanciation de la BDD
        userDataBase = DatabaseUser.getInstance(getApplicationContext());
        user =  ((MyApplication) getApplication()).getUser();

        Intent intent = getIntent();

        nbQuestions = intent.getIntExtra(NB_QUESTIONS_KEY, 0);
        int nbErreurs = intent.getIntExtra(NB_ERREURS_KEY, 0);

        Button retourExos = findViewById(R.id.retour_exos);
        Button corriger = findViewById(R.id.corriger);
        TextView res = findViewById(R.id.resultat);
        TextView res_xsurx = findViewById(R.id.resultat_xsurx);

        res_xsurx.setText(getString(R.string.xsurx, nbQuestions - nbErreurs, nbQuestions));

        if (nbErreurs > 0) {
            res.setText(getResources().getQuantityString(R.plurals.erreurs, nbErreurs));
            corriger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(ResultatsActivity.this, CalculsActivity.class);
                    newIntent.putExtra(CalculsActivity.LISTE_QUESTION_KEY, intent.getSerializableExtra(LISTE_ERREURS_KEY));
                    newIntent.putExtra(CalculsActivity.OPERATION_KEY, intent.getSerializableExtra(OPERATION_KEY));
                    startActivity(newIntent);
                    finish();
                }
            });
        } else {
            majUser();

            res.setText(getString(R.string.all_good));
            corriger.setVisibility(View.GONE);
        }

        retourExos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultatsActivity.this, ChoixActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void majUser() {
        class MajUser extends  AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                userDataBase.getAppDatabase()
                        .userDao()
                        .majHighScore(user.getId(), user.getHighScore());
                return null;
            }
        }

        //////////////////////////
        // MAJ du user dans MyApplication
        ((MyApplication) getApplication()).majUser(nbQuestions, getApplicationContext());

        // Maj du USer dans la BDD
        MajUser maj = new MajUser();
        maj.execute();
    }
}
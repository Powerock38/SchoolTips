package com.example.schooltips;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultatsActivity extends AppCompatActivity {
    static public final String NB_QUESTIONS_KEY = "NB_QUESTIONS_KEY";
    static public final String NB_ERREURS_KEY = "NB_ERREURS_KEY";
    static public final String LISTE_ERREURS_KEY = "LISTE_ERREURS_KEY";
    static public final String ISCORRECTION_KEY = "ISCORRECTION_KEY";

    static public final String EXERCICE_KEY = "EXERCICE_KEY";

    static public final String OPERATION_KEY = "OPERATION_KEY";
    static public final String THEME_KEY = "THEME_KEY";

    private int nbQuestions;
    private int nbErreurs;
    private int score;
    private User user;

    // Database
    private DatabaseUser userDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        // Instanciation de la BDD
        userDataBase = DatabaseUser.getInstance(getApplicationContext());
        user = ((MyApplication) getApplication()).getUser();

        Intent intent = getIntent();

        nbQuestions = intent.getIntExtra(NB_QUESTIONS_KEY, 0);
        nbErreurs = intent.getIntExtra(NB_ERREURS_KEY, 0);
        score = nbQuestions - nbErreurs;

        Button retourExos = findViewById(R.id.retour_exos);
        Button corriger = findViewById(R.id.corriger);
        TextView res = findViewById(R.id.resultat);
        TextView res_xsurx = findViewById(R.id.resultat_xsurx);

        res_xsurx.setText(getString(R.string.xsurx, score, nbQuestions));

        if (nbErreurs > 0) {
            res.setText(getResources().getQuantityString(R.plurals.erreurs, nbErreurs));

            int exercice = intent.getIntExtra(EXERCICE_KEY, 0);
            corriger.setOnClickListener(v -> {
                Intent newIntent;

                if (exercice == 0) {
                    newIntent = new Intent(ResultatsActivity.this, CalculsActivity.class);
                    newIntent.putExtra(CalculsActivity.LISTE_QUESTION_KEY, intent.getSerializableExtra(LISTE_ERREURS_KEY));
                    newIntent.putExtra(CalculsActivity.OPERATION_KEY, intent.getSerializableExtra(OPERATION_KEY));
                } else if (exercice == 1) {
                    newIntent = new Intent(ResultatsActivity.this, CultureActivity.class);
                    newIntent.putExtra(CultureActivity.LISTE_QUESTION_KEY, intent.getIntArrayExtra(LISTE_ERREURS_KEY));
                    newIntent.putExtra(CultureActivity.THEME_KEY, intent.getSerializableExtra(THEME_KEY));
                } else { //should never trigger, but just in case
                    newIntent = new Intent(ResultatsActivity.this, ChoixActivity.class);
                }

                startActivity(newIntent);
                finish();
            });
        } else {
            corriger.setVisibility(View.GONE);
            if (nbQuestions == 0) {
                res_xsurx.setVisibility(View.GONE);
                res.setText(getString(R.string.no_question));
            } else res.setText(getString(R.string.all_good));
        }

        if (!intent.getBooleanExtra(ISCORRECTION_KEY, true)) majUser();

        retourExos.setOnClickListener(v -> {
            Intent intent1 = new Intent(ResultatsActivity.this, ChoixActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();
        });
    }

    private void majUser() {
        class MajUser extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                userDataBase.getAppDatabase()
                        .userDao()
                        .majHighScore(user.getId(), user.getHighScore());
                return null;
            }
        }

        // MAJ du user dans MyApplication
        ((MyApplication) getApplication()).majUser(score, getApplicationContext());

        // Maj du User dans la BDD
        MajUser maj = new MajUser();
        maj.execute();
    }
}
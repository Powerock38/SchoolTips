package com.example.schooltips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultatsActivity extends AppCompatActivity {
    static public final String NB_QUESTIONS_KEY = "NB_QUESTIONS_KEY";
    static public final String NB_ERREURS_KEY = "NB_ERREURS_KEY";
    static public final String LISTE_ERREURS_KEY = "LISTE_ERREURS_KEY";
    static public final String OPERATION_KEY = "OPERATION_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        Intent intent = getIntent();

        int nbQuestions = intent.getIntExtra(NB_QUESTIONS_KEY, 0);
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
            res.setText(getString(R.string.all_good));
            corriger.setVisibility(View.GONE);
        }

        retourExos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultatsActivity.this, ChoixActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
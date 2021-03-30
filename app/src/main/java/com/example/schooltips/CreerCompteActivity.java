package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreerCompteActivity extends AppCompatActivity {
    //BDD
    private DatabaseUser userDataBase ;
    //Views
    private Button valider, retour;
    private EditText name, firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        // Récupération du DatabaseClient
        userDataBase = DatabaseUser.getInstance(getApplicationContext());

        name = findViewById(R.id.Compte_name);
        firstname = findViewById(R.id.Compte_firstname);

        retour = findViewById(R.id.back_inscription);
        valider = findViewById(R.id.valider_inscription);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreerCompteActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instanciation de la BDD
                DatabaseUser userDataBase = DatabaseUser.getInstance(getApplicationContext());

                //Sauvegarde de l'utilisateur créé
                saveUser();

                Intent intent = new Intent(CreerCompteActivity.this, CalculsParamActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void saveUser() {

        // Récupérer les informations contenues dans les vues
        final String sName = name.getText().toString().trim();
        final String sFirstName = firstname.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (sName == " ? ") {
            name.setError("Task required");
            name.requestFocus();
            return;
        }

        if (sFirstName == " ? ") {
            firstname.setError("Desc required");
            firstname.requestFocus();
            return;
        }

        /**
         * Création d'une classe asynchrone pour sauvegarder l'utilisateur donnée par l'UI
         */
        class SaveUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a task
                User user = new User();
                user.setNom(sName);
                user.setPrenom(sFirstName);

                // adding to database
                userDataBase.getAppDatabase()
                        .userDao()
                        .insert(user);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveUser sU = new SaveUser();
        sU.execute();
    }
}
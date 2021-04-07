package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreerCompteActivity extends AppCompatActivity {
    //BDD
    private DatabaseUser userDataBase ;
    //Views
    private Button valider, retour;
    private EditText lastname, firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        // Récupération du DatabaseClient
        userDataBase = DatabaseUser.getInstance(getApplicationContext());

        lastname = findViewById(R.id.Compte_lastname);
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
                //Sauvegarde de l'utilisateur créé
                saveUser();
            }
        });
    }

    private void saveUser() {
        // Récupérer les informations contenues dans les vues
        final String sLastName = lastname.getText().toString().trim();
        final String sFirstName = firstname.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (sLastName.isEmpty()) {
            lastname.setError(getString(R.string.error_lastname));
            lastname.requestFocus();
            return;
        }

        if (sFirstName.isEmpty()) {
            firstname.setError(getString(R.string.error_firstname));
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
                user.setNom(sLastName);
                user.setPrenom(sFirstName);

                //Associtaion à l'activité gloable
                ((MyApplication) getApplication()).setUser(user);

                //Adding user to database
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

        Intent intent = new Intent(CreerCompteActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
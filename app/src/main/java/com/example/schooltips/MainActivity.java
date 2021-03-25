package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Database
    private DatabaseUser userDataBase;
    private UsersAdapter adapter;

    //Views
    private ListView userView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userView = findViewById(R.id.list_users);

        // Lier l'adapter au listView
        adapter = new UsersAdapter(this, new ArrayList<User>());
        userView.setAdapter(adapter);

        /*
         * la bdd bose problème ..
         * c'est en cours (25/03/21)
         */
        userDataBase = DatabaseUser.getInstance(getApplicationContext());

        /* ittérer sur les users de la database pour les afficher pour les users ou bien se creer un compte
        * le problème, c'est comment ?
        *//*
        for (User u : userList) {
            Button userButton = new Button(this);
            userButton.setText(u.getNom() + " " + u.getPrenom() + "\t" + "avatar");
            userView.addView(userButton);
        }*/

        Button test = findViewById(R.id.button_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalculsActivity.class);
                startActivity(intent);
            }
        });

        // Mise à jour des users
        getUsers();
    }

    private void getUsers() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = userDataBase.getAppDatabase()
                        .userDao()
                        .getAll();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                // Mettre à jour l'adapter avec la liste de users
                adapter.clear();
                adapter.addAll(users);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetUsers gu = new GetUsers();
        gu.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des taches
        getUsers();

    }
}
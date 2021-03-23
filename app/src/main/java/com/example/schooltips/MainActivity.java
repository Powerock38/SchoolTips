package com.example.schooltips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Database
    private DatabaseUser userDataBase;

    //Views
    private ListView userView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * appel à la bdd bose problème ..
         * c'est en cours (23/03/21)
         */
        userDataBase = DatabaseUser.getInstance(getApplicationContext());

        //List<User> userList = userDataBase.getAppDatabase().taskDao().getAll();

        userView = findViewById(R.id.list_users);

        /* itérer sur les users de la database pour les afficher pour les users ou bien se crer un compte
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
    }
}
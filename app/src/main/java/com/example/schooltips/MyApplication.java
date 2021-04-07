package com.example.schooltips;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private DatabaseUser userDataBase; //BDD
    private User user; //User courant

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        //l'insertion dans la bdd est déjà faite spar CrerCompteActivity
        //initialisation du user courant
        this.user = user;
    }

    public void majUser(int highScore, Context c) {
        // Instanciation de la BDD
        userDataBase = DatabaseUser.getInstance(c);
        user.setHighScore(user.getHighScore() + highScore);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Quizz.loadQuizz(getAssets());
    }
}


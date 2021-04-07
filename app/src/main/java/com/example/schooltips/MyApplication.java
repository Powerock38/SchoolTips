package com.example.schooltips;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    //BDD
    private DatabaseUser userDataBase;

    //User courant
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        //l'insertion dans la bdd est déjà faite spar CrerCompteActivity
        //initialisation du user courant
        this.user = user;
    }

    public void majUser(int highScore, Context c){
        // Instanciation de la BDD
        userDataBase = DatabaseUser.getInstance(c);
        user.setHighScore(user.getHighScore() + highScore);
        //Maj dans la BDD
        userDataBase.getAppDatabase().userDao().majHighScore(user.getId(), user.getHighScore());
    }
}

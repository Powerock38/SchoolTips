package com.example.schooltips;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE id = :idUser LIMIT 1")
    User getUser(int idUser);

    @Query("UPDATE user SET highScore = :highScore WHERE id = :idUser")
    void majHighScore(int idUser, int highScore);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Insert
    long[] insertAll(User... user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}

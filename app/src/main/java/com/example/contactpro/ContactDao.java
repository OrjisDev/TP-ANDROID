package com.example.contactpro;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    long insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE favori = 1")
    List<Contact> getFavoris();

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getById(long id);
}

package com.example.note.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note.entities.Note;

import java.util.List;
@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("UPDATE notes SET lock_mode = :lockMode WHERE id = :id")
    void lockNote(Boolean lockMode, int id);

    @Query("SELECT lock_mode FROM notes WHERE id = :id")
    int checkLockNote(int id);

}

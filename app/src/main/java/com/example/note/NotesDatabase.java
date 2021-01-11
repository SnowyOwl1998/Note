package com.example.note;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.note.activities.MainActivity;
import com.example.note.dao.NoteDao;
import com.example.note.entities.Note;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import java.io.File;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    public static final char[] PASS_PHARSE = {'!','@','#','$','%','^'};
    private static NotesDatabase notesDatabase;
    public static synchronized NotesDatabase getDatabase(Context context){
        final byte[] passphare = SQLiteDatabase.getBytes(PASS_PHARSE);
        final SupportFactory factory = new SupportFactory(passphare);
        if(notesDatabase == null){
            notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase.class,
                    "notes_db"
            ).openHelperFactory(factory)
                    .build();
        }
//        SupportSQLiteOpenHelper.Configuration.builder().name().callback(.build())
        return notesDatabase;
    }

    public abstract NoteDao noteDao();

}

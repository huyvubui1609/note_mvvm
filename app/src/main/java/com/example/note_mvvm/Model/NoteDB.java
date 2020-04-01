package com.example.note_mvvm.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDB extends RoomDatabase {
    private static NoteDB instance;
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    public static synchronized NoteDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDB.class, "note_database")
                    .addCallback(roomCallBack)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract NoteDAO noteDAO();

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;

        private PopulateDBAsyncTask(NoteDB db) {
            noteDAO = db.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Title 1", "Description 1", 1));
            noteDAO.insert(new Note("Title 2", "Description 2", 2));
            noteDAO.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}

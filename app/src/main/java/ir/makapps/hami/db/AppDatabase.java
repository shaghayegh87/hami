package ir.makapps.hami.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;

@Database(entities = {NoteDetailModel.class},version = 3,exportSchema = true)

public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context)
    {
        if (appDatabase == null)
        {
            appDatabase = Room.databaseBuilder(context,AppDatabase.class,"db_hami").allowMainThreadQueries().build();
        }
        return appDatabase;
    }

}


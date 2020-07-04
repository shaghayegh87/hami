package ir.makapps.hami.screens.main.fragments.note;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import ir.makapps.hami.model.NoteModel;


@Dao
public interface NoteDao {
    @Insert
    void save(NoteModel noteModel);

    @Update
    void update(NoteModel noteModel);

    @Query("SELECT * FROM TBL_NOTE WHERE idObject LIKE :id")
    NoteModel edit(int id);

    @Delete
    void delete(NoteModel noteModel);

    @Query("SELECT * FROM tbl_note")
    List<NoteModel> getAllNotes();

}

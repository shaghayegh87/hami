package ir.makapps.hami.screens.main.fragments.note;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.model.NoteModel;


@Dao
public interface NoteDao {
    @Insert
    void save(NoteDetailModel noteDetailModel);

    @Update
    void update(NoteDetailModel noteDetailModel);

    @Query("SELECT * FROM TBL_NOTE_DETAIL WHERE idObject LIKE :id")
    NoteDetailModel edit(int id);

    @Query("SELECT * FROM TBL_NOTE_DETAIL WHERE id LIKE :id")
    NoteDetailModel select(int id);

    @Delete
    void delete(NoteDetailModel noteDetailModel);

    @Query("SELECT * FROM tbl_note_detail")
    List<NoteDetailModel> getAllNotes();

}

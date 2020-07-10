package ir.makapps.hami.screens.createNote.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import javax.inject.Inject;
import ir.makapps.hami.R;
import ir.makapps.hami.db.AppDatabase;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.createNote.dagger.CreateNoteModule;
import ir.makapps.hami.screens.createNote.dagger.DaggerCreateNoteComponent;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.utils.Utils;

public class CreateNoteActivity extends BaseActivity implements CreateNoteContract.View, View.OnClickListener {
    @Inject
    CreateNoteContract.Presenter presenter;
    EditText edtCreateNote;
    Button btnCancel, btnInsertNote;
    ImageView imgCancel;
    AppDatabase appDatabase;
    NoteDao noteDao;
    ConstraintLayout constraintNote;
    int detailId;
    String title,description,address,image,date,city;
    MainModel mainModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getInstance(this);
        noteDao = appDatabase.noteDao();
        detailId = getIntent().getIntExtra("detail_id",0);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        address = getIntent().getStringExtra("address");
        image = getIntent().getStringExtra("image");
        date = getIntent().getStringExtra("date");
        city = getIntent().getStringExtra("city");





        presenter.attachView(this);
        presenter.updateNote(detailId,noteDao);
    }

    @Override
    protected void bindViews() {
        edtCreateNote = findViewById(R.id.edt_create_note);
        constraintNote = findViewById(R.id.constraint_create_note_main);
        btnCancel = findViewById(R.id.btn_cancel_note);
        btnInsertNote = findViewById(R.id.btn_insert_note);
        imgCancel = findViewById(R.id.img_note_close);
        btnInsertNote.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCancel.setOnClickListener(this);

    }

    @Override
    protected void injectDagger() {
        DaggerCreateNoteComponent.builder().
                appComponent(App.getAppComponent()).
                createNoteModule(new CreateNoteModule(this)).
                build().injectToCreateNoteActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_note:
                finish();
                break;

            case R.id.btn_insert_note:
                insertToLocalDb();
                break;

            case R.id.img_note_close:
                finish();
                break;
        }
    }

    @Override
    public void showNote(String note) {
        edtCreateNote.setText(note);
    }

    public void insertToLocalDb() {
        String noteText = edtCreateNote.getText().toString();
        NoteDetailModel model = noteDao.edit(detailId)!= null ? noteDao.edit(detailId) : new NoteDetailModel();
        if (noteText.length() > 0) {
            if (model.getIdObject() == 0) {
                model.setIdObject(detailId);
                model.setAddress(address);
                model.setDescription(description);
                model.setImagePath(image);
                model.setTitle(title);
                model.setDate(date);
                model.setCity(city);
                model.setText(noteText);
//                model.setDate();
                noteDao.save(model);
                Utils.showSnackbar(constraintNote,getApplicationContext().getResources().getString(R.string.insert_note_success),"green");
                edtCreateNote.setText("");
                finish();

            } else {
                model.setText(noteText);
                noteDao.update(model);
                Utils.showSnackbar(constraintNote,getApplicationContext().getResources().getString(R.string.insert_note_success),"green");
                edtCreateNote.setText("");
                finish();
            }
        }
        else {
            noteDao.delete(model);
            Utils.showSnackbar(constraintNote,getApplicationContext().getResources().getString(R.string.remove_note_success),"green");
            edtCreateNote.setText("");
            finish();
        }
//            NoteModel noteModel = new NoteModel();
//            noteModel.setText(noteText);
//            noteModel.setIdObject(detailId);
//            noteDao.save(noteModel);
//            edtCreateNote.setText("");
//            Utils.showSnackbar(constraintNote,"یادداشت شما ذخیره شد.","green");
//            Intent intent = new Intent(CreateNoteActivity.this, DetailActivity.class);
//            intent.putExtra("detail_id",detailId);
//            intent.putExtra("hasNote",true);
//            startActivity(intent);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_create_note;
    }

    @Override
    public void showDetail(MainModel model) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void ShowProgressbar() {

    }

    @Override
    public void HideProgressbar() {

    }

    @Override
    public void changeNoteInModel(Boolean bool) {

    }


}
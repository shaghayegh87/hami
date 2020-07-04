package ir.makapps.hami.screens.main.fragments.note.mvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.db.AppDatabase;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.screens.main.fragments.note.recycler.NoteRecyclerContract;
import ir.makapps.hami.utils.Utils;

public class NoteFragmentPresenter implements NoteFragmentContract.Presenter {

    List<NoteDetailModel> NoteList;
    NoteFragmentContract.View view;
    CompositeDisposable compositeDisposable;
    NoteFragmentContract.Model model;
    AppDatabase appDatabase;
    NoteDao noteDao;


    public NoteFragmentPresenter(NoteFragmentContract.View view, NoteFragmentContract.Model model,
                                 CompositeDisposable compositeDisposable) {
        this.model = model;
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        NoteList = new ArrayList<>();
        appDatabase = AppDatabase.getInstance(App.getContext());
        noteDao = appDatabase.noteDao();
    }

    @Override
    public void attachView(NoteFragmentContract.View view) {
        this.view = view;
        view.showProgressBar();
        if (getInternet()) {
            getNoteList();
        } else {
            view.showNoContent();
            view.showError(Utils.errorInternet);
            view.hideProgressBar();
            view.showNoContent();
        }
    }

    @Override
    public void refreshNoteList() {
        if (getInternet()) {
            getNoteList();
            view.swipeRefresh(false);
            view.hideProgressBar();
        } else {
            view.showError(Utils.errorInternet);
            view.swipeRefresh(false);
            view.hideProgressBar();
        }
    }


    @Override
    public int getHomeRowsCount() {
        return NoteList.size();
    }


    @Override
    public boolean getInternet() {
        return Utils.isConnected();
    }

    @Override
    public void bindHomeViewHolder(int position, NoteRecyclerContract.HomeViewHolder holder) {
        NoteDetailModel model = NoteList.get(position);
        holder.fillData(model);
    }

    @Override
    public void sendToDetailActivity(int id) {
        view.sendDataToDetail(id);
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getNoteList() {
        NoteList = noteDao.getAllNotes();
        if(NoteList.size() == 0)
        {
            view.showNoContent();
        }
        else
        view.refreshNoteList();
    }
}

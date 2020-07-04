package ir.makapps.hami.screens.main.fragments.note.mvp;


import java.util.List;

import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import ir.makapps.hami.screens.main.fragments.note.recycler.NoteRecyclerContract;

public interface NoteFragmentContract {

    interface View extends BaseView {
        void showProgressBar();

        void hideProgressBar();

        void swipeRefresh(boolean b);

        void showError(String error);

        void showNoContent();

        void sendDataToDetail(int id);

        void refreshNoteList();

//        List<NoteModel> sendNoteList(List<NoteModel> NoteList);
    }

    interface Presenter extends BasePresenter<NoteFragmentContract.View>, NoteRecyclerContract.HomeAdapter {

        void refreshNoteList();

        void getNoteList();
    }

    interface Model {
        List<NoteModel> getNoteList();
    }
}

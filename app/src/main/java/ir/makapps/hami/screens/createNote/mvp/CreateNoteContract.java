package ir.makapps.hami.screens.createNote.mvp;

import io.reactivex.Single;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;

public interface CreateNoteContract {

    interface View extends BaseView {
        void showDetail(MainModel model);

        void showError(String error);

        void ShowProgressbar();

        void HideProgressbar();

        void changeNoteInModel(Boolean bool);

        void showNote(String note);

    }

    interface Presenter extends BasePresenter<CreateNoteContract.View> {
        void updateDetail(int id);

        void saveBookmark(int id);

        void deleteBookmark(int id);

        void updateNote(int id, NoteDao noteDao);
    }

    interface Model {
        Single<HamiResponse<MainModel>> getDetail(String token, int id);

        Single<HamiResponse> saveBookmarkToDB(String token, int id);

        Single<HamiResponse> deleteBookmarkToDB(String token, int id);
    }
}

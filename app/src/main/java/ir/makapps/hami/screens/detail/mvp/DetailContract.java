package ir.makapps.hami.screens.detail.mvp;

import io.reactivex.Single;
import ir.makapps.hami.db.AppDatabase;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;

public interface DetailContract {

    interface View extends BaseView {
        void showDetail(MainModel model);

        void showError(String error);

        void ShowProgressbar();

        void HideProgressbar();

        void changeBookmarkInModel(Boolean bool);

        void fillNote();

        void firstNote();

    }

    interface Presenter extends BasePresenter<DetailContract.View> {
        void updateDetail(int id);

        void updateNote(int id, NoteDao noteDao);

        void saveBookmark(int id);

        void deleteBookmark(int id);
    }

    interface Model {
        Single<HamiResponse<MainModel>> getDetail(String token, int id);

        Single<HamiResponse> saveBookmarkToDB(String token, int id);

        Single<HamiResponse> deleteBookmarkToDB(String token, int id);
    }
}

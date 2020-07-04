package ir.makapps.hami.screens.main.fragments.bookmark.mvp;


import java.util.List;

import io.reactivex.Single;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import ir.makapps.hami.screens.main.fragments.bookmark.recycler.BookmarkRecyclerContract;

public interface BookmarkFragmentContract {

    interface View extends BaseView {
        void showProgressBar();
        void hideProgressBar();
        void swipeRefresh(boolean b);
        void showError(String error);
        void showNoContent();
        void sendDataToDetail(int id);
        void refreshMainList();
    }

    interface Presenter extends  BasePresenter<BookmarkFragmentContract.View> , BookmarkRecyclerContract.HomeAdapter{
        void getMainList(int stateId, String desc, int pageIndex);
        void refreshBookmarkList();
    }

    interface Model {
        Single<HamiResponse<List<MainBriefModel>>> getBookmarkList();
    }
}

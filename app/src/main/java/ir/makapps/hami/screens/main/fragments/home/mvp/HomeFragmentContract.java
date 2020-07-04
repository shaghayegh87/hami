package ir.makapps.hami.screens.main.fragments.home.mvp;



import java.util.List;

import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import io.reactivex.Single;
import ir.makapps.hami.screens.main.fragments.home.recycler.HomeRecyclerContract;

public interface HomeFragmentContract {

    interface View extends BaseView {
        void showProgressBar();
        void hideProgressBar();
        void swipeRefresh(boolean b);
        void showError(String error);
        void showNoContent();
        void sendDataToDetail(int id);
        void refreshMainList();

    }

    interface Presenter extends  BasePresenter<HomeFragmentContract.View> , HomeRecyclerContract.HomeAdapter{
        void getMainList(int stateId, String desc, int pageIndex);
        void refreshMainList();

    }

    interface Model {
        Single<HamiResponse<List<MainBriefModel>>> getMainList(int stateId , String desc, int pageIndex);
    }
}

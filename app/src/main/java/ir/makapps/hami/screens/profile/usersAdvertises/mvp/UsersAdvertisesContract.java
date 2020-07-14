package ir.makapps.hami.screens.profile.usersAdvertises.mvp;

import java.util.List;

import io.reactivex.Single;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.screens.profile.usersAdvertises.recycler.UsersAdvertiseRecyclerContract;

public interface UsersAdvertisesContract {

    interface View extends BaseView {
        void showListOfAdvertises();

        void showError(String error);

        void ShowProgressbar();

        void HideProgressbar();


    }

    interface Presenter extends BasePresenter<UsersAdvertisesContract.View>, UsersAdvertiseRecyclerContract.AdvertisesUserAdapter {

        void getUsersAdvertise(String token);
    }

    interface Model {
        Single<HamiResponse<List<MainBriefModel>>> GetUserMains(String token);

    }
}

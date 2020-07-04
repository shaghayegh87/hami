package ir.makapps.hami.screens.splash.mvp;

import io.reactivex.Single;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;

public interface SplashContract {

    interface View extends BaseView {

        void showError(String error);

        boolean hasToken();

        void setDelay(Boolean hasToken);


    }

    interface Presenter extends BasePresenter<SplashContract.View> {
        void switchByToken();
    }

    interface Model {
        Single<HamiResponse<MainModel>> getDetail(String token, int id);

    }
}

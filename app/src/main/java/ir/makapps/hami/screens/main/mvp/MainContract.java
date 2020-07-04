package ir.makapps.hami.screens.main.mvp;

import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;

public interface MainContract {
    interface View extends BaseView {
        void showError(String error);
        void setFragment(BaseFragment baseFragment);
    }

    interface Presenter extends BasePresenter<MainContract.View> {
        void updateFragment(BaseFragment baseFragment);
    }

    interface Model {

    }
}

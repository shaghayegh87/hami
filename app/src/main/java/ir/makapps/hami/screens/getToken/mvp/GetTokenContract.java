package ir.makapps.hami.screens.getToken.mvp;

import io.reactivex.Single;
import ir.makapps.hami.model.BodyModel;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;

public interface GetTokenContract {

    interface View extends BaseView {

        void showError(String error);

        void showMessage(String msg);

        void hideProgressBar();

        void showProgressBar();

        void changeColorOfViews();

        void changeColorToFirst();

        void goToNextActivity();

        void getValidationCodeAgain();


    }

    interface Presenter extends BasePresenter<View> {

        void getData(String mobile, int confirmCode);

        void getTokenFromModel(String mobile, int confirmCode);


    }

    interface Model {
        Single<HamiResponse<BodyModel>> getToken(String mobile, int validationCode);
    }
}

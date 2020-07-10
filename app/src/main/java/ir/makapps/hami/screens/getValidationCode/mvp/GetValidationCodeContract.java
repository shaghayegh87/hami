package ir.makapps.hami.screens.getValidationCode.mvp;

import io.reactivex.Single;
import ir.makapps.hami.model.ValidationModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;

public interface GetValidationCodeContract {

    interface View extends BaseView {
        void showConfirmCode(int code);

        void showError(String error);

        void showMessage(String msg);

        void hideProgressBar();

        void showProgressBar();

        void changeColorOfViews();

        void changeColorToFirst();

        void goToNextActivity();

        void getTime();


    }

    interface Presenter extends BasePresenter<View> {

        void getData(String mobile, int securityNumber);

        void validationCodeFromModel(String mobile, int securityNumber);

        int getSecurityNumber(String number);

        boolean getInternet();
    }

    interface Model {
        Single<ValidationModel> getValidation(String mobile, int securityNumber);
    }
}

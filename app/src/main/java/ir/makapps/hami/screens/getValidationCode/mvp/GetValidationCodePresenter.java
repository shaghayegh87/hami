package ir.makapps.hami.screens.getValidationCode.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.ValidationModel;
import ir.makapps.hami.utils.Utils;

public class GetValidationCodePresenter implements GetValidationCodeContract.Presenter {
    GetValidationCodeContract.View view;
    GetValidationCodeContract.Model model;
    CompositeDisposable compositeDisposable;
    public int confirmCode ;

    public GetValidationCodePresenter(GetValidationCodeContract.View view, GetValidationCodeContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void getData(String mobile, int securityNumber) {
        validationCodeFromModel(mobile, securityNumber);
    }

    @Override
    public void validationCodeFromModel(String mobile, int securityNumber) {
        view.changeColorOfViews();
        model.getValidation(mobile, securityNumber).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ValidationModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ValidationModel validationModel) {
                        view.successProgressBar();
                        confirmCode = validationModel.getBody();
                        view.showConfirmCode(confirmCode);
                        Log.d("get_number", confirmCode + "");
                        view.showMessage(App.getContext().getResources().getString(R.string.success_process));
                        view.goToNextActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failureProgressBar();
                        view.hideProgressBar();
                        view.changeColorToFirst();
                        view.showError(App.getContext().getString(R.string.failed_process));
                    }
                });

    }

    @Override
    public int getSecurityNumber(String number) {
        return Utils.produceSecurityNumber(number);
    }


    @Override
    public void attachView(GetValidationCodeContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        if (compositeDisposable.size() > 0 && compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public boolean getInternet() {
        return false;
    }


}


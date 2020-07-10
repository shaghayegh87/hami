package ir.makapps.hami.screens.getToken.mvp;

import android.view.View;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.BodyModel;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.utils.Utils;

public class GetTokenPresenter implements GetTokenContract.Presenter {
    GetTokenContract.View view;
    GetTokenContract.Model model;
    CompositeDisposable compositeDisposable;

    public GetTokenPresenter(GetTokenContract.View view, GetTokenContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void getData(String mobile, int confirmCode) {
        if(getInternet())
        {
            view.showProgressBar();
            getTokenFromModel(mobile, confirmCode);
        }
        else view.showError(Utils.errorInternet);

    }

    @Override
    public void getTokenFromModel(String mobile, int confirmCode) {
        view.changeColorOfViews();
        model.getToken(mobile, confirmCode).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse<BodyModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(HamiResponse<BodyModel> bodyModelHamiResponse) {
                      view.hideProgressBar();
                        if (bodyModelHamiResponse.getResultCode() == 1) {
                            String token = bodyModelHamiResponse.getResults().getToken();
                            Utils.insertToHawk(App.getContext().getResources().getString(R.string.token), token);
                            view.showMessage(App.getContext().getResources().getString(R.string.success_process));
                            view.goToNextActivity();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                       view.hideProgressBar();
                        view.hideProgressBar();
                        view.changeColorToFirst();
                        view.showError(App.getContext().getResources().getString(R.string.failed_process));
                        view.changeColorToFirst();
                        view.getValidationCodeAgain();
                    }
                });
    }


    @Override
    public void attachView(GetTokenContract.View view) {

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
        return Utils.networkInfo();
    }


}


package ir.makapps.hami.screens.splash.mvp;

import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.utils.Utils;

public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View view;
    SplashContract.Model model;
    CompositeDisposable compositeDisposable;
    private String token = "";

    public SplashPresenter(SplashContract.View view, SplashContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(SplashContract.View view) {

        this.view = view;
//        view.setDelay();
        switchByToken();
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
        return Utils.isConnected();
    }

    @Override
    public void switchByToken() {
        if (getInternet()) {


            if(Utils.hasToken())
            {
               view.setDelay(true);
            }

            else
            {
                view.setDelay(false);
            }

        }
    }
}

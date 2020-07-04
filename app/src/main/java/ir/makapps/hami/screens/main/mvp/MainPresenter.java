package ir.makapps.hami.screens.main.mvp;

import java.util.List;


import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.utils.Utils;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    MainContract.Model model;
    CompositeDisposable compositeDisposable;

    public MainPresenter(MainContract.View view, MainContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(MainContract.View view) {
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
        return Utils.isConnected();
    }

    @Override
    public void updateFragment(BaseFragment baseFragment) {
        view.setFragment(baseFragment);
    }


}

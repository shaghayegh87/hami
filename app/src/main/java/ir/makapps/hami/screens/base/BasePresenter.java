package ir.makapps.hami.screens.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();

    boolean getInternet();
}

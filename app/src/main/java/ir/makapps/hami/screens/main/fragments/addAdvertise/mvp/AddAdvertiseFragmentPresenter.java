package ir.makapps.hami.screens.main.fragments.addAdvertise.mvp;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.CityModel;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.utils.Utils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddAdvertiseFragmentPresenter implements AddAdvertiseFragmentContract.Presenter {
    AddAdvertiseFragmentContract.View view;
    AddAdvertiseFragmentContract.Model model;
    CompositeDisposable compositeDisposable;

    public AddAdvertiseFragmentPresenter(AddAdvertiseFragmentContract.View view, AddAdvertiseFragmentContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void attachView(AddAdvertiseFragmentContract.View view) {

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
    public void getStates(String token) {

        model.getStatesDB(token).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse<List<CityModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(HamiResponse<List<CityModel>> listHamiResponse) {
                        List<CityModel> cityList = listHamiResponse.getResults();
                        view.showCityList(cityList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.toString().contains("Authoriz")) {
                            view.showError(Utils.errorUnAuthorization);
                        }
                    }
                });
    }

    @Override
    public void insertAdvertise(List<MultipartBody.Part> files, String title, String address, String Description, int StatedId, String Latitude, String Longitude) {
        if (getInternet()) {
            view.showProgressbar();
            RequestBody titleBody = RequestBody.create(okhttp3.MultipartBody.FORM, title);
            RequestBody addressBody = RequestBody.create(okhttp3.MultipartBody.FORM, address);
            RequestBody DescriptionBody = RequestBody.create(okhttp3.MultipartBody.FORM, Description);
            RequestBody LatitudeBody = RequestBody.create(okhttp3.MultipartBody.FORM, Latitude);
            RequestBody LongitudeBody = RequestBody.create(okhttp3.MultipartBody.FORM, Longitude);
            model.insertAdvertiseToDB(files, titleBody, addressBody, DescriptionBody, StatedId, LatitudeBody, LongitudeBody).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<HamiResponse<MainModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(HamiResponse<MainModel> mainModelHamiResponse) {
                            MainModel model = mainModelHamiResponse.getResults();
                            view.clearInformationAdvertise();
                            view.hideProgressbar();
                            view.showMessage(App.getContext().getResources().getString(R.string.success_process), "green");

                        }

                        @Override
                        public void onError(Throwable e) {
                            String error = e.toString();
                            view.showError(Utils.errorInApp);
                            view.hideProgressbar();
                            view.clearInformationAdvertise();
                        }
                    });

        } else {
            view.showError(Utils.errorInternet);

        }
    }


}

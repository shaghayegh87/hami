package ir.makapps.hami.screens.main.fragments.addAdvertise.mvp;

import java.io.File;
import java.util.List;

import io.reactivex.Single;
import ir.makapps.hami.model.CityModel;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.screens.base.BasePresenter;
import ir.makapps.hami.screens.base.BaseView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface AddAdvertiseFragmentContract {
    interface View extends BaseView {
        void showCityList(List<CityModel> cityList);

        void showProgressbar();

        void hideProgressbar();

        void showError(String error);

        void clearInformationAdvertise();

        void showMessage(String msg,String color);


    }

    interface Presenter extends BasePresenter<View> {


        void getStates(String token);

        void insertAdvertise(List<MultipartBody.Part> Files, String title, String address, String Description, int StatedId, String Latitude, String Longitude);
    }

    interface Model {
        Single<HamiResponse<List<CityModel>>> getStatesDB(String token);

        Single<HamiResponse<MainModel>> insertAdvertiseToDB(List<MultipartBody.Part> Files, RequestBody title, RequestBody address, RequestBody Description, int StatedId, RequestBody Latitude, RequestBody Longitude);
    }
}

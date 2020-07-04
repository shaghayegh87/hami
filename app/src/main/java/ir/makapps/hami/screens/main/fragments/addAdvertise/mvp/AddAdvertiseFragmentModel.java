package ir.makapps.hami.screens.main.fragments.addAdvertise.mvp;

import java.util.List;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.CityModel;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.utils.Utils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddAdvertiseFragmentModel implements AddAdvertiseFragmentContract.Model {

    private HamipetApi hamipetApi;
    private String token = Utils.getToken();

    public AddAdvertiseFragmentModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }


    @Override
    public Single<HamiResponse<List<CityModel>>> getStatesDB(String token) {
        return hamipetApi.getStates(token);
    }

    @Override
    public Single<HamiResponse<MainModel>> insertAdvertiseToDB(List<MultipartBody.Part> files, RequestBody title, RequestBody address, RequestBody Description, int StatedId,RequestBody Latitude,RequestBody Longitude) {
        return hamipetApi.InsertNewSupport(token,files,title,Description,address,StatedId,Latitude,Longitude);
    }
}

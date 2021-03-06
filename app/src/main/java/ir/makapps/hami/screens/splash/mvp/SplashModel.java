package ir.makapps.hami.screens.splash.mvp;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.utils.Utils;

public class SplashModel implements SplashContract.Model {
   private HamipetApi hamipetApi;
   private String token = Utils.getToken();

    public SplashModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }


    @Override
    public Single<HamiResponse<MainModel>> getDetail(String token, int id) {
        return hamipetApi.getDetail(token,id);
    }


}


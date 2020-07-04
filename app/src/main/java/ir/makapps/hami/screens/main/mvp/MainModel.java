package ir.makapps.hami.screens.main.mvp;

import java.util.List;


import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainListModel;

public class MainModel implements MainContract.Model {
    HamipetApi hamipetApi;

    public MainModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }

    //@Override
//    public Single<HamiResponse<List<MainListModel>>> getMainList(String code, int stateId, String address, String desc, int pageIndex) {
//        return hamipetApi.getData(code, stateId, address, desc, pageIndex);
//    }}
}
package ir.makapps.hami.screens.main.fragments.home.mvp;

import java.util.List;


import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;

public class HomeFragmentModel implements HomeFragmentContract.Model {
    HamipetApi hamipetApi;
    private String token = ir.makapps.hami.utils.Utils.getToken();

    public HomeFragmentModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }

    @Override
    public Single<HamiResponse<List<MainBriefModel>>> getMainList(int stateId, String desc, int pageIndex) {
        return hamipetApi.GetMainBriefList(token,stateId,desc,pageIndex);
    }
}

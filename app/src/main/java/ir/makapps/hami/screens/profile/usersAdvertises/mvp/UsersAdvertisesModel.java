package ir.makapps.hami.screens.profile.usersAdvertises.mvp;

import java.util.List;
import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.utils.Utils;

public class UsersAdvertisesModel implements UsersAdvertisesContract.Model {
   private HamipetApi hamipetApi;
   private String token = Utils.getToken();

    public UsersAdvertisesModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }

    @Override
    public Single<HamiResponse<List<MainBriefModel>>> GetUserMains(String token) {
        return hamipetApi.GetUserMains(token);
    }
}


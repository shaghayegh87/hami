package ir.makapps.hami.screens.getToken.mvp;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.BodyModel;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.ValidationModel;

public class GetTokenModel implements GetTokenContract.Model {
    HamipetApi hamipetApi;

    public GetTokenModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }


    @Override
    public Single<HamiResponse<BodyModel>> getToken(String mobile, int validationCode) {
        return hamipetApi.getToken(mobile,validationCode);
    }

}

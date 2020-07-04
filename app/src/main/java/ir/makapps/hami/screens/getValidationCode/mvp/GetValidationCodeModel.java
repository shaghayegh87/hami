package ir.makapps.hami.screens.getValidationCode.mvp;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.ValidationModel;

public class GetValidationCodeModel implements GetValidationCodeContract.Model {
    HamipetApi hamipetApi;

    public GetValidationCodeModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }

    @Override
    public Single<ValidationModel> getValidation(String mobile, int securityNumber) {
        return hamipetApi.getValidationCode(mobile, securityNumber);

    }
}

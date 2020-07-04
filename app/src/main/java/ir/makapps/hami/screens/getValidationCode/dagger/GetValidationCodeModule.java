package ir.makapps.hami.screens.getValidationCode.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.getValidationCode.mvp.GetValidationCodeContract;
import ir.makapps.hami.screens.getValidationCode.mvp.GetValidationCodeModel;
import ir.makapps.hami.screens.getValidationCode.mvp.GetValidationCodePresenter;


@Module
public class GetValidationCodeModule {

    private GetValidationCodeContract.View view;

    public GetValidationCodeModule(GetValidationCodeContract.View view) {
        this.view = view;
    }

    @GetValidationCodeScope
    @Provides
    GetValidationCodeContract.View provideView() {
        return this.view;
    }



    @GetValidationCodeScope
    @Provides
    GetValidationCodeContract.Presenter providePresenter(GetValidationCodeContract.View view, GetValidationCodeContract.Model model, CompositeDisposable compositeDisposable) {
        return new GetValidationCodePresenter(view,model,compositeDisposable);
    }



    @GetValidationCodeScope
    @Provides
    GetValidationCodeContract.Model provideModel(HamipetApi hamipetApi) {
        return new GetValidationCodeModel(hamipetApi);
    }


    @GetValidationCodeScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

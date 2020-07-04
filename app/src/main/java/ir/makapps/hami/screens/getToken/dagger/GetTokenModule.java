package ir.makapps.hami.screens.getToken.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.getToken.mvp.GetTokenContract;
import ir.makapps.hami.screens.getToken.mvp.GetTokenModel;
import ir.makapps.hami.screens.getToken.mvp.GetTokenPresenter;


@Module
public class GetTokenModule {

    private GetTokenContract.View view;

    public GetTokenModule(GetTokenContract.View view) {
        this.view = view;
    }

    @GetTokenScope
    @Provides
    GetTokenContract.View provideView() {
        return this.view;
    }



    @GetTokenScope
    @Provides
    GetTokenContract.Presenter providePresenter(GetTokenContract.View view, GetTokenContract.Model model, CompositeDisposable compositeDisposable) {
        return new GetTokenPresenter(view,model,compositeDisposable);
    }



    @GetTokenScope
    @Provides
    GetTokenContract.Model provideModel(HamipetApi hamipetApi) {
        return new GetTokenModel(hamipetApi);
    }


    @GetTokenScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

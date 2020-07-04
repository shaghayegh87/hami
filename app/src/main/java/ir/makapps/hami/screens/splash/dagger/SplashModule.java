package ir.makapps.hami.screens.splash.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.splash.mvp.SplashContract;
import ir.makapps.hami.screens.splash.mvp.SplashModel;
import ir.makapps.hami.screens.splash.mvp.SplashPresenter;

@Module
public class SplashModule {

    private SplashContract.View view;

    public SplashModule(SplashContract.View view) {
        this.view = view;
    }

    @SplashScope
    @Provides
    SplashContract.View provideView() {
        return this.view;
    }



    @SplashScope
    @Provides
    SplashContract.Presenter providePresenter(SplashContract.View view, SplashContract.Model model, CompositeDisposable compositeDisposable) {
        return new SplashPresenter(view,model,compositeDisposable);
    }



    @SplashScope
    @Provides
    SplashContract.Model provideModel(HamipetApi hamipetApi) {
        return new SplashModel(hamipetApi);
    }


    @SplashScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

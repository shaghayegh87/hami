package ir.makapps.hami.screens.main.fragments.home.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.main.fragments.home.mvp.HomeFragmentContract;
import ir.makapps.hami.screens.main.fragments.home.mvp.HomeFragmentModel;
import ir.makapps.hami.screens.main.fragments.home.mvp.HomeFragmentPresenter;

@Module
public class HomeModule {

    private HomeFragmentContract.View view;

    public HomeModule(HomeFragmentContract.View view) {
        this.view = view;
    }

    @HomeScope
    @Provides
    HomeFragmentContract.View provideView() {
        return this.view;
    }


    @HomeScope
    @Provides
    HomeFragmentContract.Presenter providePresenter(HomeFragmentContract.View view, HomeFragmentContract.Model model,CompositeDisposable compositeDisposable) {
        return new HomeFragmentPresenter(view,model,compositeDisposable);
    }


    @HomeScope
    @Provides
    HomeFragmentContract.Model provideModel(HamipetApi hamipetApi) {
        return new HomeFragmentModel(hamipetApi);
    }

    @HomeScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }
}

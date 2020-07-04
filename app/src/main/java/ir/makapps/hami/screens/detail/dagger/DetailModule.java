package ir.makapps.hami.screens.detail.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.detail.mvp.DetailContract;
import ir.makapps.hami.screens.detail.mvp.DetailModel;
import ir.makapps.hami.screens.detail.mvp.DetailPresenter;

@Module
public class DetailModule {

    private DetailContract.View view;

    public DetailModule(DetailContract.View view) {
        this.view = view;
    }

    @DetailScope
    @Provides
    DetailContract.View provideView() {
        return this.view;
    }



    @DetailScope
    @Provides
    DetailContract.Presenter providePresenter(DetailContract.View view, DetailContract.Model model,CompositeDisposable compositeDisposable) {
        return new DetailPresenter(view,model,compositeDisposable);
    }



    @DetailScope
    @Provides
    DetailContract.Model provideModel(HamipetApi hamipetApi) {
        return new DetailModel(hamipetApi);
    }


    @DetailScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

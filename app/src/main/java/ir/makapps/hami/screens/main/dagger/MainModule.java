package ir.makapps.hami.screens.main.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.main.mvp.MainContract;
import ir.makapps.hami.screens.main.mvp.MainModel;
import ir.makapps.hami.screens.main.mvp.MainPresenter;

@Module
public class MainModule {

    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @MainScope
    @Provides
    MainContract.View provideView() {
        return this.view;
    }



    @MainScope
    @Provides
    MainContract.Presenter providePresenter(MainContract.View view, MainContract.Model model,CompositeDisposable compositeDisposable) {
        return new MainPresenter(view,model,compositeDisposable);
    }

//    @LoginScope
//    @Provides
//    BookmarkFragment provideHomeFragment()
//    {
//        return new BookmarkFragment();
//    }
//
//    @LoginScope
//    @Provides
//    BookmarkFragment provideBookmarkFragment()
//    {
//        return new BookmarkFragment();
//    }

    @MainScope
    @Provides
    MainContract.Model provideModel(HamipetApi hamipetApi) {
        return new MainModel(hamipetApi);
    }


    @MainScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

package ir.makapps.hami.screens.profile.usersAdvertises.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.profile.usersAdvertises.mvp.UsersAdvertisesContract;
import ir.makapps.hami.screens.profile.usersAdvertises.mvp.UsersAdvertisesModel;
import ir.makapps.hami.screens.profile.usersAdvertises.mvp.UsersAdvertisesPresenter;

@Module
public class UsersAdvertisesModule {

    private UsersAdvertisesContract.View view;

    public UsersAdvertisesModule(UsersAdvertisesContract.View view) {
        this.view = view;
    }

    @UsersAdvertisesScope
    @Provides
    UsersAdvertisesContract.View provideView() {
        return this.view;
    }



    @UsersAdvertisesScope
    @Provides
    UsersAdvertisesContract.Presenter providePresenter(UsersAdvertisesContract.View view, UsersAdvertisesContract.Model model, CompositeDisposable compositeDisposable) {
        return new UsersAdvertisesPresenter(view,model,compositeDisposable);
    }



    @UsersAdvertisesScope
    @Provides
    UsersAdvertisesContract.Model provideModel(HamipetApi hamipetApi) {
        return new UsersAdvertisesModel(hamipetApi);
    }


    @UsersAdvertisesScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

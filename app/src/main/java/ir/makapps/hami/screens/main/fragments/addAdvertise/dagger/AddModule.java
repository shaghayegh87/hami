package ir.makapps.hami.screens.main.fragments.addAdvertise.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.main.fragments.addAdvertise.mvp.AddAdvertiseFragmentContract;
import ir.makapps.hami.screens.main.fragments.addAdvertise.mvp.AddAdvertiseFragmentModel;
import ir.makapps.hami.screens.main.fragments.addAdvertise.mvp.AddAdvertiseFragmentPresenter;

@Module
public class AddModule {

    private AddAdvertiseFragmentContract.View view;

    public AddModule(AddAdvertiseFragmentContract.View view) {
        this.view = view;
    }

    @AddScope
    @Provides
    AddAdvertiseFragmentContract.View provideView() {
        return this.view;
    }

    @AddScope
    @Provides
    AddAdvertiseFragmentContract.Presenter providePresenter(AddAdvertiseFragmentContract.View view, AddAdvertiseFragmentContract.Model model, CompositeDisposable compositeDisposable) {
        return new AddAdvertiseFragmentPresenter(view, model, compositeDisposable);
    }


    @AddScope
    @Provides
    AddAdvertiseFragmentContract.Model provideModel(HamipetApi hamipetApi) {
        return new AddAdvertiseFragmentModel(hamipetApi);
    }

    @AddScope
    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }
}

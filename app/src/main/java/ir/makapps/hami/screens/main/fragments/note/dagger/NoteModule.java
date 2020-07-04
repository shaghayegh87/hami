package ir.makapps.hami.screens.main.fragments.note.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.main.fragments.note.mvp.NoteFragmentContract;
import ir.makapps.hami.screens.main.fragments.note.mvp.NoteFragmentModel;
import ir.makapps.hami.screens.main.fragments.note.mvp.NoteFragmentPresenter;

@Module
public class NoteModule {

    private NoteFragmentContract.View view;

    public NoteModule(NoteFragmentContract.View view) {
        this.view = view;
    }

    @NoteScope
    @Provides
    NoteFragmentContract.View provideView() {
        return this.view;
    }


    @NoteScope
    @Provides
    NoteFragmentContract.Presenter providePresenter(NoteFragmentContract.View view, NoteFragmentContract.Model model, CompositeDisposable compositeDisposable) {
        return new NoteFragmentPresenter(view,model,compositeDisposable);
    }


    @NoteScope
    @Provides
    NoteFragmentContract.Model provideModel(HamipetApi hamipetApi) {
        return new NoteFragmentModel(hamipetApi);
    }

    @NoteScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }
}

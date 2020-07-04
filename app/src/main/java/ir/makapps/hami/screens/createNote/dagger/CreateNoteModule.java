package ir.makapps.hami.screens.createNote.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.createNote.mvp.CreateNoteContract;
import ir.makapps.hami.screens.createNote.mvp.CreateNoteModel;
import ir.makapps.hami.screens.createNote.mvp.CreateNotePresenter;

@Module
public class CreateNoteModule {

    private CreateNoteContract.View view;

    public CreateNoteModule(CreateNoteContract.View view) {
        this.view = view;
    }

    @CreateNoteScope
    @Provides
    CreateNoteContract.View provideView() {
        return this.view;
    }



    @CreateNoteScope
    @Provides
    CreateNoteContract.Presenter providePresenter(CreateNoteContract.View view, CreateNoteContract.Model model, CompositeDisposable compositeDisposable) {
        return new CreateNotePresenter(view,model,compositeDisposable);
    }



    @CreateNoteScope
    @Provides
    CreateNoteContract.Model provideModel(HamipetApi hamipetApi) {
        return new CreateNoteModel(hamipetApi);
    }


    @CreateNoteScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }


}

package ir.makapps.hami.screens.main.fragments.bookmark.dagger;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.screens.main.fragments.bookmark.mvp.BookmarkFragmentContract;
import ir.makapps.hami.screens.main.fragments.bookmark.mvp.BookmarkFragmentModel;
import ir.makapps.hami.screens.main.fragments.bookmark.mvp.BookmarkFragmentPresenter;

@Module
public class BookmarkModule {

    private BookmarkFragmentContract.View view;

    public BookmarkModule(BookmarkFragmentContract.View view) {
        this.view = view;
    }

    @BookmarkScope
    @Provides
    BookmarkFragmentContract.View provideView() {
        return this.view;
    }


    @BookmarkScope
    @Provides
    BookmarkFragmentContract.Presenter providePresenter(BookmarkFragmentContract.View view, BookmarkFragmentContract.Model model, CompositeDisposable compositeDisposable) {
        return new BookmarkFragmentPresenter(view,model,compositeDisposable);
    }


    @BookmarkScope
    @Provides
    BookmarkFragmentContract.Model provideModel(HamipetApi hamipetApi) {
        return new BookmarkFragmentModel(hamipetApi);
    }

    @BookmarkScope
    @Provides
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }
}

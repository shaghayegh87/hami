package ir.makapps.hami.screens.main.fragments.bookmark.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.main.fragments.bookmark.mvp.BookmarkFragment;

@BookmarkScope
@Component (modules = {BookmarkModule.class}, dependencies = {AppComponent.class})
public interface BookmarkComponent {
    void injectToBookmarkFragment(BookmarkFragment fragment);
}

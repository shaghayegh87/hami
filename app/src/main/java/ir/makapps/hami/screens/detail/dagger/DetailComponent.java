package ir.makapps.hami.screens.detail.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.detail.mvp.DetailActivity;
import ir.makapps.hami.screens.main.mvp.MainActivity;

@ir.makapps.hami.screens.detail.dagger.DetailScope
@Component (modules = {DetailModule.class}, dependencies = {AppComponent.class})
public interface DetailComponent {
    void injectToDetailActivity(DetailActivity activity);
}

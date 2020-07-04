package ir.makapps.hami.screens.main.fragments.home.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.main.fragments.home.mvp.HomeFragment;

@HomeScope
@Component (modules = {HomeModule.class}, dependencies = {AppComponent.class})
public interface HomeComponent {
    void injectToHomeFragment(HomeFragment fragment);
}

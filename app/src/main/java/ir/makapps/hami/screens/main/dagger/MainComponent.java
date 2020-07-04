package ir.makapps.hami.screens.main.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.main.mvp.MainActivity;

@MainScope
@Component (modules = {MainModule.class}, dependencies = {AppComponent.class})
public interface MainComponent {
    void injectToMainActivity(MainActivity activity);
}

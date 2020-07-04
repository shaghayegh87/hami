package ir.makapps.hami.screens.splash.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.splash.mvp.SplashActivity;

@SplashScope
@Component (modules = {SplashModule.class}, dependencies = {AppComponent.class})
public interface SplashComponent {
    void injectToSplashActivity(SplashActivity activity);
}

package ir.makapps.hami.screens.getToken.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.getToken.mvp.GetTokenActivity;

@GetTokenScope
@Component (modules = {GetTokenModule.class}, dependencies = {AppComponent.class})
public interface GetTokenComponent {
    void injectToGetTokenActivity(GetTokenActivity activity);
}

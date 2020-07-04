package ir.makapps.hami.screens.getValidationCode.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.getValidationCode.mvp.GetValidationCodeActivity;

@GetValidationCodeScope
@Component (modules = {GetValidationCodeModule.class}, dependencies = {AppComponent.class})
public interface GetValidationCodeComponent {
    void injectToGetValidationCodeActivity(GetValidationCodeActivity activity);
}

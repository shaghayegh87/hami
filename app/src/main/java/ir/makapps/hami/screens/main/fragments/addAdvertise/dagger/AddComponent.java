package ir.makapps.hami.screens.main.fragments.addAdvertise.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.main.fragments.addAdvertise.mvp.AddAdvertiseFragment;

@AddScope
@Component (modules = {AddModule.class}, dependencies = {AppComponent.class})
public interface AddComponent {
    void injectToAddFragment(AddAdvertiseFragment fragment);
}

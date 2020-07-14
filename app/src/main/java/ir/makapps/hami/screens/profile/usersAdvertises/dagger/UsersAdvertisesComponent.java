package ir.makapps.hami.screens.profile.usersAdvertises.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.profile.usersAdvertises.mvp.UsersAdvertisesActivity;

@UsersAdvertisesScope
@Component (modules = {UsersAdvertisesModule.class}, dependencies = {AppComponent.class})
public interface UsersAdvertisesComponent {
    void injectToUsersAdvertisesActivity(UsersAdvertisesActivity activity);
}

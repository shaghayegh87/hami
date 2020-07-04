package ir.makapps.hami.screens.createNote.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.createNote.mvp.CreateNoteActivity;

@CreateNoteScope
@Component (modules = {CreateNoteModule.class}, dependencies = {AppComponent.class})
public interface CreateNoteComponent {
    void injectToCreateNoteActivity(CreateNoteActivity activity);
}

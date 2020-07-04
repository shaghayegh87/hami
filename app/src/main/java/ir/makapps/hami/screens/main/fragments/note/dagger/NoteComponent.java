package ir.makapps.hami.screens.main.fragments.note.dagger;

import dagger.Component;
import ir.makapps.hami.di.builder.AppComponent;
import ir.makapps.hami.screens.main.fragments.note.mvp.NoteFragment;

@NoteScope
@Component (modules = {NoteModule.class}, dependencies = {AppComponent.class})
public interface NoteComponent {
    void injectToNoteFragment(NoteFragment fragment);
}

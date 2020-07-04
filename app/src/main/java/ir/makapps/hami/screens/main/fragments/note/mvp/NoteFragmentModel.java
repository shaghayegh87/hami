package ir.makapps.hami.screens.main.fragments.note.mvp;

import java.util.List;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.NoteModel;

public class NoteFragmentModel implements NoteFragmentContract.Model {
    HamipetApi hamipetApi;
    private String token = ir.makapps.hami.utils.Utils.getToken();

    public NoteFragmentModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }



    @Override
    public List<NoteModel> getNoteList() {
        return null;

    }
}

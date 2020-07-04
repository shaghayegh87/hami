package ir.makapps.hami.screens.createNote.mvp;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.utils.Utils;

public class CreateNoteModel implements CreateNoteContract.Model {
   private HamipetApi hamipetApi;
   private String token = Utils.getToken();

    public CreateNoteModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }


    @Override
    public Single<HamiResponse<MainModel>> getDetail(String token, int id) {
        return hamipetApi.getDetail(token,id);
    }

    @Override
    public Single<HamiResponse> saveBookmarkToDB(String token, int id) {
        return hamipetApi.saveBookmark(token,id);
    }

    @Override
    public Single<HamiResponse> deleteBookmarkToDB(String token, int id) {
        return hamipetApi.deleteBookmark(token,id);
    }
}


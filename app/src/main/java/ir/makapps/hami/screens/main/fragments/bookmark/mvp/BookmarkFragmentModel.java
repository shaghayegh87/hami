package ir.makapps.hami.screens.main.fragments.bookmark.mvp;

import java.util.List;

import io.reactivex.Single;
import ir.makapps.hami.api.HamipetApi;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;

public class BookmarkFragmentModel implements BookmarkFragmentContract.Model {
    HamipetApi hamipetApi;
    private String token = ir.makapps.hami.utils.Utils.getToken();

    public BookmarkFragmentModel(HamipetApi hamipetApi) {
        this.hamipetApi = hamipetApi;
    }

    @Override
    public Single<HamiResponse<List<MainBriefModel>>> getBookmarkList() {
        return hamipetApi.GetUserBookmarks(token);
    }
}

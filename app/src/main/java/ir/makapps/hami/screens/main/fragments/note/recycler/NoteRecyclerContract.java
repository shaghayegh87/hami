package ir.makapps.hami.screens.main.fragments.note.recycler;


import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.base.BaseRecycler;

public interface NoteRecyclerContract {
    interface HomeAdapter {
        int getHomeRowsCount();

        void bindHomeViewHolder(int position, HomeViewHolder holder);

        void sendToDetailActivity(int id);
    }

    interface HomeViewHolder extends BaseRecycler.BaseHolder<NoteDetailModel> {
    }
}

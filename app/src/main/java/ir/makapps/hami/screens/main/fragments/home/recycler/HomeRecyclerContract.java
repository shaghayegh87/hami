package ir.makapps.hami.screens.main.fragments.home.recycler;

import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.base.BaseRecycler;

public interface HomeRecyclerContract {
    interface HomeAdapter {
        int getHomeRowsCount();

        void bindHomeViewHolder(int position, HomeViewHolder holder);

        void sendToDetailActivity(int id);
    }

    interface HomeViewHolder extends BaseRecycler.BaseHolder<MainBriefModel> {
    }
}

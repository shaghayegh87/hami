package ir.makapps.hami.screens.profile.usersAdvertises.recycler;


import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.screens.base.BaseRecycler;

public interface UsersAdvertiseRecyclerContract {
    interface AdvertisesUserAdapter {
        int getAdvertisesUserRowsCount();

        void bindAdvertisesUserViewHolder(int position, UsersAdvertiseViewHolder holder);

        void sendToDetailActivity(int id);
    }

    interface AdvertisesUserViewHolder extends BaseRecycler.BaseHolder<MainBriefModel> {
    }
}

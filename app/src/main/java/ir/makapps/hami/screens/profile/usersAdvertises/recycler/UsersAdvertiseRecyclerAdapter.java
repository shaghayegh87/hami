package ir.makapps.hami.screens.profile.usersAdvertises.recycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdvertiseRecyclerAdapter extends RecyclerView.Adapter<UsersAdvertiseViewHolder> {

    UsersAdvertiseRecyclerContract.AdvertisesUserAdapter presenter;

    public UsersAdvertiseRecyclerAdapter(UsersAdvertiseRecyclerContract.AdvertisesUserAdapter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public UsersAdvertiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersAdvertiseViewHolder(parent, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdvertiseViewHolder holder, int position) {
        presenter.bindAdvertisesUserViewHolder(position,holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getAdvertisesUserRowsCount();
    }
}

package ir.makapps.hami.screens.main.fragments.home.recycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.makapps.hami.model.MainListModel;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    HomeRecyclerContract.HomeAdapter presenter;

    public HomeRecyclerAdapter(HomeRecyclerContract.HomeAdapter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(parent, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        presenter.bindHomeViewHolder(position,holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getHomeRowsCount();
    }
}

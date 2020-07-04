package ir.makapps.hami.screens.main.fragments.bookmark.recycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkRecyclerAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {

    BookmarkRecyclerContract.HomeAdapter presenter;

    public BookmarkRecyclerAdapter(BookmarkRecyclerContract.HomeAdapter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkViewHolder(parent, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        presenter.bindHomeViewHolder(position,holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getHomeRowsCount();
    }
}

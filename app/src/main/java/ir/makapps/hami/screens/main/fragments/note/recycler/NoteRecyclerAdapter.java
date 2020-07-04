package ir.makapps.hami.screens.main.fragments.note.recycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    NoteRecyclerContract.HomeAdapter presenter;

    public NoteRecyclerAdapter(NoteRecyclerContract.HomeAdapter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        presenter.bindHomeViewHolder(position,holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getHomeRowsCount();
    }
}

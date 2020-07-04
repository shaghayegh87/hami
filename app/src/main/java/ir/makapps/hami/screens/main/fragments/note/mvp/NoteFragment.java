package ir.makapps.hami.screens.main.fragments.note.mvp;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.screens.detail.mvp.DetailActivity;
import ir.makapps.hami.screens.main.fragments.bookmark.dagger.DaggerBookmarkComponent;
import ir.makapps.hami.screens.main.fragments.note.dagger.DaggerNoteComponent;
import ir.makapps.hami.screens.main.fragments.note.dagger.NoteModule;
import ir.makapps.hami.screens.main.fragments.note.recycler.NoteRecyclerAdapter;
import ir.makapps.hami.utils.Utils;


public class NoteFragment extends BaseFragment implements NoteFragmentContract.View {

    @Inject
    NoteFragmentContract.Presenter presenter;

    private RecyclerView recyclerView;
    private NoteRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noContent;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_note;
    }

    @Override
    public void injectDagger() {
        DaggerNoteComponent.builder().
                appComponent(App.getAppComponent()).
                noteModule(new NoteModule(this)).
                build().injectToNoteFragment(this);
    }

    @Override
    public void bindViews() {
        noContent = rootView.findViewById(R.id.noContent);
        noContent.setVisibility(View.GONE);
        progressBar = rootView.findViewById(R.id.progress);
        swipeRefreshLayout = rootView.findViewById(R.id.pullToRefresh);
        recyclerView = rootView.findViewById(R.id.recycler_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressBar();
                presenter.refreshNoteList();
            }
        });
        adapter = new NoteRecyclerAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getData() {
        presenter.getNoteList();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void swipeRefresh(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void refreshNoteList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoContent() {
        noContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendDataToDetail(int id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("detail_id", id);
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        View v = getActivity().findViewById(android.R.id.content);
        Utils.showSnackbar(v, error, "");
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }
}

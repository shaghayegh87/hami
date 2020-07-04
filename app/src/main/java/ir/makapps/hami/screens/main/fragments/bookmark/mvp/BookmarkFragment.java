package ir.makapps.hami.screens.main.fragments.bookmark.mvp;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import javax.inject.Inject;

import de.mrapp.android.dialog.adapter.ViewPagerAdapter;
import dmax.dialog.SpotsDialog;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.screens.detail.mvp.DetailActivity;
import ir.makapps.hami.screens.main.fragments.bookmark.dagger.BookmarkModule;
import ir.makapps.hami.screens.main.fragments.bookmark.dagger.DaggerBookmarkComponent;
import ir.makapps.hami.screens.main.fragments.bookmark.recycler.BookmarkRecyclerAdapter;
import ir.makapps.hami.screens.main.fragments.home.dagger.HomeComponent;
import ir.makapps.hami.utils.Utils;


public class BookmarkFragment extends BaseFragment implements BookmarkFragmentContract.View {

    @Inject
    BookmarkFragmentContract.Presenter presenter;

    private RecyclerView recyclerView;
    private BookmarkRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noContent;
    private AlertDialog alertDialogBookmark;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_bookmark;
    }

    @Override
    public void injectDagger() {
        DaggerBookmarkComponent.builder().
                appComponent(App.getAppComponent()).
                bookmarkModule(new BookmarkModule(this)).
                build().injectToBookmarkFragment(this);
    }

    @Override
    public void bindViews(){
        noContent = rootView.findViewById(R.id.noContent);
        noContent.setVisibility(View.GONE);
        progressBar = rootView.findViewById(R.id.progress);
        swipeRefreshLayout = rootView.findViewById(R.id.pullToRefresh);
        recyclerView = rootView.findViewById(R.id.rv_banners);




        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressBar();
                presenter.refreshBookmarkList();
            }
        });
        adapter = new BookmarkRecyclerAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void getData() {
        presenter.getMainList(0,"",1);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void swipeRefresh(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void refreshMainList() {
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
    public void showProgressBar()
    {
//        alertDialogBookmark = new SpotsDialog.Builder()
//                .setContext(getContext())
//                .setTheme(R.style.Custom)
//                .setMessage(R.string.loading)
//                .setCancelable(false)
//                .build();
//        alertDialogBookmark.show();
    }

    @Override
    public void hideProgressBar() {
//        alertDialogBookmark.dismiss();
    }

    @Override
    public void showError(String error) {
        View v = getActivity().findViewById(android.R.id.content);
        Utils.showSnackbar(v, error, "red");
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }
}

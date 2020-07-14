package ir.makapps.hami.screens.main.fragments.home.mvp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.screens.detail.mvp.DetailActivity;
import ir.makapps.hami.screens.main.fragments.home.dagger.DaggerHomeComponent;
import ir.makapps.hami.screens.main.fragments.home.dagger.HomeModule;
import ir.makapps.hami.screens.main.fragments.home.recycler.HomeRecyclerAdapter;
import ir.makapps.hami.utils.Utils;


public class HomeFragment extends BaseFragment implements HomeFragmentContract.View {

    @Inject
    HomeFragmentContract.Presenter presenter;

    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noContent;
    private AlertDialog alertDialog;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void injectDagger() {
        DaggerHomeComponent.builder().
                appComponent(App.getAppComponent()).
                homeModule(new HomeModule(this)).
                build().injectToHomeFragment(this);
    }


    @Override
    public void bindViews() {
        noContent = rootView.findViewById(R.id.noContent);
        noContent.setVisibility(View.GONE);
        progressBar = rootView.findViewById(R.id.progress);
        swipeRefreshLayout = rootView.findViewById(R.id.pullToRefresh);
        recyclerView = rootView.findViewById(R.id.rv_banners);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshMainList();
            }
        });
        adapter = new HomeRecyclerAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getData() {

        if (presenter.getInternet()) {
            showProgressBar();
            presenter.getMainList(0, "", 1);
        } else {
            noContent.setText(Utils.errorInternet);
            showNoContent();
        }

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
    public void showProgressBar() {
        createDialogProgress();
    }

    public void createDialogProgress() {
        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .setMessage(R.string.loading)
                .setCancelable(false)
                .build();
        alertDialog.show();
    }

    @Override
    public void hideProgressBar() {
        alertDialog.dismiss();
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

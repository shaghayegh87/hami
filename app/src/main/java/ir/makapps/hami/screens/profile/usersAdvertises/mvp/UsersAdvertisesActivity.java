package ir.makapps.hami.screens.profile.usersAdvertises.mvp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;
import ir.makapps.hami.R;
import ir.makapps.hami.db.AppDatabase;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.ImageModel;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.createNote.mvp.CreateNoteActivity;
import ir.makapps.hami.screens.detail.dagger.DaggerDetailComponent;
import ir.makapps.hami.screens.detail.dagger.DetailModule;
import ir.makapps.hami.screens.detail.imageSlider.DetailImageAdapter;
import ir.makapps.hami.screens.detail.imageSlider.ViewAdapter;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.screens.profile.usersAdvertises.dagger.DaggerUsersAdvertisesComponent;
import ir.makapps.hami.screens.profile.usersAdvertises.dagger.UsersAdvertisesModule;
import ir.makapps.hami.screens.profile.usersAdvertises.recycler.UsersAdvertiseRecyclerAdapter;
import ir.makapps.hami.utils.Utils;
import ir.map.sdk_map.MapirStyle;
import ir.map.sdk_map.maps.MapView;
import okhttp3.internal.Util;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;
import static ir.makapps.hami.di.App.getContext;

public class UsersAdvertisesActivity extends BaseActivity implements UsersAdvertisesContract.View, View.OnClickListener {
    @Inject
    UsersAdvertisesContract.Presenter presenter;
    private RecyclerView recycler;
    private ConstraintLayout view;
    private AlertDialog alertDialog;
    private UsersAdvertiseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
//        presenter.getUsersAdvertise(Utils.getToken());
//        adapter = new UsersAdvertiseRecyclerAdapter(presenter);
        getData();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getData() {
        if(Utils.networkInfo())
        {
presenter.getUsersAdvertise(Utils.getToken());
        }
        else Utils.showSnackbar(view,Utils.errorInternet,"red");
    }


    @Override
    public void bindViews() {
        recycler = findViewById(R.id.recycler_list_advertises_user);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        view = findViewById(R.id.constraint_advertises_user);
        adapter = new UsersAdvertiseRecyclerAdapter(presenter);
        recycler.setAdapter(adapter);

    }

    @Override
    protected void injectDagger() {
        DaggerUsersAdvertisesComponent.builder().
                appComponent(App.getAppComponent()).
                usersAdvertisesModule(new UsersAdvertisesModule(this)).
                build().injectToUsersAdvertisesActivity(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activiy_advertises;
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public void showError(String error) {
        Utils.showSnackbar(view, error, "");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }

    }

    @Override
    public void showListOfAdvertises() {
      adapter.notifyDataSetChanged();
    }

    public void createDialogProgress() {
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .setMessage(R.string.loading)
                .setCancelable(false)
                .build();
        alertDialog.show();
    }

    @Override
    public void ShowProgressbar() {
        createDialogProgress();
    }

    @Override
    public void HideProgressbar() {

        alertDialog.dismiss();
    }
}
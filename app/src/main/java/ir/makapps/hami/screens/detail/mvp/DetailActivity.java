package ir.makapps.hami.screens.detail.mvp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ir.makapps.hami.R;
import ir.makapps.hami.db.AppDatabase;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.ImageModel;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.createNote.mvp.CreateNoteActivity;
import ir.makapps.hami.screens.detail.dagger.DaggerDetailComponent;
import ir.makapps.hami.screens.detail.dagger.DetailModule;
import ir.makapps.hami.screens.detail.imageSlider.DetailImageAdapter;
import ir.makapps.hami.screens.detail.imageSlider.ViewAdapter;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.screens.main.mvp.MainActivity;
import ir.makapps.hami.utils.Utils;
import ir.map.sdk_map.MapirStyle;
import ir.map.sdk_map.maps.MapView;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;
import static ir.makapps.hami.di.App.getContext;

public class DetailActivity extends BaseActivity implements DetailContract.View, View.OnClickListener {
    @Inject
    DetailContract.Presenter presenter;
    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private DetailImageAdapter adapter;
    private ViewAdapter myAdapter;
    private List<ImageModel> sliderImg;
    private ImageModel model;
    private MainModel mainModel;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout sliderDotsPanel;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View view;
    private int detailId, noteId;
    private LatLng comeFromEditingAddressPosition;
    private ProgressBar progress;
    private List<String> imagePathList;
    private MapView mapView;
    private MapboxMap map;
    private Style mapStyle;
    private int sampleZoom = 17;
    private LatLng samplePoint;
    private ConstraintLayout constraintMap;
    private Button btnMap;
    private Bundle bundle;
    private double latitude;
    private double longitude;
    private TextView title, txtBookmark, txtNote, txtAddress, txtDescription, txtDate;
    private ImageView iconBookmark, iconNote;
    private LinearLayout linearBookmark, linearNote;
    private Boolean hasNote = false;
    private AppDatabase appDatabase;
    private NoteDao noteDao;
    private NoteDetailModel noteDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        detailId = getIntent().getIntExtra("detail_id", 0);
        noteId = getIntent().getIntExtra("note_id", 0);
        presenter.attachView(this);
        presenter.updateDetail(detailId);
        appDatabase = AppDatabase.getInstance(DetailActivity.this);
        noteDao = appDatabase.noteDao();
        if (detailId > 0) {
            presenter.updateNote(detailId, noteDao);
            presenter.hasNote(detailId,noteDao);
        }

        if (noteId > 0) {
            presenter.getNote(noteId, noteDao);
        }
    }
//        if (checkInternet()) {
//            presenter.updateDetail(home_id);
//        }
//        else {
//            Utils.showSnackbar(view, Utils.errorInternet, "");
//        }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.updateNote(detailId, noteDao);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLatLng(String lat, String lan) {
        if (!(lat.equals("") && !lan.equals(""))) {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lan);
            samplePoint = new LatLng(latitude, longitude);
            constraintMap.setVisibility(View.VISIBLE);
        } else {
            constraintMap.setVisibility(View.GONE);
        }

        showLocation();

    }

    private void addSymbolToMap() {
        mapStyle.addImage("sample_image_id", getResources().getDrawable(R.drawable.ic_pin));
        SymbolManager sampleSymbolManager = new SymbolManager(mapView, map, mapStyle);
        sampleSymbolManager.addClickListener(new OnSymbolClickListener() {
            @Override
            public void onAnnotationClick(Symbol symbol) {
                mapView = view.findViewById(R.id.map_view_detail);
            }
        });
        sampleSymbolManager.addLongClickListener(new OnSymbolLongClickListener() {
            @Override
            public void onAnnotationLongClick(Symbol symbol) {

//                Toast.makeText(DetailActivity.this, "This is LONG_CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
// set non-data-driven properties, such as:
        sampleSymbolManager.setIconAllowOverlap(true);
        sampleSymbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);
// Add symbol at specified lat/lon
        SymbolOptions sampleSymbolOptions = new SymbolOptions();
        sampleSymbolOptions.withLatLng(samplePoint);
        sampleSymbolOptions.withIconImage("sample_image_id");
        sampleSymbolOptions.withIconSize(1.0f);
// save created Symbol Object for later access
        Symbol sampleSymbol = sampleSymbolManager.create(sampleSymbolOptions);
    }

    private void manageCameraPosition() {

        if (comeFromEditingAddressPosition != null) {

            setCameraInMap(map, comeFromEditingAddressPosition);

        } else {
            setCameraInMap(map, samplePoint);
        }
    }

    private void setCameraInMap(MapboxMap map, LatLng latLng) {

        map.setCameraPosition(
                new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(15)
                        .build());

    }

    private void reverseGeocode(CameraPosition cameraPosition) {
        samplePoint = cameraPosition.target;
    }


    @Override
    public void bindViews() {
        view = findViewById(android.R.id.content);
        progress = view.findViewById(R.id.progress_detail);
        linearBookmark = view.findViewById(R.id.linear_bookmark);
        linearNote = view.findViewById(R.id.linear_note);
        iconBookmark = view.findViewById(R.id.icon_bookmark);
        iconNote = view.findViewById(R.id.icon_note);
        linearBookmark = view.findViewById(R.id.linear_bookmark);
        linearBookmark = view.findViewById(R.id.linear_bookmark);
        mapView = view.findViewById(R.id.map_view_detail);
        constraintMap = view.findViewById(R.id.constraint_map);
        btnMap = view.findViewById(R.id.btn_show_google_map);
        sliderDotsPanel = findViewById(R.id.sliderDots);
        txtBookmark = view.findViewById(R.id.txt_bookmark);
        txtNote = view.findViewById(R.id.txt_note);
        txtAddress = view.findViewById(R.id.txt_address);
        txtDate = view.findViewById(R.id.date_detail);
        txtDescription = view.findViewById(R.id.txt_description);
        title = view.findViewById(R.id.title_detail);
        viewPager = findViewById(R.id.viewPager2);
        toolbar = findViewById(R.id.toolbar_detail);
//        if(hasNote)
//        {
//            changeNote(hasNote);
//        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        linearBookmark.setOnClickListener(this);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }
        });

        linearNote.setOnClickListener(this);
    }


    @Override
    public void fillNote() {
        Drawable img = getContext().getResources().getDrawable(R.drawable.ic_add_note_fill);
        Drawable back = getContext().getResources().getDrawable(R.drawable.radius_border_red);
        iconNote.setImageDrawable(img);
        txtNote.setText(R.string.note);
        txtNote.setTextColor(getContext().getResources().getColor(R.color.red));
        linearNote.setBackground(back);

    }

    @Override
    public void firstNote() {
        Drawable img = getContext().getResources().getDrawable(R.drawable.ic_add_note);
        Drawable back = getContext().getResources().getDrawable(R.drawable.radius_border);
        iconNote.setImageDrawable(img);
        txtNote.setText(R.string.note);
        txtNote.setTextColor(getContext().getResources().getColor(R.color.colorGrayMedium));
        linearNote.setBackground(back);
    }


    @Override
    protected void injectDagger() {
        DaggerDetailComponent.builder().
                appComponent(App.getAppComponent()).
                detailModule(new DetailModule(this)).
                build().injectToDetailActivity(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }


    private void setViewPager(List<String> imagePathList) {
        myAdapter = new ViewAdapter(DetailActivity.this, imagePathList, viewPager);
        viewPager.setAdapter(myAdapter);
        dotsCount = imagePathList.size();
        dots = new ImageView[dotsCount];
        if (dotsCount > 1) {
            for (int i = 0; i < dotsCount; i++) {

                dots[i] = new ImageView(DetailActivity.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotsPanel.addView(dots[i], params);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dot_select));

        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (dotsCount > 1) {
                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dot_select));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void showDetail(MainModel mainModel) {
        this.mainModel = mainModel;
        txtDate.setText(mainModel.getRegisterPDate());
        title.setText(mainModel.getTitle());
        if (mainModel.getIsBookMarked()) {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bookmark_fill);
            Drawable back = getContext().getResources().getDrawable(R.drawable.radius_border_red);
            iconBookmark.setImageDrawable(img);
            txtBookmark.setText(R.string.bookmark_selected);
            txtBookmark.setTextColor(getContext().getResources().getColor(R.color.red));
            linearBookmark.setBackground(back);

        }
        if (!mainModel.getIsBookMarked()) {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bookmark);
            Drawable back = getContext().getResources().getDrawable(R.drawable.radius_border);
            iconBookmark.setImageDrawable(img);
            txtBookmark.setTextColor(getContext().getResources().getColor(R.color.colorGrayMedium));
            txtBookmark.setText(R.string.bookmark_unselected);
            linearBookmark.setBackground(back);
//                    bookMark.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }
        if (mainModel.getLatitude() != null && mainModel.getLongitude() != null) {
            setLatLng(mainModel.getLatitude(), mainModel.getLongitude());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mainModel.getTitle() == null) {
            collapsingToolbarLayout.setTitle("nulll");
        } else {
            collapsingToolbarLayout.setTitle(mainModel.getTitle().toString() + "");
        }

        if (mainModel.getImages().size() > 0) {
            imagePathList = new ArrayList<>();
            for (int i = 0; i < mainModel.getImages().size(); i++) {
                imagePathList.add(mainModel.getImages().get(i));
            }
            setViewPager(imagePathList);
        }
        if ((mainModel.getAddress().equals(null)) || (mainModel.getAddress().equals(""))) {
            txtAddress.setText("آدرس" + " :" + " ");
        }
        if ((!mainModel.getAddress().equals(null)) || !(mainModel.getAddress().equals(""))) {
            txtAddress.setText("آدرس" + " :" + " " + mainModel.getAddress());
        }

        if ((mainModel.getDescription().equals(null)) || (mainModel.getDescription().equals(""))) {
            txtDescription.setText("توضیحات" + " :" + " ");
        }

        if ((!mainModel.getDescription().equals(null)) || !(mainModel.getDescription().equals(""))) {
            txtDescription.setText("توضیحات" + " :" + " " + mainModel.getDescription());
        }

    }

    @Override
    public void showNoteDetail(NoteDetailModel model) {
        this.noteDetailModel = model;
        txtDate.setText(mainModel.getRegisterPDate());
        title.setText(mainModel.getTitle());
        if (mainModel.getIsBookMarked()) {

            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bookmark_fill);
            Drawable back = getContext().getResources().getDrawable(R.drawable.radius_border_red);
            iconBookmark.setImageDrawable(img);
            txtBookmark.setText(R.string.bookmark_selected);
            txtBookmark.setTextColor(getContext().getResources().getColor(R.color.red));
            linearBookmark.setBackground(back);

        }
        if (!mainModel.getIsBookMarked()) {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bookmark);
            Drawable back = getContext().getResources().getDrawable(R.drawable.radius_border);
            iconBookmark.setImageDrawable(img);
            txtBookmark.setTextColor(getContext().getResources().getColor(R.color.colorGrayMedium));
            txtBookmark.setText(R.string.bookmark_unselected);
            linearBookmark.setBackground(back);
//                    bookMark.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }
        if (mainModel.getLatitude() != null && mainModel.getLongitude() != null) {
            setLatLng(mainModel.getLatitude(), mainModel.getLongitude());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mainModel.getTitle() == null) {
            collapsingToolbarLayout.setTitle("nulll");
        } else {
            collapsingToolbarLayout.setTitle(mainModel.getTitle().toString() + "");
        }

        if (mainModel.getImages().size() > 0) {
            imagePathList = new ArrayList<>();
            for (int i = 0; i < mainModel.getImages().size(); i++) {
                imagePathList.add(mainModel.getImages().get(i));
            }
            setViewPager(imagePathList);
        }
        if ((mainModel.getAddress().equals(null)) || (mainModel.getAddress().equals(""))) {
            txtAddress.setText("آدرس" + " :" + " ");
        }
        if ((!mainModel.getAddress().equals(null)) || !(mainModel.getAddress().equals(""))) {
            txtAddress.setText("آدرس" + " :" + " " + mainModel.getAddress());
        }

        if ((mainModel.getDescription().equals(null)) || (mainModel.getDescription().equals(""))) {
            txtDescription.setText("توضیحات" + " :" + " ");
        }

        if ((!mainModel.getDescription().equals(null)) || !(mainModel.getDescription().equals(""))) {
            txtDescription.setText("توضیحات" + " :" + " " + mainModel.getDescription());
        }
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
            case R.id.linear_bookmark:
                if (mainModel.getIsBookMarked()) {
                    presenter.deleteBookmark(detailId);
                }
                if (!mainModel.getIsBookMarked()) {
                    presenter.saveBookmark(detailId);

                }
                break;

            case R.id.linear_note:


                Intent intent = new Intent(this,CreateNoteActivity.class);
                intent.putExtra("detail_id",mainModel.getId());
                intent.putExtra("title",mainModel.getTitle());
                intent.putExtra("description",mainModel.getDescription());
                intent.putExtra("address",mainModel.getAddress());
                intent.putExtra("id",mainModel.getId());
                intent.putExtra("city",mainModel.getStateName());
                intent.putExtra("date",mainModel.getRegisterPDate());
                intent.putExtra("image",mainModel.getImages().get(0));
                startActivity(intent);

                break;

        }

    }

    @Override
    public void ShowProgressbar() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideProgressbar() {
        progress.setVisibility(View.GONE);

    }


    @Override
    public void changeBookmarkInModel(Boolean bool) {
        if (bool) {
//            presenter.deleteBookmark(id);
            txtBookmark.setText(R.string.bookmark_selected);
            txtBookmark.setTextColor(getContext().getResources().getColor(R.color.red));
            linearBookmark.setBackground(getContext().getResources().getDrawable(R.drawable.radius_border_red));
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bookmark_fill);
//            img.setBounds(60,60,0,0);
//            bookMark.setCompoundDrawables(img,null,null,null);
            iconBookmark.setImageDrawable(img);

//                    bookMark.setImageResource(R.drawable.ic_bookmark_selected);
        }
        if (!bool) {
//            presenter.saveBookmark(id);

            linearBookmark.setBackground(getContext().getResources().getDrawable(R.drawable.radius_border));
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bookmark);
//            img.setBounds(0, 0, 100, 80);
            txtBookmark.setText(R.string.bookmark_unselected);
            txtBookmark.setTextColor(getContext().getResources().getColor(R.color.colorGrayMedium));
            iconBookmark.setImageDrawable(img);

//                    bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);

        }
        mainModel.setIsBookMarked(bool);
    }


    private void showLocation() {
        mapView.onCreate(bundle);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                map = mapboxMap;
                manageCameraPosition();
                map.setStyle(new Style.Builder().fromUri(MapirStyle.MAIN_MOBILE_VECTOR_STYLE), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;
                        addSymbolToMap();
                        reverseGeocode(mapboxMap.getCameraPosition());
                        mapboxMap.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {

                                reverseGeocode(mapboxMap.getCameraPosition());
                            }
                        });

                    }
                });
                mapView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetailActivity.this, "mapview", Toast.LENGTH_SHORT).show();
                    }
                });


                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        return false;
                    }
                });


            }


        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}
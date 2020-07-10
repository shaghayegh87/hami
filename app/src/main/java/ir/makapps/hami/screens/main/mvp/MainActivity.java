package ir.makapps.hami.screens.main.mvp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;
import javax.inject.Inject;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.screens.main.dagger.MainModule;
import ir.makapps.hami.screens.main.fragments.addAdvertise.mvp.AddAdvertiseFragment;
import ir.makapps.hami.screens.main.fragments.bookmark.mvp.BookmarkFragment;
import ir.makapps.hami.screens.main.dagger.DaggerMainComponent;
import ir.makapps.hami.screens.main.fragments.home.mvp.HomeFragment;
import ir.makapps.hami.screens.main.fragments.note.mvp.NoteFragment;


public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainContract.Presenter mainPresenter;
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private BookmarkFragment bookmarkFragment;
    private AddAdvertiseFragment addFragment;
    private NoteFragment noteFragment;
    private String extra, lan, lat;
    private NavigationView navigation;
    private DrawerLayout drawerLayout;
    private BaseFragment baseFragment;
    private Bundle bundle;
    private MeowBottomNavigation meowBottomNavigation;
    private boolean clickedItem;
    private static final int home_id = 1;
    private static final int insert_id = 2;
    private static final int bookmark_id = 3;
    private static final int note_id = 4;
    private static final int profile_id = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo change check token in this activity

        mainPresenter.attachView(this);
        extra = getIntent().getStringExtra("fragment");
        if (extra != null) {
            checkMenuSelected();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void injectDagger() {
        DaggerMainComponent.builder().
                appComponent(App.getAppComponent()).
                mainModule(new MainModule(this)).
                build().injectToMainActivity(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    //    @Override
    public boolean checkInternet() {
        return mainPresenter.getInternet();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void bindViews() {
        navigation = findViewById(R.id.navigation_main);
        drawerLayout = findViewById(R.id.drawer_main);
        meowBottomNavigation = findViewById(R.id.bottom_navigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(home_id, R.drawable.ic_iconfinder_home_new));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(bookmark_id, R.drawable.ic_bookmark_new));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(insert_id, R.drawable.ic_iconfinder_add));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(note_id, R.drawable.ic_google_docs));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(profile_id, R.drawable.ic_user));


        if (homeFragment == null) {
            setFragment(new HomeFragment());
        } else setFragment(homeFragment);

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }

        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case home_id:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            mainPresenter.updateFragment(homeFragment);
                            setFragment(homeFragment);
                        } else setFragment(homeFragment);
                        break;


                    case insert_id:
                        if (addFragment == null) {
                            addFragment = new AddAdvertiseFragment();
                            mainPresenter.updateFragment(addFragment);
                            setFragment(addFragment);
                        } else setFragment(addFragment);
                        break;

                    case bookmark_id:
                        if (bookmarkFragment == null) {
                            bookmarkFragment = new BookmarkFragment();
                            mainPresenter.updateFragment(bookmarkFragment);
                            setFragment(bookmarkFragment);
                        } else setFragment(bookmarkFragment);
                        break;


                    case note_id:
                        if (noteFragment == null) {
                            noteFragment = new NoteFragment();
                            mainPresenter.updateFragment(noteFragment);
                            setFragment(noteFragment);
                        } else setFragment(noteFragment);
                        break;

                    case profile_id:
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                        break;
                }
            }
        });


        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case home_id:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            mainPresenter.updateFragment(homeFragment);
                            setFragment(homeFragment);
                        } else setFragment(homeFragment);
                        break;


                    case insert_id:
                        if (addFragment == null) {
                            addFragment = new AddAdvertiseFragment();
                            mainPresenter.updateFragment(addFragment);
                            setFragment(addFragment);
                        } else setFragment(addFragment);
                        break;

                    case bookmark_id:
//                        if (bookmarkFragment == null) {
                            bookmarkFragment = new BookmarkFragment();
                            mainPresenter.updateFragment(bookmarkFragment);
                            setFragment(bookmarkFragment);
//                        } else setFragment(bookmarkFragment);
                        break;


                    case note_id:
                        if (noteFragment == null) {
                            noteFragment = new NoteFragment();
                            mainPresenter.updateFragment(noteFragment);
                            setFragment(noteFragment);
                        } else setFragment(noteFragment);
                        break;

                    case profile_id:
                        drawerLayout.openDrawer(Gravity.RIGHT);
                        break;
                }
            }
        });
        meowBottomNavigation.show(home_id, true);
    }

    private void tokenGetNull() {

        Hawk.put("hami_token", "");
    }

    @Override
    public void setFragment(BaseFragment baseFragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragmentContainer, baseFragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 2040) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                addFragment.displayCamera();
//
//            } else Utils.showSnackbar(drawerLayout, "اپلیکیشن دسترسی لازم را ندارد.", "red");
//        }
//
//        if (requestCode == 2050) {
//            addFragment.choosePhotoFromGallery();
//        } else Utils.showSnackbar(drawerLayout, "اپلیکیشن دسترسی لازم را ندارد.", "red");
//    }

    public void checkMenuSelected() {

        switch (extra) {
            case "bookmark":
                bookmarkFragment = new BookmarkFragment();
                mainPresenter.updateFragment(bookmarkFragment);
//                bottomNavigation.setSelectedItemId(R.id.action_bookmark);
                break;
        }

    }

    @Override
    public void showError(String error) {

    }


}
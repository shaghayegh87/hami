package ir.makapps.hami.screens.splash.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import javax.inject.Inject;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.getValidationCode.mvp.GetValidationCodeActivity;
import ir.makapps.hami.screens.main.mvp.MainActivity;
import ir.makapps.hami.screens.splash.dagger.DaggerSplashComponent;
import ir.makapps.hami.screens.splash.dagger.SplashModule;
import ir.makapps.hami.utils.Utils;

public class SplashActivity extends BaseActivity implements SplashContract.View, View.OnClickListener {
  @Inject
  SplashContract.Presenter presenter;
  Thread splashTread;
  protected boolean _active = true;
  protected int _splashTime = 6000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.attachView(SplashActivity.this);
    splashTread.start();

  }


  @Override
  public void bindViews() {

  }

  @Override
  protected void injectDagger() {
    DaggerSplashComponent.builder().
            appComponent(App.getAppComponent()).
            splashModule(new SplashModule(this)).
            build().injectToSplashActivity(this);
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_splash;
  }



  @Override
  public void showError(String error) {
//       Utils.showSnackbar(view, error,"");
  }

  @Override
  public void onClick(View view) {


  }

  @Override
  public boolean hasToken() {
    if(Utils.hasToken())
      return  true;
    else
      return false;
  }




  @Override
  public void setDelay(final Boolean hasToken) {
    splashTread = new Thread() {
      @Override
      public void run() {
        try {
          int waited = 0;
          while (_active && (waited < _splashTime)) {
            sleep(100);
            if (_active) {
              waited += 100;
            }
          }
        } catch (Exception e) {

        } finally {
          if(hasToken)
          {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
          }
          else
          {
            startActivity(new Intent(SplashActivity.this, GetValidationCodeActivity.class));
            finish();
          }
        }
      }
    };

  }
}
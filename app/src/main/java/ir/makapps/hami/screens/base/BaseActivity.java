package ir.makapps.hami.screens.base;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected Context ctx;

    protected abstract void bindViews();
    protected abstract void injectDagger();
    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDagger();
        setContentView(getLayout());
        bindViews();

    }

    protected void showSnackbar(View parentLayout, String txt)
    {
        Snackbar snackbar = Snackbar.make(parentLayout,txt, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View SnackbarView = snackbar.getView();
        SnackbarView.setBackgroundColor(Color.RED);
        snackbar.show();
    }


    protected boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }
}

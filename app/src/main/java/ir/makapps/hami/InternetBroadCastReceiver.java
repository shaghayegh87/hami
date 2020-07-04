package ir.makapps.hami;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import ir.makapps.hami.di.App;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class InternetBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if(isConnected){
            Toast.makeText(App.getContext(), "اینترنت وصل است", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(App.getContext(), "اینترنت قطع است", Toast.LENGTH_SHORT).show();
        }
    }
}

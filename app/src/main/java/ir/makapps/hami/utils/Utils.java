package ir.makapps.hami.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.orhanobut.hawk.Hawk;

import ir.makapps.hami.di.App;

public class Utils {

    public static String errorInternet = "اینترنت وصل نیست";
    public static String errorInApp = "خطایی رخ داده است مجددا تلاش کنید";
    public static String no_connection_internet = "اینترنت وصل نیست";
    public static String empty_phone_number = "شماره همراه نباید خالی باشد.";
    public static String wrong_phone_number = "شماره وارد شده صحیح نیست";
    public static String wrong_confirm_number = "کد فعالسازی صحیح نیست";
    public static String empty_confirm_number = "کد فعالسازی نباید خالی باشد.";
    public static String insertSuccess = "اطلاعات با موفقیت ثبت شد بعد از بررسی منتشر میشود";
    public static String errorUnAuthorization = "شما مجوز ورود ندارید دوباره لاگین کنید";
    public static CharSequence camera = "دوربین";
    public static CharSequence Rotate = "چرخش عکس";
    public static CharSequence Delete = "حذف عکس";
    public static CharSequence gallery = "گالری";
    public static CharSequence close = "بستن";

//    public static void insertToHawk(String key, String value) {
//        Hawk.put(key, value);
//    }

    public static <T> T readFromHawk(String key, T defaultValue) {
        return Hawk.get(key, defaultValue);
    }


    public static <T> boolean insertToHawk(String key, T defaultValue) {
        return Hawk.put(key,defaultValue);
    }

    public static void showSnackbar(View parentLayout, String txt, String color) {
        Snackbar snackbar = Snackbar.make(parentLayout, txt, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        if (color == "red") {
            snackbarView.setBackgroundColor(Color.RED);
        } else if ((color == "green")) {
            snackbarView.setBackgroundColor(Color.GREEN);
        }

        snackbar.show();
    }

    public static boolean networkInfo()
    {
        Boolean internet = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null)
        {
            if (info.getType()==ConnectivityManager.TYPE_WIFI)
            {
//                Toast.makeText(App.getContext(), "اتصال از طریق Wifi", Toast.LENGTH_SHORT).show();
            }
            else if(info.getType() == ConnectivityManager.TYPE_MOBILE)
            {
//                Toast.makeText(App.getContext(), "اتصال از طریق Mobile", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            internet = false;
        }
        return internet;
    }

//    public static int produceSecurityNumber(String mobile) {
//        String defaultNumber = "6758143962";
//        String last5Digit = mobile.substring(mobile.length() - 5);
//        char[] arr = new char[3];
//        String newNumber = "";
//        int i = 0;
//        for (char c : last5Digit.toCharArray()) {
//            newNumber += defaultNumber.charAt(Integer.parseInt(c + ""));
//        }
//        return Integer.parseInt(newNumber);
//
//    }

    public static int produceSecurityNumber(String mobile) {
        String defaultNumber = "6758143962";
        String last5Digit = mobile.substring(mobile.length() - 5);
        char[] arr = new char[3];
        String newNumber = "";
        int i = 0;
        for (char c : last5Digit.toCharArray()) {

            newNumber += defaultNumber.charAt(Integer.parseInt(c + ""));
        }
        return Integer.parseInt(newNumber);
    }

    public static boolean isConnected() {
//        ConnectivityManager
//                cm = (ConnectivityManager) App.getAppComponent().getContext()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null
//                && activeNetwork.isConnectedOrConnecting();
        return true;
    }

    public static String getToken() {
        return Hawk.get("token_value","");
    }

    public static boolean hasToken() {
        String token = Hawk.get("token_value", "");
        if (!token.equals("")) {
            return true;
        } else return false;
    }


}

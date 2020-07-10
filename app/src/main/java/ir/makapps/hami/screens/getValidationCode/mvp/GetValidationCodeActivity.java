package ir.makapps.hami.screens.getValidationCode.mvp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.getToken.mvp.GetTokenActivity;
import ir.makapps.hami.screens.getValidationCode.dagger.DaggerGetValidationCodeComponent;
import ir.makapps.hami.screens.getValidationCode.dagger.GetValidationCodeModule;
import ir.makapps.hami.utils.Utils;

import static ir.makapps.hami.di.App.getContext;

public class GetValidationCodeActivity extends BaseActivity implements GetValidationCodeContract.View {


    @Inject
    GetValidationCodeContract.Presenter presenter;
    private ConstraintLayout parentView;
    private EditText editCheckNumber;
    private Button btnCheckNumber;
    private String phoneNumber;
    private int valueOfIntent;
    private int time;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
        valueOfIntent = getIntent().getIntExtra("Token_Activity", 0);
        if (valueOfIntent == 1) {
            getTime();
            String phone = Utils.readFromHawk("phone_number", "");
            if (!phone.isEmpty() && !phone.equals("") && phone.length() == 11)
                editCheckNumber.setText(phone);
        }
    }

    @Override
    public void injectDagger() {
        DaggerGetValidationCodeComponent.builder().
                appComponent(App.getAppComponent()).
                getValidationCodeModule(new GetValidationCodeModule(this)).
                build().injectToGetValidationCodeActivity(this);
    }

    @Override
    protected void bindViews() {
        parentView = findViewById(R.id.constraintLayout_validation_main);
        editCheckNumber = findViewById(R.id.editCheckNumber);
        btnCheckNumber = findViewById(R.id.btnCheckNumber);

        btnCheckNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = editCheckNumber.getText().toString();
                int securityNumber = 0;
                if (!phoneNumber.isEmpty() && !phoneNumber.equals("") && phoneNumber.length() == 11 && phoneNumber.startsWith("09")) {
//                    startLoading();
                    securityNumber = presenter.getSecurityNumber(phoneNumber);
                    Utils.insertToHawk("phone_number", phoneNumber);
                    presenter.getData(phoneNumber, securityNumber);
                } else if (!phoneNumber.equals("") || phoneNumber.length() > 11) {
                    editCheckNumber.setText("");
                    Utils.showSnackbar(parentView, Utils.wrong_phone_number, "red");

                } else {
                    editCheckNumber.setText("");
                    Utils.showSnackbar(parentView, Utils.empty_phone_number, "red");
                }

            }
        });
    }

    public void createAlertDialogProgress() {
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .setMessage(R.string.loading)
                .setCancelable(false)
                .build();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_get_validation_code;
    }


    @Override
    public void showConfirmCode(int code) {
        Toast.makeText(ctx, code + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String msg) {
        Utils.showSnackbar(parentView, msg, "green");
    }

    @Override
    public void showError(String error) {
        Utils.showSnackbar(parentView, error, "red");
    }


    @Override
    public void hideProgressBar() {
        alertDialog.dismiss();
    }

    @Override
    public void showProgressBar() {
        createAlertDialogProgress();
    }

    @Override
    public void changeColorOfViews() {
        editCheckNumber.setBackgroundResource(R.color.colorLight);
        btnCheckNumber.setBackgroundResource(R.color.colorLight);
        btnCheckNumber.setEnabled(false);
        editCheckNumber.setEnabled(false);
    }

    @Override
    public void changeColorToFirst() {
        editCheckNumber.setBackgroundResource(R.color.colorEditBackground);
        btnCheckNumber.setBackgroundResource(R.color.colorButtonBackground);
        btnCheckNumber.setText(getContext().getResources().getString(R.string.check_number));
        btnCheckNumber.setEnabled(true);
        editCheckNumber.setEnabled(true);
    }

    @Override
    public void goToNextActivity() {

        Intent i = new Intent(GetValidationCodeActivity.this, GetTokenActivity.class);
        startActivity(i);

    }

    @Override
    public void getTime() {
        time = 90;
        new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                btnCheckNumber.setText(" ارسال مجدد بعد از " + "0:" + checkDigit(time) + " ثانیه ");
                btnCheckNumber.setBackgroundResource(R.color.colorLight);
                btnCheckNumber.setEnabled(false);
                time--;
            }

            public void onFinish() {
                btnCheckNumber.setText("ارسال مجدد");
                btnCheckNumber.setBackgroundResource(R.color.colorButtonBackground);
                btnCheckNumber.setEnabled(true);
            }

        }.start();


    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}

package ir.makapps.hami.screens.getToken.mvp;

import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import javax.inject.Inject;

import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.screens.base.BaseActivity;
import ir.makapps.hami.screens.getToken.dagger.DaggerGetTokenComponent;
import ir.makapps.hami.screens.getToken.dagger.GetTokenModule;
import ir.makapps.hami.screens.getValidationCode.mvp.GetValidationCodeActivity;
import ir.makapps.hami.screens.main.mvp.MainActivity;
import ir.makapps.hami.utils.Utils;

public class GetTokenActivity extends BaseActivity implements GetTokenContract.View {

    @Inject
    GetTokenContract.Presenter presenter;
    ConstraintLayout getParentView;
    private ConstraintLayout parentView;
    private EditText editCheckConfirmCode;
    private Button btnGetToken;
    private String phoneNumber;
    private AnimatedCircleLoadingView animatedCircleLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    public void injectDagger() {
        DaggerGetTokenComponent.builder().
                appComponent(App.getAppComponent()).
                getTokenModule(new GetTokenModule(this)).
                build().injectToGetTokenActivity(this);
    }

    @Override
    protected void bindViews() {
        parentView = findViewById(R.id.constraintLayout_main);
        editCheckConfirmCode = findViewById(R.id.editCheckConfirmCode);
        btnGetToken = findViewById(R.id.btnGetToken);
//        animatedCircleLoadingView = findViewById(R.id.circle_loading_view);
        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = Utils.readFromHawk("phone_number", "");
                String confirmCode = editCheckConfirmCode.getText().toString();
                if (!confirmCode.isEmpty() && !confirmCode.equals("") && confirmCode.length() > 4 && !phoneNumber.equals("")) {
                    startLoading();
                    int code = Integer.parseInt(confirmCode);
                    presenter.getData(phoneNumber, code);
                } else if (confirmCode.equals("") || confirmCode.isEmpty()) {
                    editCheckConfirmCode.setText("");
                    Utils.showSnackbar(parentView, Utils.empty_confirm_number, "red");

                } else {
                    editCheckConfirmCode.setText("");
                    Utils.showSnackbar(parentView, Utils.wrong_confirm_number, "red");
                }
            }
        });

    }

    private void startLoading() {
//        animatedCircleLoadingView.startDeterminate();
    }


    @Override
    public void successProgressBar() {
//        animatedCircleLoadingView.stopOk();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_get_token;
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
//        animatedCircleLoadingView.stopFailure();
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        startLoading();

//        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void failureProgressBar() {
//


    }

    @Override
    public void changeColorOfViews() {
        editCheckConfirmCode.setBackgroundResource(R.color.colorLight);
        btnGetToken.setBackgroundResource(R.color.colorLight);
        btnGetToken.setEnabled(false);
        editCheckConfirmCode.setEnabled(false);
    }

    @Override
    public void changeColorToFirst() {
        editCheckConfirmCode.setBackgroundResource(R.color.colorEditBackground);
        btnGetToken.setBackgroundResource(R.color.colorButtonBackground);
        editCheckConfirmCode.setText("");
        btnGetToken.setEnabled(true);
        editCheckConfirmCode.setEnabled(true);
//        animatedCircleLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void goToNextActivity() {

        Intent i = new Intent(GetTokenActivity.this, MainActivity.class);
        startActivity(i);

    }

    @Override
    public void getValidationCodeAgain() {
        Intent i = new Intent(GetTokenActivity.this, GetValidationCodeActivity.class);
        i.putExtra("Token_Activity", 1);
        startActivity(i);
    }
}

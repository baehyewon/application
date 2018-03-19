package com.baehw1107.share_park5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baehw1107.share_park5.model.Response;
import com.baehw1107.share_park5.model.User;
import com.baehw1107.share_park5.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.baehw1107.share_park5.utils.Validation.validateEmail;
import static com.baehw1107.share_park5.utils.Validation.validateFields;

public class RegisterActivity extends AppCompatActivity {

    Intent intent;

    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtRegister;
    private TextView mTvLogin;
    private TextInputLayout mTiName;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressbar;

    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mSubscriptions = new CompositeSubscription();

        mEtName = (EditText) findViewById(R.id.et_name);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtRegister = (Button) findViewById(R.id.btn_register);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mTiName = (TextInputLayout) findViewById(R.id.ti_name);
        mTiEmail = (TextInputLayout) findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout)findViewById(R.id.ti_password);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);

        //회원가입 버튼을 눌렀을 때
        mBtRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                setError();

                String name = mEtName.getText().toString();
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                int err = 0;

                if (!validateFields(name)) {

                    err++;
                    mTiName.setError("Name should not be empty !");
                }

                if (!validateEmail(email)) {

                    err++;
                    mTiEmail.setError("Email should be valid !");
                }

                if (!validateFields(password)) {

                    err++;
                    mTiPassword.setError("Password should not be empty !");
                }

                if (err == 0) {

                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);

                    mProgressbar.setVisibility(View.VISIBLE);
                    registerProcess(user);

                } else {

                    showSnackBarMessage("Enter Valid Details !");
                }
            }
        });

        mTvLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setError() {
        mTiName.setError(null);
        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    //스낵바
    private void showSnackBarMessage(String message) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }
    //회원가입 진행 프로세스
    private void registerProcess(User user) {
        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
    //
    private void handleResponse(Response response) {
        mProgressbar.setVisibility(View.GONE);
        showSnackBarMessage(response.getMessage());

        intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
    //
    private void handleError(Throwable error) {
        mProgressbar.setVisibility(View.GONE);
        if (error instanceof HttpException) {
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showSnackBarMessage("Network Error !");
        }
    }


}

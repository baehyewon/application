package com.baehw1107.share_park5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baehw1107.share_park5.model.Response;
import com.baehw1107.share_park5.model.User;
import com.baehw1107.share_park5.network.NetworkUtil;
import com.baehw1107.share_park5.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.baehw1107.share_park5.utils.Validation.validateEmail;
import static com.baehw1107.share_park5.utils.Validation.validateFields;

public class LoginActivity extends AppCompatActivity {

    Intent intent0;

    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtLogin;
    private TextView mTvRegister;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    String email;


    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSubscriptions = new CompositeSubscription();

        mEtEmail = (EditText)findViewById(R.id.et_email);
        mEtPassword = (EditText)findViewById(R.id.et_password);
        mBtLogin = (Button)findViewById(R.id.btn_login);
        mTiEmail = (TextInputLayout)findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout)findViewById(R.id.ti_password);
        mTvRegister = (TextView)findViewById(R.id.tv_register);

        //로그인하기 버튼 눌렀을 때
        mBtLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                setError();


                email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                int err = 0;

                if (!validateEmail(email)) {

                    err++;
                    mTiEmail.setError("Email should be valid !");
                }

                if (!validateFields(password)) {

                    err++;
                    mTiPassword.setError("Password should not be empty !");
                }

                if (err == 0) {

                    loginProcess(email,password);
                    intent0 = new Intent(getApplicationContext(), ProfileActivity.class);
                    //intent.putExtra("email", email);
                    startActivity(intent0);

                } else {

                    showSnackBarMessage("Enter Valid Details !");
                }

            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(it);
            }
        });


    }

    //스낵바
    private void showSnackBarMessage(String message) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    //이메일과 패스워드가 빈칸이면 에러
    private void setError() {
        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    //로그인 진행 프로세스!
    private void loginProcess(String email, String password) {
        User tempUser = new User();
        tempUser.setEmail(email);
        tempUser.setPassword(password);
        mSubscriptions.add(NetworkUtil.getRetrofit(email, password).login(tempUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
    //로그인 정보 보내기
    private void handleResponse(Response response) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();

        //mEtEmail.setText(null);
        //mEtPassword.setText(null);
       // intent0 = new Intent(getApplicationContext(), ProfileActivity.class);
        //intent.putExtra("email", email);
        //startActivity(intent0);
    }
    //로그인 실패처리
    private void handleError(Throwable error) {

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
            showSnackBarMessage("network error!");
        }
    }
}

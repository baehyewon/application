package com.baehw1107.share_park5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baehw1107.share_park5.model.Response;
import com.baehw1107.share_park5.model.User;
import com.baehw1107.share_park5.network.NetworkUtil;
import com.baehw1107.share_park5.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfileActivity extends AppCompatActivity {
    //implements ChangePasswordDialog.Listener

    public static final String TAG = ProfileActivity.class.getSimpleName();

    //private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvDate;
    //private Button mBtChangePassword;
    private Button mBtLogout;
    Button btnMain;

    private ProgressBar mProgressbar;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;

    //Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mSubscriptions = new CompositeSubscription();

        mTvDate = (TextView)findViewById(R.id.tv_time);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        // 출력될 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        mTvDate.setText(simpleDateFormat.format(date));

        //Intent it = getIntent();
        //mEmail = it.getStringExtra("email");

        //mTvEmail = (TextView)findViewById(R.id.tv_email);
        //mTvEmail.setText(mEmail);

        initViews();
        initSharedPreferences();
        loadProfile();

        btnMain = (Button)findViewById(R.id.btn_go_main);
        btnMain.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                //it.putExtra("email", mEmail);
                startActivity(it);

            }
        });

    }

    private void initViews() {

        //mTvName = (TextView) findViewById(R.id.tv_name);
        mTvEmail = (TextView) findViewById(R.id.tv_email);
        // mBtChangePassword = (Button) findViewById(R.id.btn_change_password);
        mBtLogout = (Button) findViewById(R.id.btn_logout);
        //mProgressbar = (ProgressBar) findViewById(R.id.progress);

        //mBtChangePassword.setOnClickListener(view -> showDialog());
        mBtLogout.setOnClickListener(view -> logout());
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.TOKEN,"");
        editor.apply();
        finish();
    }
    /*
        private void showDialog(){

            ChangePasswordDialog fragment = new ChangePasswordDialog();

            Bundle bundle = new Bundle();
            bundle.putString(Constants.EMAIL, mEmail);
            bundle.putString(Constants.TOKEN,mToken);
            fragment.setArguments(bundle);

            fragment.show(getFragmentManager(), ChangePasswordDialog.TAG);
        }
    */
    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(User user) {

        mProgressbar.setVisibility(View.GONE);
        //mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());


        // mTvDate.setText(user.getCreated_at());
        //mTvEmail.setText(p_email);
    }

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
            showSnackBarMessage(" ");
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    /*
    @Override
    public void onPasswordChanged() {

        showSnackBarMessage("Password Changed Successfully !");
    }*/
}
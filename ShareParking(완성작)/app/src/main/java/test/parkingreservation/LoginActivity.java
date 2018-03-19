package test.parkingreservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener{

    private DBHelper mDBHelper;

    private EditText edtId, edtPw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDBHelper = new DBHelper(this, DBConstants.DATABASE_NAME);

        edtId = (EditText)findViewById(R.id.al_edt_id);
        edtPw = (EditText)findViewById(R.id.al_edt_pw);

        btnLogin = (Button)findViewById(R.id.al_btn_login);
        btnLogin.setOnClickListener(this);
    }

    public String getUserMode(String userid, String pw)
    {
        String Mode = "";

        List<UserInfo> UserInfoList = mDBHelper.getUserInfoList("", new String[] {});

        for(int i=0; i<UserInfoList.size(); i++) {

            UserInfo item = UserInfoList.get(i);

            if(userid.equals(item.getUserid()) == true)
            {
                if(pw.equals(item.getPassword()) == true)
                {
                    Mode = item.getMode();
                }
            }
        }

        return Mode;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.al_btn_login:

                String strId = edtId.getText().toString();
                String strPw = edtPw.getText().toString();

                if(strId.equals(""))
                {
                    Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strPw.equals(""))
                {
                    Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String strMode = getUserMode(strId, strPw);

                Intent intent = new Intent();
                intent.putExtra(Define.INTENT_MODE, strMode);

                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

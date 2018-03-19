package test.parkingreservation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 메인 메뉴 팝업
 */
public class MainPopupActivity extends Activity implements View.OnClickListener {

    private Button btnMode, btnOk;
    private EditText edtKey;

    private Dialog DlgManager = null;

    private DBHelper mDBHelper;

    private String CurMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_popup);

        mDBHelper = new DBHelper(this, DBConstants.DATABASE_NAME);
        btnMode = (Button)findViewById(R.id.amp_btn_change_mode);
        btnMode.setOnClickListener(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();

        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        Intent intent = getIntent();
        CurMode = intent.getStringExtra(Define.INTENT_MODE);

        if(CurMode.equals(Define.USER_MODE))
            btnMode.setText("관리자모드 로그인");
        else
            btnMode.setText("관리자모드 로그아웃");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.amp_btn_change_mode:

                if(CurMode.equals(Define.USER_MODE)) {
                    DlgManager = new Dialog(this, android.R.style.Theme_Holo_Dialog);
                    DlgManager.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    DlgManager.setContentView(R.layout.dialog_change_manager);

                    edtKey = (EditText) DlgManager.findViewById(R.id.dlg_edt_key);
                    btnOk = (Button) DlgManager.findViewById(R.id.dlg_btn_ok);
                    btnOk.setOnClickListener(this);
                    DlgManager.show();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.putExtra(Define.INTENT_MODE, Define.USER_MODE);

                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

            case R.id.dlg_btn_ok:

                String strKey = edtKey.getText().toString();
                if(strKey.equals(""))
                {
                    Toast.makeText(this, "매니저 모드로 전환할 Key를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean bConfirm = false;

                List<UserInfo> UserInfoList = mDBHelper.getUserInfoList("", new String[] {});

                for(int i=0; i<UserInfoList.size(); i++) {

                    UserInfo item = UserInfoList.get(i);

                    //매니저 모드일 경우
                    if(item.getMode().equals(Define.MANAGER_MODE))
                    {
                        //입력한 키값과 매니저모드의 패스워드가 일치할경우
                        if(strKey.equals(item.getPassword()))
                        {
                            bConfirm = true;
                            Intent intent = new Intent();
                            intent.putExtra(Define.INTENT_MODE, Define.MANAGER_MODE);

                            setResult(RESULT_OK, intent);
                            finish();
                            return;
                        }
                    }
                }

                if(bConfirm == false)
                {
                    Toast.makeText(this, "Key가 일치하지 않습니다.\n다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    edtKey.setText("");
                    return;
                }
                break;
        }
    }
}

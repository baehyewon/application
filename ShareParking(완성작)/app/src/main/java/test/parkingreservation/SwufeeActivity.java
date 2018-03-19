package test.parkingreservation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SwufeeActivity extends Activity {

    TextView tv_swufee, tv_swufee2, tv_swufee_student;
    String str_swufee, str_swufee2, str_swufee_student, str_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swufee);

        tv_swufee = (TextView)findViewById(R.id.tv_swufee) ;
        tv_swufee2 = (TextView)findViewById(R.id.tv_swufee2) ;
        tv_swufee_student = (TextView)findViewById(R.id.tv_swufee_student);

        str_swufee ="           서울여자대학교 시간 당 주차요금 \n\n" + "* 외부인 : 입차 후 30분까지 -> 1000원\n" + "                       이후 10분 당 ->    500원 추가";
        str_swufee_student = "* 본교학생/재직자 :     종일 -> 1500원";
        str_swufee2 = "\n①입차 후 요금 정산소 출차시간 기준 10분간 무료 \n"
                + "②일요일/공휴일은 무료\n"
                + "③8:30 ~ 9:30은 교통 혼잡으로 인해 10분 무료 없음";

        tv_swufee.setText(str_swufee);
        tv_swufee_student.setText(str_swufee_student);
        tv_swufee2.setText(str_swufee2);

    }
}

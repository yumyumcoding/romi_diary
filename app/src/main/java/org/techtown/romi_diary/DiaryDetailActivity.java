package org.techtown.romi_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvDate; //일시 설정 텍스트
    private EditText mEtTitle,mEtcontent; // 일기 제목, 일기 내용
    private RadioGroup mRgWeather;

    private String mDetailMode = "";//intent로 받아낸 게시글 모드
    private String mBeforeDate = ""; //intent로 받아낸 게시글 기존 작성 일자
    private String mSelectedUserDate = ""; //선택된 일시 값
    private int mSelectedWeatherType = -1; //선택된 날씨 값


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        mTvDate = findViewById(R.id.tv_date);
        mEtTitle = findViewById(R.id.et_title);
        mEtcontent = findViewById(R.id.et_content);
        mRgWeather = findViewById(R.id.rg_weather);

        ImageView iv_back = findViewById(R.id.iv_back);
        ImageView iv_check = findViewById(R.id.iv_check);

        mTvDate.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_check.setOnClickListener(this);//클릭 기능 부여

        //기본으로 설정될 날짜의 값을 지정 (디바이스 현재 시간 기준)
        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(new Date());
        mTvDate.setText(mSelectedUserDate);
    }

    @Override
    public void onClick(View view) {
        //setOnClickListener가 붙어있는 뷰들은 클릭이 발생하면 모두 이곳을
        switch (view.getId()){
            case R.id.iv_back:
                //뒤로가기
                finish();
                break;
            case R.id.iv_check:
                //작성완료
                //라디오 그룹의 버튼 클릭 현재 상황 가져오기
                mSelectedWeatherType = mRgWeather.indexOfChild(findViewById(mRgWeather.getCheckedRadioButtonId()));
                //입력 필드 작성란이 비어있는지 체크
                if (mEtTitle.getText().length() == 0 || mEtcontent.getText().length()==0){
                    Toast.makeText(this,"입력되지 않은 필드가 존재합니다.",Toast.LENGTH_SHORT).show();
                    return; //밑의 로직 실행 x
                }

                //날씨 선택이 되어있는지 체크

                if(mSelectedWeatherType==-1){
                    //error
                    Toast.makeText(this,"날씨를 선택해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                /////////////에러 없으므로 데이터 저장
                String title = mEtTitle.getText().toString();           // 제목 입력 값
                String content = mEtcontent.getText().toString();       // 내용 입력 값
                String userDate = mSelectedUserDate;                    // 사용자가 선택한 일시

                String writeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN).format(new Date());
                finish(); //현재 액티비티 종료
                break;


            case R.id.tv_date:
                //일시 설정 텍스트

                //달력을 띄워서 사용자에게 일시를 입력 받는다.
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //달력에 선택된 (년,월,일) 을 가지고 와서 다시 캘린더 함수에 넣어줘서 사용자가 선택한 요일을 알아낸다.
                        Calendar innerCal = Calendar.getInstance();
                        innerCal.set(Calendar.YEAR,year);
                        innerCal.set(Calendar.MONTH,month);
                        innerCal.set(Calendar.DATE,dayOfMonth);

                        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(innerCal.getTime());
                        mTvDate.setText(mSelectedUserDate);

                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                dialog.show();// 다이얼로그 활성화!
                break;

        }
    }
}
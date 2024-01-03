package org.techtown.romi_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRvDiary;
    DiaryListAdapter mAdapter;
    ArrayList<DiaryModel> mLstDiary;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //액티비티가 시작할 때 최초 1회만 실행
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터 베이스 객체 초기화
        mDatabaseHelper = new DatabaseHelper(this);

        mLstDiary = new ArrayList<>();

        mRvDiary = findViewById(R.id.rv_diary);

        mAdapter = new DiaryListAdapter();

//        //다이어리 샘플 아이템 1개 생성
//        DiaryModel item = new DiaryModel();
//        item.setId(0);
//        item.setTitle("다이어리 어플 만들기 도전!");
//        item.setContent("1일차");
//        item.setUserDate("2023/12/17/SAT");
//        item.setWriteDate("2023/12/17/SAT");
//        item.setWeatherType(0);
//        mLstDiary.add(item);
//
//        DiaryModel item2 = new DiaryModel();
//        item2.setId(0);
//        item2.setTitle("다이어리 어플 만들기 도전!");
//        item2.setContent("2일차");
//        item2.setUserDate("2023/12/17/SAT");
//        item2.setWriteDate("2023/12/17/SAT");
//        item2.setWeatherType(1);
//        mLstDiary.add(item2);
//
//        DiaryModel item3 = new DiaryModel();
//        item3.setId(0);
//        item3.setTitle("다이어리 어플 만들기 도전!");
//        item3.setContent("3일차");
//        item3.setUserDate("2023/12/17/SAT");
//        item3.setWriteDate("2023/12/17/SAT");
//        item3.setWeatherType(2);
//        mLstDiary.add(item3);

        mRvDiary.setAdapter(mAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        floatingActionButton.setOnClickListener(view -> {
            //작성하기 버튼을 눌렀을 때 호출되는 곳
            Intent intent = new Intent(MainActivity.this,DiaryDetailActivity.class);
            //작성하기 화면으로 이동
            startActivity(intent);
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        //액티비티의 재개

        //get load list
        setLoadRecentList();
    }

    private void setLoadRecentList(){

        //최근 데이터 베이스 정보를 가져와서 리사이클러뷰에 갱신해준다.

        //이전에 배열 리스트에 저장된 데이터가 있으면 비워버림.
        if (!mLstDiary.isEmpty()){
            mLstDiary.clear();
        }

        mLstDiary = mDatabaseHelper.getDiaryListFromDB();//데이터 베이스로부터 저장되어 있는 DB를 확인하여 가지고 옴.
        mAdapter.setListInit(mLstDiary);
    }
}
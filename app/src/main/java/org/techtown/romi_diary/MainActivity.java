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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLstDiary = new ArrayList<>();

        mRvDiary = findViewById(R.id.rv_diary);

        mAdapter = new DiaryListAdapter();

        //다이어리 샘플 아이템 1개 생성
        DiaryModel item = new DiaryModel();
        item.setId(0);
        item.setTitle("다이어리 어플 만들기 도전!");
        item.setContent("1일차");
        item.setUserDate("2023/12/17/SAT");
        item.setWriteDate("2023/12/17/SAT");
        item.setWeatherType(0);
        mLstDiary.add(item);

        DiaryModel item2 = new DiaryModel();
        item2.setId(0);
        item2.setTitle("다이어리 어플 만들기 도전!");
        item2.setContent("2일차");
        item2.setUserDate("2023/12/17/SAT");
        item2.setWriteDate("2023/12/17/SAT");
        item2.setWeatherType(1);
        mLstDiary.add(item2);

        DiaryModel item3 = new DiaryModel();
        item3.setId(0);
        item3.setTitle("다이어리 어플 만들기 도전!");
        item3.setContent("3일차");
        item3.setUserDate("2023/12/17/SAT");
        item3.setWriteDate("2023/12/17/SAT");
        item3.setWeatherType(2);
        mLstDiary.add(item3);

        mAdapter.setSampleList(mLstDiary);
        mRvDiary.setAdapter(mAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //작성하기 버튼을 눌렀을 때 호출되는 곳
                Intent intent = new Intent(MainActivity.this,DiaryDetailActivity.class);
                //작성하기 화면으로 이동
                startActivity(intent);
            }
        });
    }
}
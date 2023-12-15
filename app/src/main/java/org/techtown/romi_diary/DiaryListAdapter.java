package org.techtown.romi_diary;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {

    ArrayList<DiaryModel> mLstDiary; //다이어리 데이터들을 들고있는 자료형
    Context mContext;

    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View holder = LayoutInflater.from(mContext).inflate(R.layout.list_item_diary, parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        int weatherType = mLstDiary.get(position).getWeatherType();

        switch (weatherType){
            case 0:
                //맑음
                holder.iv_weather.setImageResource(R.drawable.sun);
                break;
            case 1:
                //조금 흐림
                holder.iv_weather.setImageResource(R.drawable.cloudy);
                break;
            case 2:
                //흐림
                holder.iv_weather.setImageResource(R.drawable.bad_cloud);
                break;
            case 3:
                //바람
                holder.iv_weather.setImageResource(R.drawable.windy);
                break;
            case 4:
                //비
                holder.iv_weather.setImageResource(R.drawable.rain);
                break;
            case 5:
                //눈
                holder.iv_weather.setImageResource(R.drawable.snow);
               break;
        }

        String title = mLstDiary.get(position).getTitle();
        String userDate = mLstDiary.get(position).getUserDate();

        holder.tv_title.setText(title);
        holder.tv_user_date.setText(userDate);
    }

    @Override
    public int getItemCount() {
        return mLstDiary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_weather;
        TextView tv_title,tv_user_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_weather = itemView.findViewById(R.id.iv_weather);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_user_date = itemView.findViewById(R.id.tv_user_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = getAdapterPosition();
                    DiaryModel diaryModel = mLstDiary.get(currentPosition);

                    Intent diaryDetailIntent = new Intent(mContext, DiaryDetailActivity.class);
                    diaryDetailIntent.putExtra("diaryModel",diaryModel);//다이어리 데이터 넘기기
                    diaryDetailIntent.putExtra("mode","detail"); //상세보기 모드로 설정
                    mContext.startActivity(diaryDetailIntent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int currentPosition = getAdapterPosition();
                    DiaryModel diaryModel = mLstDiary.get(currentPosition);

                    String[] strChoiceArray = {"수정 하기","삭제 하기"};
                    //팝업 화면 표시
                    new AlertDialog.Builder(mContext)
                            .setTitle("원하시는 동작을 선택하세요")
                            .setItems(strChoiceArray, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position) {
                                    if (position == 0) {
                                        Intent diaryDetailIntent = new Intent(mContext, DiaryDetailActivity.class);
                                        diaryDetailIntent.putExtra("diaryModel", diaryModel);//다이어리 데이터 넘기기
                                        diaryDetailIntent.putExtra("mode", "modify"); //수정 모드로 설정
                                        mContext.startActivity(diaryDetailIntent);
                                    } else {
                                        mLstDiary.remove(currentPosition);
                                        notifyItemRemoved(currentPosition);
                                    }
                                }
                            }).show();
                    return false;
                }
            });
        }
    }

    public void setSampleList(ArrayList<DiaryModel> lstDiary){
        mLstDiary = lstDiary;
    }
}

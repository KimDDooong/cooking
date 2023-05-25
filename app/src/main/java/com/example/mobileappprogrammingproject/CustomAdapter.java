package com.example.mobileappprogrammingproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private static ArrayList<DataModel> localDataSet;





    //===== 뷰홀더 클래스 =====================================================
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private LinearLayout buttonLayout;
        private Button button1;
        private Button button2;

        private Context context;


        private TodayAdapter todayAdapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.k_food_text);
            mImageView = itemView.findViewById(R.id.k_food_image);
            buttonLayout = itemView.findViewById(R.id.buttonLayout);
            button1 = itemView.findViewById(R.id.button1);
            button2 = itemView.findViewById(R.id.button2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    toggleButtonLayout(); // 버튼 레이아웃의 가시성을 토글
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        //Toast.makeText(view.getContext(), position+"번째 텍스트 뷰 클릭", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String data = localDataSet.get(position).getTitle();

                        // DatabaseHelper 클래스의 인스턴스 생성
                        DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());

                        // 데이터베이스에 데이터 삽입
                        long result = databaseHelper.insertData(data);

                        if (result != -1) {
                            // 삽입 작업이 성공한 경우
                            Toast.makeText(view.getContext(), data +"을(를)담았습니다.", Toast.LENGTH_SHORT).show();

                            // 데이터베이스에서 항목을 로드하고 어댑터에 설정
                            todayAdapter = new TodayAdapter(new ArrayList<>());

                            ArrayList<TodayItem> items = DatabaseHelper.loadItemsFromDatabase(databaseHelper);
                            todayAdapter.setData(items);
                            todayAdapter.notifyDataSetChanged();
                        } else {
                            // 삽입 작업이 실패한 경우
                           // Toast.makeText(view.getContext(), "데이터 삽입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        }

        private void toggleButtonLayout() {
            if (buttonLayout.getVisibility() == View.VISIBLE) {
                buttonLayout.setVisibility(View.GONE);
            } else {
                buttonLayout.setVisibility(View.VISIBLE);
            }
        }
        public TextView getTextView() {
            return mTextView;
        }
    }
    //========================================================================

    //----- 생성자 --------------------------------------
    // 생성자를 통해서 데이터를 전달받도록 함
    public CustomAdapter (ArrayList<DataModel> dataSet) {
        this.localDataSet = dataSet;
    }
    //--------------------------------------------------

    @NonNull
    @Override   // ViewHolder 객체를 생성하여 리턴한다.
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.korean_food, parent, false);
        CustomAdapter.ViewHolder viewHolder = new CustomAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override   // ViewHolder안의 내용을 position에 해당되는 데이터로 교체한다.
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mTextView.setText(localDataSet.get(position).getTitle());
        viewHolder.mImageView.setImageResource(localDataSet.get(position).image_path);

    }

    @Override   // 전체 데이터의 갯수를 리턴한다.
    public int getItemCount() {
        return localDataSet.size();
    }


}
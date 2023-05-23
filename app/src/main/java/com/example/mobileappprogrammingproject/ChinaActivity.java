package com.example.mobileappprogrammingproject;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        TextView tv = (TextView)findViewById(R.id.country_name_text);
        tv.setText("중국 음식");
        //===== 테스트를 위한 더미 데이터 생성 ===================
        ArrayList<DataModel> testDataSet = new ArrayList<>();

        testDataSet.add(new DataModel("짜장면",R.drawable.flag_china));
        testDataSet.add(new DataModel("짬뽕",R.drawable.flag_eu));
        testDataSet.add(new DataModel("탕수육",R.drawable.flag_global));
        testDataSet.add(new DataModel("깐풍기",R.drawable.flag_korea));
        testDataSet.add(new DataModel("간짜장",R.drawable.flag_japan));




        //========================================================

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //--- LayoutManager는 아래 3가지중 하나를 선택하여 사용 ---
        // 1) LinearLayoutManager()
        // 2) GridLayoutManager()
        // 3) StaggeredGridLayoutManager()
        //---------------------------------------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        CustomAdapter customAdapter = new CustomAdapter(testDataSet);
        recyclerView.setAdapter(customAdapter); // 어댑터 설정


    }
}
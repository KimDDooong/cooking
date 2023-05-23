package com.example.mobileappprogrammingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Tab_recipe extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_tab_recipe, container, false);

        Button btn_korea = (Button) rootview.findViewById(R.id.koreanButton);
        Button btn_china = (Button) rootview.findViewById(R.id.chineseButton);
        Button btn_japan = (Button) rootview.findViewById(R.id.japaneseButton);
        Button btn_western = (Button) rootview.findViewById(R.id.westernButton);
        Button btn_etc = (Button) rootview.findViewById(R.id.etcButton);

        btn_korea.setOnClickListener(this);
        btn_china.setOnClickListener(this);
        btn_japan.setOnClickListener(this);
        btn_western.setOnClickListener(this);
        btn_etc.setOnClickListener(this);





        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.koreanButton:
                startKoreaActivity();
                break;
            case R.id.japaneseButton:
                startJapanActivity();
                break;
            case R.id.chineseButton:
                startChinaActivity();
                break;
            case R.id.westernButton:
                startWesternActivity();
                break;
            case R.id.etcButton:
                startEtcActivity();
                break;
        }
    }

    private void startKoreaActivity() {
        Intent intent = new Intent(getActivity(),KoreaActivity.class);
        startActivity(intent);
    }
    private void startChinaActivity() {
        Intent intent = new Intent(getActivity(),ChinaActivity.class);
        startActivity(intent);
    }
    private void startJapanActivity() {
        Intent intent = new Intent(getActivity(),JapanActivity.class);
        startActivity(intent);
    }
    private void startWesternActivity() {
        Intent intent = new Intent(getActivity(),WesternActivity.class);
        startActivity(intent);
    }
    private void startEtcActivity() {
        Intent intent = new Intent(getActivity(),EtcActivity.class);
        startActivity(intent);
    }
}

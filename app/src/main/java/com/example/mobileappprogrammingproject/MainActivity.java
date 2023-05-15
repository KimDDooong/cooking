package com.example.mobileappprogrammingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends FragmentActivity {

    TabLayout tabs;

    Tab_map tab_map;
    Tab_recipe tab_recipe;
    Tab_timer tab_timer;
    Tab_today tab_today;

    FragmentManager fragmentManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout);
        tab_recipe = new Tab_recipe();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,tab_recipe).commit();

        tabs = findViewById(R.id.tab);
        tabs.addTab(tabs.newTab().setText("레시피 목록"));
        tabs.addTab(tabs.newTab().setText("오늘의 메뉴"));
        tabs.addTab(tabs.newTab().setText("맛집 지도"));
        tabs.addTab(tabs.newTab().setText("쿡타이머"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                //Fragment selected = null;
                if(position == 0){
                    if(tab_recipe == null) {
                        tab_recipe = new Tab_recipe();
                        fragmentManager.beginTransaction().add(R.id.container, tab_recipe).commit();
                    }
                    if(tab_recipe != null) fragmentManager.beginTransaction().show(tab_recipe).commit();
                    if(tab_today != null) fragmentManager.beginTransaction().hide(tab_today).commit();
                    if(tab_map != null) fragmentManager.beginTransaction().hide(tab_map).commit();
                    if(tab_timer != null) fragmentManager.beginTransaction().hide(tab_timer).commit();

                }
                    //selected = tab_recipe;
                else if(position == 1){
                    if(tab_today == null) {
                        tab_today = new Tab_today();
                        fragmentManager.beginTransaction().add(R.id.container, tab_today).commit();
                    }
                    if(tab_recipe != null) fragmentManager.beginTransaction().hide(tab_recipe).commit();
                    if(tab_today != null) fragmentManager.beginTransaction().show(tab_today).commit();
                    if(tab_map != null) fragmentManager.beginTransaction().hide(tab_map).commit();
                    if(tab_timer != null) fragmentManager.beginTransaction().hide(tab_timer).commit();

                }
                    //selected = tab_today;
                else if(position == 2){
                    if(tab_map == null) {
                        tab_map = new Tab_map();
                        fragmentManager.beginTransaction().add(R.id.container, tab_map).commit();
                    }
                    if(tab_recipe != null) fragmentManager.beginTransaction().hide(tab_recipe).commit();
                    if(tab_today != null) fragmentManager.beginTransaction().hide(tab_today).commit();
                    if(tab_map != null) fragmentManager.beginTransaction().show(tab_map).commit();
                    if(tab_timer != null) fragmentManager.beginTransaction().hide(tab_timer).commit();

                }
                    //selected = tab_map;
                else{
                    if(tab_timer == null) {
                        tab_timer = new Tab_timer();
                        fragmentManager.beginTransaction().add(R.id.container, tab_timer).commit();
                    }
                    if(tab_recipe != null) fragmentManager.beginTransaction().hide(tab_recipe).commit();
                    if(tab_today != null) fragmentManager.beginTransaction().hide(tab_today).commit();
                    if(tab_map != null) fragmentManager.beginTransaction().hide(tab_map).commit();
                    if(tab_timer != null) fragmentManager.beginTransaction().show(tab_timer).commit();

                }
                    //selected = tab_timer;

                //getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
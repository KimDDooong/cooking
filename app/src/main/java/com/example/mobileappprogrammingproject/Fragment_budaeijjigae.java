package com.example.mobileappprogrammingproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_budaeijjigae extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budaejjigae, container, false);
        Button backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fragment를 호스팅하는 Activity에서 현재 Fragment를 제거합니다.
                requireActivity().getSupportFragmentManager().beginTransaction().remove(Fragment_budaeijjigae.this).commit();
            }
        });
        return rootView;
    }
}

package com.example.mobileappprogrammingproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Tab_today extends Fragment {
    private RecyclerView recyclerView;
    private TodayAdapter todayAdapter;

    private DatabaseHelper databaseHelper;
    private Fragment_kimchijjigae fragment_kimchijjigae;
    private Fragment_budaeijjigae fragment_budaeijjigae;
    private FragmentManager fragmentManager;
    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_today, container, false);
        recyclerView = rootView.findViewById(R.id.today_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        todayAdapter = new TodayAdapter(new ArrayList<>());
        recyclerView.setAdapter(todayAdapter);

        databaseHelper = new DatabaseHelper(getContext());

        // 데이터베이스에서 항목을 로드하고 어댑터에 설정
        ArrayList<TodayItem> items = DatabaseHelper.loadItemsFromDatabase(databaseHelper);
        todayAdapter.setData(items);
        todayAdapter.notifyDataSetChanged();

        return rootView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 아이템을 클릭했을 때의 동작을 정의합니다
        todayAdapter.setOnItemClickListener(new TodayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TodayItem item) {
                if (fragment_kimchijjigae == null) {
                    fragment_kimchijjigae = new Fragment_kimchijjigae();
                }
                if (fragment_budaeijjigae == null) {
                    fragment_budaeijjigae = new Fragment_budaeijjigae();
                }

                String food = item.getName();
                fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (food) {
                    case "김치찌개":fragmentTransaction.replace(R.id.fragment_container, fragment_kimchijjigae);
                                    break;
                    case "부대찌개":fragmentTransaction.replace(R.id.fragment_container, fragment_budaeijjigae);
                        break;
                }


                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

            @Override
            public void onItemLongClick(TodayItem item) {
                showDeleteDialog(item);
            }
        });
    }

    private void showDeleteDialog(TodayItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("삭제 확인")
                .setMessage(item.getName() + "을(를) 삭제하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItemFromDatabase(item);
                        showToast(item.getName() + "을(를) 삭제했습니다.");
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 팝업 창을 표시하는 메서드를 정의합니다
    private void showPopup(String itemName) {
        // 팝업 창을 생성하고 내용을 설정합니다
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("팝업 창")
                .setMessage(itemName + "을(를) 선택했습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인 버튼을 클릭했을 때의 동작을 정의합니다
                        dialog.dismiss(); // 팝업 창 닫기
                    }
                });

        // 팝업 창을 표시합니다
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void deleteItemFromDatabase(TodayItem item) {
        String name = item.getName();
        int result = databaseHelper.deleteData(name);
        if (result > 0) {
            showToast(name + "을(를) 삭제했습니다.");
            // 삭제된 항목을 어댑터에서도 제거합니다.
            todayAdapter.getData().remove(item);
            todayAdapter.notifyDataSetChanged();
        } else {
            showToast("삭제에 실패했습니다.");
        }
    }


}

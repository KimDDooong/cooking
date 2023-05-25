package com.example.mobileappprogrammingproject;

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
import java.util.List;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    private static ArrayList<TodayItem> dataSet;
    private static OnItemClickListener listener;
    public void setData(ArrayList<TodayItem> itemList) {
        dataSet = itemList;
    }
    public ArrayList<TodayItem> getData() {
        return dataSet;
    }


    public interface OnItemClickListener {
        void onItemClick(TodayItem item);

        void onItemLongClick(TodayItem item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.k_food_text_2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(dataSet.get(position));
                    }
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }




    public TodayAdapter(ArrayList<TodayItem> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public TodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAdapter.ViewHolder holder, int position) {
        TodayItem item = dataSet.get(position);
        holder.getTextView().setText(item.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onItemLongClick(item);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}

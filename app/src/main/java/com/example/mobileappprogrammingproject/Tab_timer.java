package com.example.mobileappprogrammingproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class Tab_timer extends Fragment {
    private int seconds;
    private boolean running;
    private boolean wasRunning;

    private FloatingActionButton startButton;
    private FloatingActionButton stopButton;
    private FloatingActionButton plusButton;
    private FloatingActionButton plus30Button;
    private FloatingActionButton minusButton;
    private FloatingActionButton resetButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_timer, container, false);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startButton = view.findViewById(R.id.start);
        stopButton = view.findViewById(R.id.stop);
        plusButton = view.findViewById(R.id.plus);
        plus30Button = view.findViewById(R.id.btn2);
        minusButton = view.findViewById(R.id.btn3);
        resetButton = view.findViewById(R.id.reset);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlus();
            }
        });

        plus30Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlus_30();
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMinus();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReset();
            }
        });

        runTimer();
    }

    public void onStart() {
        super.onStart();
        running = true;
    }

    public void onStop() {
        super.onStop();
        running = false;
    }

    public void onPlus() {
        seconds += 600;
    }

    public void onPlus_30() {
        seconds += 300;
    }

    public void onMinus() {
        if (seconds >= 600) {
            seconds -= 600;
        } else {
            seconds = 0;
        }
    }

    public void onReset() {
        running = false;
        seconds = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    private void runTimer() {
        final TextView timeView = getView().findViewById(R.id.textView);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int now = seconds / 10;
                int hours = now / 3600;
                int minutes = (now % 3600) / 60;
                int secs = now % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if (running && seconds > 0) {
                    seconds--;
                }

                if (seconds == 0) {
                    running = false;
                }

                handler.postDelayed(this, 100);
            }
        });
    }
}

package com.learnpainless.analogclock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Attr;

import java.nio.file.attribute.AttributeView;

public class MainActivity extends AppCompatActivity {
    private TextView timeTextView;
    private Button startButton, stopButton, resetButton,lapButton;
    private Handler handler;
    private long startTime, elapsedTime;
    private boolean running;
    MyView myView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.clock);

        timeTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        lapButton = findViewById(R.id.lapButton);


        handler = new Handler();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    if (elapsedTime > 0) {
                        startTime = System.currentTimeMillis() - elapsedTime;
                    } else {
                        startTime = System.currentTimeMillis();
                    }
                    running = true;
                    runTimer();
                }
            }
        });

        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    running = false;
                    elapsedTime = System.currentTimeMillis() - startTime;
                    Log.e("TAG", "onClick: "+elapsedTime );
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                elapsedTime = 0;
                updateTimer(0);
            }
        });
    }

    private void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    long currentTime = System.currentTimeMillis();
                    elapsedTime = currentTime - startTime;
                    updateTimer(elapsedTime);
                    handler.postDelayed(this, 100); // Update every 100 milliseconds
                }
            }
        });
    }

    private void updateTimer(long elapsedMillis) {
        int seconds = (int) (elapsedMillis / 1000);
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        int millis = (int) (elapsedMillis % 1000);
        String time = String.format("%d:%02d:%02d.%03d", hours, minutes, secs, millis);
        Log.e("TAG", "updateTimer: " + elapsedMillis);
        myView.mo(elapsedMillis);
        timeTextView.setText(time);
    }
}

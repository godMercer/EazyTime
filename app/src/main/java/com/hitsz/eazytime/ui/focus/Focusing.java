package com.hitsz.eazytime.ui.focus;

import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import java.util.Timer;
import java.util.TimerTask;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hitsz.eazytime.R;

public class Focusing extends AppCompatActivity implements View.OnClickListener {
    private int focustime, focussecond, focusmin, t=0;
    private Button startfocus, stopfocus,continucefocus;
    private EditText time;
    private TextView showtime;
    private Timer timer = null;
    private TimerTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focusing);
        getit();
    }

    private void getit() {
        time = findViewById(R.id.time);
        startfocus = findViewById(R.id.startfocus);
        stopfocus = findViewById(R.id.stopfocus);
        continucefocus=findViewById(R.id.continucefocus);
        showtime = findViewById(R.id.showtime);
        startfocus.setOnClickListener(this);
        stopfocus.setOnClickListener(this);
        continucefocus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startfocus:
                if(t==1)timer.cancel();
                t=1;
                focusmin = Integer.parseInt(time.getText().toString());
                focustime = focusmin * 60;
                StartFocus();
                break;
            case R.id.continucefocus:
                Conti();
                break;
            case R.id.stopfocus:
                StopFocus();
                break;
            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            showtime.setText(msg.arg1 + "");
            if(t==1) StartFocus();
        }

        ;
    };


    public void StartFocus() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                if (focustime > 0) {
                    focustime--;
                    Message message = mHandler.obtainMessage();
                    message.arg1 = focustime;
                    mHandler.sendMessage(message);
                }
            }
        };
        timer.schedule(task, 1000);
    }

    public void StopFocus(){
        focustime=0;
        t=0;
        Message message = mHandler.obtainMessage();
        message.arg1 = focustime;
        mHandler.sendMessage(message);
        timer.cancel();
    }

    public void Conti() {
        if (t == 1) {
            t = 0;
            timer.cancel();
        }
        else{
            t=1;
            StartFocus();
        }

    }

}
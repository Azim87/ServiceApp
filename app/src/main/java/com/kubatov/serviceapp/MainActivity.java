package com.kubatov.serviceapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    private EditText editText;
    private ProgressBar bar;
    private int count = 0;
    MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text);
        countPb();
    }

    public void countPb(){
        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);

        final Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                bar.setProgress(count);
                if (count == 100){
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 10, 100);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void onPlay(View view) {
        if (musicPlayer == null){
            musicPlayer = (MediaPlayer) MediaPlayer.create(this, R.raw.nana);
        }
        musicPlayer.start();
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopMusic();
            }
        });

        int i = 0;
        try {
            i = Integer.parseInt(editText.getText().toString().trim());
        }catch (NumberFormatException e ){
            i = 10;
        }

        intent = new Intent(this, MyIntentService.class);
        intent.putExtra("number", i);
        bar.setVisibility(View.VISIBLE);
        startService(intent);
    }

    public void onStop(View view) {
        if (musicPlayer !=null){
            musicPlayer.stop();
        }
    }

    public void onPause(View view) {
        stopMusic();
    }

    private void stopMusic(){
        if (musicPlayer !=null){
            musicPlayer.release();
            musicPlayer = null;

        }
        if (intent !=null) {
            bar.setVisibility(View.INVISIBLE);
            stopService(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMusic();
    }
}

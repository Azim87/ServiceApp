package com.kubatov.serviceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    private EditText editText;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text);
        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);
    }

    public void onClickStart(View view) {

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

    public void onClickStop(View view) {
        if (intent !=null) {
            bar.setVisibility(View.INVISIBLE);
            stopService(intent);
        }
    }
}

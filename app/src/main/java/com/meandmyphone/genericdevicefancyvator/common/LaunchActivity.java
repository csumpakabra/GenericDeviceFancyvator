package com.meandmyphone.genericdevicefancyvator.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.meandmyphone.genericdevicefancyvator.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        TextView text = findViewById(R.id.text);


    }
}
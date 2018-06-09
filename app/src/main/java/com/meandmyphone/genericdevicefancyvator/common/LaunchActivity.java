package com.meandmyphone.genericdevicefancyvator.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.meandmyphone.genericdevicefancyvator.R;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView text = findViewById(R.id.text);

        Parser parser = new Parser(this);

        Scene scene = parser.parse();


    }

}

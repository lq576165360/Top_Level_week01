package com.liuquan.liwushuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liuquan.liwushuo.MainActivity;
import com.liuquan.liwushuo.R;

/**
 * Created by PC on 2016/3/29.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

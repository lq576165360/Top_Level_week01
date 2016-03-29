package com.liuquan.liwushuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.bean.Hot2Info;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Hot2Activity extends AppCompatActivity {
    private String url;
    private String detail_html;
    @Bind(R.id.hot2_toolbar)
    RelativeLayout mToolBar;
    @Bind(R.id.hot2_scoroll)
    NestedScrollView mNestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot2);
        //初始化视图
        ButterKnife.bind(this);
       // mToolBar.setTitle("");
       // setSupportActionBar(mToolBar);
        //初始化数据
        downLoadData();
        mNestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float rawY = event.getRawY();
                LogTool.LOG_D("--------rawY-------" + rawY);
                float y = event.getY();
                LogTool.LOG_D("--------y-------" + y);
                return false;
            }
        });
        mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }

    private void downLoadData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        url = "http://api.liwushuo.com/v2/items/"+id;
        OkHttpTools.okGet(url, Hot2Info.class, new IOkCallback<Hot2Info>() {
            @Override
            public void onSuccess(Hot2Info resulte) {
                detail_html = resulte.getData().getDetail_html();

            }
        });
    }
}

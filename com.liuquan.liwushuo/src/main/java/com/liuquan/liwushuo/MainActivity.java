package com.liuquan.liwushuo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.liuquan.liwushuo.fragment.BaseFragment;
import com.liuquan.liwushuo.fragment.CategoryFragment;
import com.liuquan.liwushuo.fragment.HotFragment;
import com.liuquan.liwushuo.fragment.MainFragment;
import com.liuquan.liwushuo.fragment.MyFragment;
import com.liuquan.liwushuo.tools.LogTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_rg_bottom)
    RadioGroup menuRadioGroup;
    @Bind(R.id.main_rb_guide)
    RadioButton guideRadioButton;
    private FragmentManager supportFragmentManager;
    private int lastIndex;
    private List<BaseFragment> fragmentList;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportFragmentManager = getSupportFragmentManager();
        //ButterKnife绑定activity
        ButterKnife.bind(this);
        //初始化数据
        initData();
        //设置默认指南被选中
        guideRadioButton.setChecked(true);
        controlFragment(0,false);
        //设置监听
        initListenner();
        LogTool.LOG_D(this.getClass(), "main--------------");
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(MainFragment.newInstance());
        fragmentList.add(HotFragment.newInstance(null, null));
        fragmentList.add(CategoryFragment.newInstance(null, null));
        fragmentList.add(MyFragment.newInstance(null, null));
    }

    /**
     * 设置监听
     */
    private void initListenner() {
        menuRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.main_rb_guide:
                        LogTool.LOG_D(this.getClass(),"000000000000000");
                        controlFragment(0, true);
                        break;
                    case R.id.main_rb_hot:
                        controlFragment(1, true);
                        break;
                    case R.id.main_rb_category:
                        controlFragment(2, true);
                        break;
                    case R.id.main_rb_my:
                        controlFragment(3, true);
                        break;
                }
            }
        });
    }

    /**
     * 控制Fragment的各种状态
     *
     * @param index
     * @param isShow
     */
    public void controlFragment(int index, boolean isShow) {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        BaseFragment fragment = fragmentList.get(index);
        BaseFragment lastFragment = fragmentList.get(lastIndex);
        if (isShow) {
            fragmentTransaction.hide(lastFragment);
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.main_contain, fragment);
        }
        fragmentTransaction.commit();
        lastIndex = index;
    }

}

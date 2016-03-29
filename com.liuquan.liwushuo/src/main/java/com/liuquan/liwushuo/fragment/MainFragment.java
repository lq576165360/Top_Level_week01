package com.liuquan.liwushuo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.activity.SearchActivity;
import com.liuquan.liwushuo.bean.TabInfo;
import com.liuquan.liwushuo.fragment.viewpager.fragment.CommonFragment;
import com.liuquan.liwushuo.fragment.viewpager.fragment.HeartSelectFragment;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;
import com.liuquan.liwushuo.ui.FlowLayout;
import com.liuquan.liwushuo.urlinfo.URLInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;
    private MyViewPagerAdater myViewPagerAdater;
    private List<TabInfo.DataEntity.ChannelsEntity> channels;
    private Context mContext;
    private PopupWindow popupWindow;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Bind(R.id.main_fragment_menu_search)
    ImageView searchImageView;
    @Bind(R.id.main_fragment_menu_left)
    ImageView leftImageView;
    @Bind(R.id.main_fragment_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.main_fragment_viewpager)
    ViewPager viewPager;
    @Bind(R.id.main_fragment_pop_iv)
    ImageView arrow;
    private List<String> titleList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private boolean isOpenPop = false;
    private PopupWindow window;
    @Bind(R.id.fragment_main_toolbar)
    Toolbar mToolBar;
@Bind(R.id.main_fragment_contain)
    RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        //初始化数据
        initData();
        //设置Tablayout
        setTabLayout();
        //设置监听
        setListenner();
        return view;
    }

    private void setListenner() {
        searchImageView.setOnClickListener(this);
        leftImageView.setOnClickListener(this);
        arrow.setOnClickListener(this);
    }

    private void setTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initData() {
        OkHttpTools.okGet(URLInfo.TABLAYOUT_URL, TabInfo.class, new IOkCallback<TabInfo>() {


            @Override
            public void onSuccess(TabInfo resulte) {
                channels = resulte.getData().getChannels();
                for (int i = 0; i < channels.size(); i++) {
                    titleList.add(channels.get(i).getName());
                    idList.add(channels.get(i).getId());
                }
                LogTool.LOG_D("========IDlIST==========" + idList);
                fragments.add(HeartSelectFragment.newInstance(idList.get(0)));
                for (int i = 1; i < channels.size(); i++) {
                    fragments.add(CommonFragment.newInstance(idList.get(i)));
                }
                //myViewPagerAdater.notifyDataSetChanged();

                //为viewPager设置适配器
                myViewPagerAdater = new MyViewPagerAdater(getFragmentManager());
                viewPager.setAdapter(myViewPagerAdater);
                //tabLayout和viewPager关联
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setSelectedTabIndicatorColor(Color.RED);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_fragment_menu_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.main_fragment_menu_left:

                break;
            case R.id.main_fragment_pop_iv:
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                relativeLayout.setVisibility(View.GONE);
                popupWindow.showAsDropDown(mToolBar,0,0);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
                break;
        }
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = LayoutInflater.from(mContext).inflate(R.layout.pop, null,
                false);
        FlowLayout flowLayout = (FlowLayout)popupWindow_view.findViewById(R.id.pop_flowlayout);
        ImageView upImageView = (ImageView)popupWindow_view.findViewById(R.id.pop_imageview);
        for (int i =0;i<titleList.size();i++){
            TextView textView = new TextView(mContext);
            textView.setText(titleList.get(i));
            textView.setBackgroundResource(R.drawable.btn_bg_shape);
            flowLayout.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                        relativeLayout.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(finalI);
                    }
                }
            });
        }
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        // popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
//        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                    relativeLayout.setVisibility(View.VISIBLE);
//                }
//                return false;
//            }
//        });
        upImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    class MyViewPagerAdater extends FragmentPagerAdapter {

        public MyViewPagerAdater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments == null ? null : fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}

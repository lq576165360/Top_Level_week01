package com.liuquan.liwushuo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.bean.TabInfo;
import com.liuquan.liwushuo.fragment.viewpager.fragment.CommonFragment;
import com.liuquan.liwushuo.fragment.viewpager.fragment.HeartSelectFragment;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;
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
public class MainFragment extends BaseFragment {


    private OnFragmentInteractionListener mListener;
    private MyViewPagerAdater myViewPagerAdater;
    private List<TabInfo.DataEntity.ChannelsEntity> channels;
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

    @Bind(R.id.main_fragment_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.main_fragment_viewpager)
    ViewPager viewPager;
    private List<String> titleList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        //初始化数据
        initData();
        //设置Tablayout
        setTabLayout();
        return view;
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
                LogTool.LOG_D("========IDlIST=========="+idList);
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

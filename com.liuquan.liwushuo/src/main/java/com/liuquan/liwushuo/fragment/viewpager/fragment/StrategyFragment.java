package com.liuquan.liwushuo.fragment.viewpager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.activity.BannerActivity;
import com.liuquan.liwushuo.activity.Category2Activity;
import com.liuquan.liwushuo.bean.GroupInfo;
import com.liuquan.liwushuo.bean.SpecialInfo;
import com.liuquan.liwushuo.fragment.BaseFragment;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;
import com.liuquan.liwushuo.ui.MyGruidView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class StrategyFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int offset = 0;
    private String specislUrl = "http://api.liwushuo.com/v2/collections?limit=10&offset=" + offset;
    private String allUrl = "http://api.liwushuo.com/v2/channel_groups/all";
    private Context mContext;
    private View view;
    private RecyclerView mRecycleView;
    private List<SpecialInfo.DataEntity.CollectionsEntity> collections;
    private MyAdapter recycleAdapter;
    private List<GroupInfo.DataEntity.ChannelGroupsEntity> channel_groups;
    private MyExListViewAdapter myExListViewAdapter;
    private Map<List<GroupInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity>, MyGruidViewAdapter> adapterMap
            = new HashMap<>();

    public StrategyFragment() {
        // Required empty public constructor
    }

    public static StrategyFragment newInstance(String param1, String param2) {
        StrategyFragment fragment = new StrategyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext = getActivity();
    }

    @Bind(R.id.strategy_expndablelistview)
    ExpandableListView mExpandableListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategy, container, false);
        //初始化view
        ButterKnife.bind(this, view);
        //初始化数据
        downLoadData();
        //设置头部视图
        setUpHeardView();
        //设置ExpandListView
        setUpExpandlistView();
        //设置监听
        initListenner();
        myExListViewAdapter.notifyDataSetChanged();
        return view;
    }

    private void initListenner() {

    }

    private void setUpExpandlistView() {
        //创建适配器
        myExListViewAdapter = new MyExListViewAdapter();
        mExpandableListView.setAdapter(myExListViewAdapter);
        //设置点击事件使其不能收回展开
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    private void downLoadData() {
        getExpendListViewData();

        getHaerdViewData();

    }
    /**
     * 获得头部数据
     */
    private void getHaerdViewData() {
        OkHttpTools.okGet(specislUrl, SpecialInfo.class, new IOkCallback<SpecialInfo>() {
            @Override
            public void onSuccess(SpecialInfo resulte) {
                collections = resulte.getData().getCollections();
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }
    /**
     * 获得ExpandListView数据
     */
    private void getExpendListViewData() {
        OkHttpTools.okGet(allUrl, GroupInfo.class, new IOkCallback<GroupInfo>() {
            @Override
            public void onSuccess(GroupInfo resulte) {
                channel_groups = resulte.getData().getChannel_groups();

                for (int i = 0; i < channel_groups.size(); i++) {
                    mExpandableListView.expandGroup(i);
                }
                myExListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置头部视图：专题RecycleView
     */
    private void setUpHeardView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.strategy_lv_heard, null);
        mRecycleView = (RecyclerView) view.findViewById(R.id.category_recycleview_zhuanti);
        //创建RecycleView适配器
        recycleAdapter = new MyAdapter();
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecycleView.setAdapter(recycleAdapter);
        mExpandableListView.addHeaderView(view);
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

    /**
     * RecycleView适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_recycleview, null);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String banner_image_url = collections.get(position).getBanner_image_url();
            Picasso.with(mContext).load(banner_image_url).into(holder.iconImageView);
        }

        @Override
        public int getItemCount() {
            return collections == null ? 0 : collections.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.hcategory_recy_item_iv)
            ImageView iconImageView;

            public ViewHolder(final View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(mContext, "位置" + getPosition(), Toast.LENGTH_SHORT).show();
                        int id = collections.get(getPosition()).getId();
                        LogTool.LOG_D("-------id---" + id);
                        Intent intent = new Intent(mContext, BannerActivity.class);
                        intent.putExtra("id", id);
                        LogTool.LOG_D("-----------target_id-----" + id);
                        startActivity(intent);
                    }
                });
            }
        }

    }

    /**
     * ExpandableListView适配器
     */
    class MyExListViewAdapter extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return channel_groups == null ? 0 : channel_groups.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return 1;
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            GroupViewHoudle groupViewHoudle = null;
            if (view != null) {
                groupViewHoudle = (GroupViewHoudle) view.getTag();
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_strategy_exlistview_group, null);
                groupViewHoudle = new GroupViewHoudle(view);
                view.setTag(groupViewHoudle);
            }
            groupViewHoudle.titelTextView.setText(channel_groups.get(i).getName());
            return view;
        }

        class GroupViewHoudle {
            @Bind(R.id.strategy_expndablelistview_group_tv)
            TextView titelTextView;

            public GroupViewHoudle(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            final List<GroupInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity> channels = channel_groups.get(i).getChannels();
            ChildViewHoudle childViewHoudle = null;
            if (view != null) {
                childViewHoudle = (ChildViewHoudle) view.getTag();
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_strategy_exlistview_child, viewGroup, false);
                childViewHoudle = new ChildViewHoudle(view);
                view.setTag(childViewHoudle);
            }

            //gruidViewAdapter.notifyDataSetChanged();
            //创建适配
            MyGruidViewAdapter gruidViewAdapter = adapterMap.get(channels);
            if (gruidViewAdapter == null) {
                gruidViewAdapter = new MyGruidViewAdapter(channels);
                adapterMap.put(channels, gruidViewAdapter);
            }
            childViewHoudle.myGruidView.setAdapter(gruidViewAdapter);
/**
 * 为gruideView设置监听  注意：这个监听如果写在houdler类里面会存在item复用问题
 *                             如第一个item回收最后给第第四个item复用，在这里给第四个item的图片和标题
 *                             赋值了，显示无偏差，但是仅仅只是图片和标题无偏差，实质里层数据还是第一个
 *                             item的数据
 */
            childViewHoudle.myGruidView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int id = channels.get(i).getId();
                    String name = channels.get(i).getName();
                    LogTool.LOG_D("---------gruidView---------" + id);
                    Intent intent = new Intent(mContext, Category2Activity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });
            return view;
        }

        class ChildViewHoudle {
            @Bind(R.id.strategy_expndablelistview_child_gruidview)
            MyGruidView myGruidView;

            public ChildViewHoudle(View view) {
                ButterKnife.bind(this, view);
            }

        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

    class MyGruidViewAdapter extends BaseAdapter {
        private List<GroupInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity> channels;

        public MyGruidViewAdapter(List<GroupInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity> channels) {
            this.channels = channels;
        }

        @Override
        public int getCount() {

            return channels == null ? 0 : channels.size();
        }

        @Override
        public Object getItem(int i) {
            return channels == null ? null : channels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            GruidViewHoulder gruidViewHoulder = null;
            if (view != null) {
                gruidViewHoulder = (GruidViewHoulder) view.getTag();
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_strategy_child_layout, null);
                gruidViewHoulder = new GruidViewHoulder(view);
                view.setTag(gruidViewHoulder);
            }
            gruidViewHoulder.textView.setText(channels.get(i).getName());
            gruidViewHoulder.imageView.setImageResource(R.drawable.defualt);
            Picasso.with(mContext).load(channels.get(i).getIcon_url()).into(gruidViewHoulder.imageView);
            return view;
        }
    }

    class GruidViewHoulder {
        @Bind(R.id.strategy_expndablelistview_child_gruidview_iv)
        RoundedImageView imageView;
        @Bind(R.id.strategy_expndablelistview_child_gruidview_tv)
        TextView textView;

        public GruidViewHoulder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}

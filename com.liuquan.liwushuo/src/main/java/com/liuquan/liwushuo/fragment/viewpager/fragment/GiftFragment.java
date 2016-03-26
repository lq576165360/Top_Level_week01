package com.liuquan.liwushuo.fragment.viewpager.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.activity.Gift2Activity;
import com.liuquan.liwushuo.bean.GiftInfo;
import com.liuquan.liwushuo.fragment.BaseFragment;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;
import com.liuquan.liwushuo.ui.MyGruidView;
import com.liuquan.liwushuo.urlinfo.URLInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GiftFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private ImageView lineImageView;
    private OnFragmentInteractionListener mListener;
    private List<GiftInfo.DataEntity.CategoriesEntity> categories;
    private MyListViewAdapter listViewAdapter;
    private List<ImageView> lineImages = new ArrayList<>();
    private List<GiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity> subcategories = new ArrayList<>();
    private Map<List<GiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity>, MyGruidViewAdapter> adapterMap
            = new HashMap<>();
    private MyExpenAdapter myExListViewAdapter;
    private int index = -1;

    public GiftFragment() {
        // Required empty public constructor
    }


    public static GiftFragment newInstance(String param1, String param2) {
        GiftFragment fragment = new GiftFragment();
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

    @Bind(R.id.gift_fragment_expandlistview)
    ExpandableListView mExpandableListView;
    @Bind(R.id.gift_fragment_listview)
    ListView mListView;
    @Bind(R.id.gift_fragment_toptv)
    TextView mTopTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        //初始化视图
        ButterKnife.bind(this, view);
        //下载数据
        downLoadData();
        //设置视图
        setUpListView();
        setUpExpanListView();
        //设置监听
        setListenner();

        return view;
    }


    private void setListenner() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mExpandableListView.setSelectedGroup(i);
                index = i;
                listViewAdapter.notifyDataSetChanged();
            }
        });
        mExpandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int num = i==0?0:i/2;
                LogTool.LOG_D("-------------------"+num);
            }
        });
    }


    private void setUpExpanListView() {
        //创建适配器
        myExListViewAdapter = new MyExpenAdapter();
        mExpandableListView.setAdapter(myExListViewAdapter);
        //设置点击事件使其不能收回展开
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }


    private void setUpListView() {
        //设置适配器
        listViewAdapter = new MyListViewAdapter();
        mListView.setAdapter(listViewAdapter);

    }

    private void downLoadData() {
        OkHttpTools.okGet(URLInfo.GIFT_URL, GiftInfo.class, new IOkCallback<GiftInfo>() {


            @Override
            public void onSuccess(GiftInfo resulte) {
                categories = resulte.getData().getCategories();
                listViewAdapter.notifyDataSetChanged();
                for (int i = 0; i < categories.size(); i++) {
                    mExpandableListView.expandGroup(i);
                }
                myExListViewAdapter.notifyDataSetChanged();

            }
        });
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

    class MyExpenAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return categories == null ? 0 : categories.size();
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
            GroupViewHoulder groupViewHoulder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_gift_exlistview_group, null);
                groupViewHoulder = new GroupViewHoulder(view);
                view.setTag(groupViewHoulder);
            } else {
                groupViewHoulder = (GroupViewHoulder) view.getTag();
            }

            groupViewHoulder.titleTextView.setText(categories.get(i).getName());

            return view;
        }

        class GroupViewHoulder {
            @Bind(R.id.gift_expndablelistview_group_tv)
            TextView titleTextView;

            public GroupViewHoulder(View view) {
                ButterKnife.bind(this, view);

            }

        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            List<GiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity> subcategories = categories.get(i).getSubcategories();
            ChildViewHoudle childViewHoudle = null;
            if (view != null) {
                childViewHoudle = (ChildViewHoudle) view.getTag();
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_strategy_exlistview_child, viewGroup, false);
                childViewHoudle = new ChildViewHoudle(view,subcategories);
                view.setTag(childViewHoudle);
            }

            //gruidViewAdapter.notifyDataSetChanged();
            //创建适配
            MyGruidViewAdapter gruidViewAdapter = adapterMap.get(subcategories);
            if (gruidViewAdapter == null) {
                gruidViewAdapter = new MyGruidViewAdapter(subcategories);
                adapterMap.put(subcategories, gruidViewAdapter);
            }
            childViewHoudle.myGruidView.setNumColumns(3);
            childViewHoudle.myGruidView.setAdapter(gruidViewAdapter);
            return view;
        }

        class ChildViewHoudle {
            @Bind(R.id.strategy_expndablelistview_child_gruidview)
            MyGruidView myGruidView;

            public ChildViewHoudle(View view, final List<GiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity> subcategories) {
                ButterKnife.bind(this, view);
                myGruidView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int id = subcategories.get(i).getId();
                        String name = subcategories.get(i).getName();
                        LogTool.LOG_D("---------gruidView---------" + id);
                        Intent intent = new Intent(mContext, Gift2Activity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });
            }
        }


        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

    class MyGruidViewAdapter extends BaseAdapter {
        private List<GiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity> subcategories;

        public MyGruidViewAdapter(List<GiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity> subcategories) {
            this.subcategories = subcategories;
        }

        @Override
        public int getCount() {

            return subcategories == null ? 0 : subcategories.size();
        }

        @Override
        public Object getItem(int i) {
            return subcategories == null ? null : subcategories.get(i);
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
            gruidViewHoulder.textView.setTextSize(13);
            gruidViewHoulder.textView.setText(subcategories.get(i).getName());
            gruidViewHoulder.imageView.setImageResource(R.drawable.defualt);
            Picasso.with(mContext).load(subcategories.get(i).getIcon_url()).into(gruidViewHoulder.imageView);

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

    class MyListViewAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return categories == null ? 0 : categories.size();
        }

        @Override
        public Object getItem(int i) {
            return categories == null ? null : categories.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListViewItemHoulder listViewItemHoulder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_gift_listview, null);
                listViewItemHoulder = new ListViewItemHoulder(view);
                view.setTag(listViewItemHoulder);
            } else {
                listViewItemHoulder = (ListViewItemHoulder) view.getTag();
            }
            listViewItemHoulder.cateTextView.setText(categories.get(i).getName());
            listViewItemHoulder.cateTextView.setSelected(false);
            view.setBackgroundColor(Color.GRAY);
            //listViewItemHoulder.cateTextView.setBackgroundColor(Color.GRAY);
            listViewItemHoulder.lineImageView.setSelected(false);
            if (index == i) {
                listViewItemHoulder.cateTextView.setSelected(true);
                //listViewItemHoulder.cateTextView.setBackgroundColor(Color.WHITE);
                view.setBackgroundColor(Color.WHITE);
                listViewItemHoulder.lineImageView.setSelected(true);
            }

            return view;
        }
    }

    class ListViewItemHoulder {
        @Bind(R.id.gift_fragment_listview_tv)
        TextView cateTextView;
        @Bind(R.id.gift_fragment_listview_iv)
        ImageView lineImageView;

        public ListViewItemHoulder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

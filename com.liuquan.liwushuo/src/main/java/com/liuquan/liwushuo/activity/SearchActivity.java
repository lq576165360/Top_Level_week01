package com.liuquan.liwushuo.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.bean.KeyWordsInfo;
import com.liuquan.liwushuo.bean.SearchGiftInfo;
import com.liuquan.liwushuo.bean.SearchStategyInfo;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.DecimalFormatTool;
import com.liuquan.liwushuo.tools.LogTool;
import com.liuquan.liwushuo.ui.FlowLayout;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private static int strategyOffset;
    private static int giftOffset;
    public static final String URL_GIFT_HEARD = "http://api.liwushuo.com/v2/search/item?limit=20&offset=" + giftOffset + "&sort=&keyword=";
    public static final String URL_STRATRGY_GHEARD = "http://api.liwushuo.com/v2/search/post?limit=20&offset=" + strategyOffset + "&sort=&keyword=";

    public static final String HOTWORD_URL = "http://api.liwushuo.com/v2/search/hot_words";
    private List<String> hot_words;
    private List<String> titles = new ArrayList<>();
    private List<SearchGiftInfo.DataEntity.ItemsEntity> items = new ArrayList<>();
    private List<SearchStategyInfo.DataEntity.PostsEntity> posts = new ArrayList<>();
    private Context mContext;
    @Bind(R.id.search_fowlayout)
    FlowLayout mFlowLayout;
    @Bind(R.id.search_menu_back)
    ImageView backImageView;
    @Bind(R.id.search_menu_seach_et)
    EditText searchEditText;
    @Bind(R.id.search_menu_seach_tv)
    TextView searchTextView;
    @Bind(R.id.search_Linearlayout1)
    LinearLayout mShearchLinearLayout;
    @Bind(R.id.search_Linearlayout2)
    LinearLayout mShowLinearLayout;
    @Bind(R.id.search_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.search_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.search_menu_sort)
    ImageView sortImageView;
    private List<View> viewList = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;

    private GridView gridView;
    private PullToRefreshListView listView;
    private MyGruidViewAdapter myGruidViewAdapter;
    private MyListViewAdapter myListViewAdapter;
    private PullToRefreshGridView pullGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = getApplication();
        //初始化视图
        ButterKnife.bind(this);
        //下载数据
        downLoadKeyWordsData();
        //设置TabLayout和ViewPager关联
        setTabLayOut();
        //设置监听
        setListenner();
    }

    private void setListenner() {
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = searchEditText.getEditableText().toString();
                if (content == null||content.length()==0) {
                    Toast.makeText(SearchActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    initViewPagerData(content);
                    items.clear();
                    posts.clear();
                }
            }
        });
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchEditText.setCursorVisible(true);
                searchTextView.setVisibility(View.VISIBLE);
                sortImageView.setVisibility(View.GONE);
                searchEditText.setText("");

                return false;
            }
        });
    }

    private void setTabLayOut() {
        titles.add("礼物");
        titles.add("攻略");
        pullGridView = new PullToRefreshGridView(mContext);
        myGruidViewAdapter = new MyGruidViewAdapter();
        gridView = pullGridView.getRefreshableView();
        gridView.setNumColumns(2);
        pullGridView.setAdapter(myGruidViewAdapter);

        viewList.add(gridView);

        listView = new PullToRefreshListView(mContext);
        myListViewAdapter = new MyListViewAdapter();
        listView.setAdapter(myListViewAdapter);

        viewList.add(listView);

        myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(myViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);

    }

    private void downLoadKeyWordsData() {


        OkHttpTools.okGet(HOTWORD_URL, KeyWordsInfo.class, new IOkCallback<KeyWordsInfo>() {


            @Override
            public void onSuccess(KeyWordsInfo resulte) {
                hot_words = resulte.getData().getHot_words();
                setOnView();
            }
        });
    }

    private void setOnView() {
        for (int i = 0; i < hot_words.size(); i++) {

           TextView button = new TextView(mContext);
            button.setText(hot_words.get(i));
            button.setTextColor(Color.BLACK);
            button.setTextSize(15);
            button.setBackgroundResource(R.drawable.btn_bg_shape);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView btn = (TextView) v;
                    String word = btn.getText().toString();
                    searchEditText.setText(word);
                    initViewPagerData(word);

                }
            });
            mFlowLayout.addView(button);
        }
    }

    private void initViewPagerData(String str) {
        String encode = null;
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        getGruidViewData(encode);

        getListViewData(encode);
        mShearchLinearLayout.setVisibility(View.GONE);
        mShowLinearLayout.setVisibility(View.VISIBLE);
        searchTextView.setVisibility(View.GONE);
        sortImageView.setVisibility(View.VISIBLE);
        searchEditText.setCursorVisible(false);

    }

    private void getGruidViewData(String encode) {
        String gift_search_url = URL_GIFT_HEARD + encode;
        OkHttpTools.okGet(gift_search_url, SearchGiftInfo.class, new IOkCallback<SearchGiftInfo>() {


            @Override
            public void onSuccess(SearchGiftInfo resulte) {
                if (resulte.getData() != null) {
                    LogTool.LOG_D("有内容-----进入");
                    items.addAll(resulte.getData().getItems());
                    myGruidViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SearchActivity.this, "没有相关的商品啦~~~", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getListViewData(String encode) {
        String strategy_url = URL_STRATRGY_GHEARD + encode;
        OkHttpTools.okGet(strategy_url, SearchStategyInfo.class, new IOkCallback<SearchStategyInfo>() {


            @Override
            public void onSuccess(SearchStategyInfo resulte) {
                if (resulte.getData() != null) {
                    posts.addAll(resulte.getData().getPosts());
                    myListViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SearchActivity.this, "没有相关的商品啦~~~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return posts == null ? 0 : posts.size();
        }

        @Override
        public Object getItem(int i) {
            return posts == null ? null : posts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RecycleViewHoudle houdle = null;
            if (view != null) {
                houdle = (RecycleViewHoudle) view.getTag();
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.expendlistview_child_item, null);
                houdle = new RecycleViewHoudle(view);
                view.setTag(houdle);
            }
            String title = posts.get(i).getTitle();
            String cover_image_url = posts.get(i).getCover_image_url();
            int likes_count = posts.get(i).getLikes_count();
            Picasso.with(mContext).load(cover_image_url).into(houdle.recycleImageView);
            houdle.titleTextView.setText(title);
            houdle.likeTextView.setText(likes_count + "");
            return view;
        }

        class RecycleViewHoudle {
            @Bind(R.id.heart_selecte_lv_child_iv)
            ImageView recycleImageView;
            @Bind(R.id.heart_selecte_lv_child_tv)
            TextView titleTextView;
            @Bind(R.id.heart_selecte_lv_child_liketv)
            TextView likeTextView;

            public RecycleViewHoudle(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class MyGruidViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public Object getItem(int i) {
            return items == null ? null : items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_hot_recycleview, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            SearchGiftInfo.DataEntity.ItemsEntity itemsEntity = items.get(i);
            String imagePath = itemsEntity.getCover_image_url();
            String name = itemsEntity.getName();
            String price = itemsEntity.getPrice();
            String likeCount = DecimalFormatTool.format((float) itemsEntity.getFavorites_count() / 1000);
            viewHolder.nameTextView.setText(name);
            viewHolder.likeTextView.setText(likeCount + "K");
            viewHolder.priceTextView.setText(price);
            Picasso.with(mContext).load(imagePath).into(viewHolder.iconImageView);
            return view;
        }

        class ViewHolder {
            @Bind(R.id.hot_iv)
            ImageView iconImageView;
            @Bind(R.id.hot_name_tv)
            TextView nameTextView;
            @Bind(R.id.hot_price_tv)
            TextView priceTextView;
            @Bind(R.id.hot_like_tv)
            TextView likeTextView;

            public ViewHolder(View itemView) {
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return viewList == null ? 0 : viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            if (viewList != null) {
                view = viewList.get(position);
                container.addView(view);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (viewList != null) {
                View view = viewList.get(position);
                container.removeView(view);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}


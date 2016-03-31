package com.liuquan.liwushuo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

public class SearchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static int strategyOffset;
    private static int giftOffset;
    private static String sort = "";
    public static String URL_GIFT_HEARD = "http://api.liwushuo.com/v2/search/item?limit=20&offset=" + giftOffset + "&sort=" + sort + "&keyword=";
    public static  String URL_STRATRGY_GHEARD = "http://api.liwushuo.com/v2/search/post?limit=20&offset=" + strategyOffset + "&sort=" + sort + "&keyword=";

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
    @Bind(R.id.search_toptv)
    TextView selectGift;
    private List<View> viewList = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;

    private GridView gridView;
    private PullToRefreshListView listView;
    private MyGruidViewAdapter myGruidViewAdapter;
    private MyListViewAdapter myListViewAdapter;
    private PullToRefreshGridView pullGridView;
    private PopupWindow popupWindow;
    private CheckBox morenCheckBox;
    private CheckBox hotCheckBox;
    private CheckBox price1CheckBox;
    private CheckBox price2CheckBox;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private LinearLayout morenLayaout;
    private LinearLayout hotLayaout;
    private LinearLayout price1Layaout;
    private LinearLayout price2Layaout;

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
                if (content == null || content.length() == 0) {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id1 = posts.get(position - 1).getId();
                LogTool.LOG_D("---------listView--------" + id1);
                String cover_image_url = posts.get(position - 1).getCover_image_url();
                String title = posts.get(position - 1).getTitle();
                Intent intent = new Intent(mContext, DetailActivity.class);
//                intent.putExtra("url", url);
                //TODO
                intent.putExtra("image_url", cover_image_url);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id1 = items.get(position).getId();
                LogTool.LOG_D("---------gridView--------" + id1);
                Intent intent = new Intent(mContext, Hot2Activity.class);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });

        sortImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow.showAsDropDown(v, 0, 0);
                popupWindow.showAtLocation(v, Gravity.LEFT, 0, 10);
            }
        });

        selectGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelecteGiftActivity.class);
                startActivity(intent);
            }
        });
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
        View popupWindow_view = LayoutInflater.from(mContext).inflate(R.layout.sort_pop, null,
                false);
        morenCheckBox = (CheckBox) popupWindow_view.findViewById(R.id.sort_moren);
        hotCheckBox = (CheckBox) popupWindow_view.findViewById(R.id.sort_hot);
        price1CheckBox = (CheckBox) popupWindow_view.findViewById(R.id.sort_price1);
        price2CheckBox = (CheckBox) popupWindow_view.findViewById(R.id.sort_price2);

        morenLayaout = (LinearLayout) popupWindow_view.findViewById(R.id.sort_layout_moren);
        hotLayaout = (LinearLayout) popupWindow_view.findViewById(R.id.sort_layout_hot);
        price1Layaout = (LinearLayout) popupWindow_view.findViewById(R.id.sort_layout_price1);
        price2Layaout = (LinearLayout) popupWindow_view.findViewById(R.id.sort_layout_price2);
        checkBoxes.add(morenCheckBox);
        checkBoxes.add(hotCheckBox);
        checkBoxes.add(price1CheckBox);
        checkBoxes.add(price2CheckBox);
        morenCheckBox.setChecked(true);
        morenLayaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morenCheckBox.setChecked(true);
            }
        });
        hotLayaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotCheckBox.setChecked(true);
            }
        });
        price1Layaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price1CheckBox.setChecked(true);
            }
        });
        price2Layaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price2CheckBox.setChecked(true);
            }
        });
        morenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controlCheckBox();
                    morenCheckBox.setChecked(true);
                    String content = searchEditText.getEditableText().toString();
                    sort = "";
                    URL_GIFT_HEARD = "http://api.liwushuo.com/v2/search/item?limit=20&offset="
                     + giftOffset + "&sort=" + sort + "&keyword=";
                    URL_STRATRGY_GHEARD = "http://api.liwushuo.com/v2/search/post?limit=20&offset="
                            + strategyOffset + "&sort=" + sort + "&keyword=";
                    initViewPagerData(content);
                    items.clear();
                    posts.clear();
                    LogTool.LOG_D("----------------默认排序-----");
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
        hotCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controlCheckBox();
                    hotCheckBox.setChecked(true);
                    String content = searchEditText.getEditableText().toString();
                    sort = "hot";
                    URL_GIFT_HEARD = "http://api.liwushuo.com/v2/search/item?limit=20&offset="
                            + giftOffset + "&sort=" + sort + "&keyword=";
                    URL_STRATRGY_GHEARD = "http://api.liwushuo.com/v2/search/post?limit=20&offset="
                            + strategyOffset + "&sort=" + sort + "&keyword=";
                    initViewPagerData(content);
                    items.clear();
                    posts.clear();
                    LogTool.LOG_D("----------------热门排序-----");
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
        price1CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controlCheckBox();
                    price1CheckBox.setChecked(true);
                    LogTool.LOG_D("----------------价格1排序-----");

                    String content = searchEditText.getEditableText().toString();
                    sort = "price";
                    URL_GIFT_HEARD = "http://api.liwushuo.com/v2/search/item?limit=20&offset="
                            + giftOffset + "&sort=" + sort + "&keyword=";
                    URL_STRATRGY_GHEARD = "http://api.liwushuo.com/v2/search/post?limit=20&offset="
                            + strategyOffset + "&sort=" + sort + "&keyword=";
                    initViewPagerData(content);
                    items.clear();
                    posts.clear();
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
        price2CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controlCheckBox();
                    price2CheckBox.setChecked(true);
                    LogTool.LOG_D("----------------价格2排序-----");
                    String content = searchEditText.getEditableText().toString();
                    sort = "price%3Adesc";
                    URL_GIFT_HEARD = "http://api.liwushuo.com/v2/search/item?limit=20&offset="
                            + giftOffset + "&sort=" + sort + "&keyword=";
                    URL_STRATRGY_GHEARD = "http://api.liwushuo.com/v2/search/post?limit=20&offset="
                            + strategyOffset + "&sort=" + sort + "&keyword=";
                    items.clear();
                    posts.clear();
                    initViewPagerData(content);
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 300, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        // popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
//                        popupWindow = null;
                }
                return false;
            }
        });

    }

    private void controlCheckBox() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setChecked(false);
        }
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
        LogTool.LOG_D("-----------url-------"+gift_search_url);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sort_moren:
                LogTool.LOG_D("----------------默认排序-----");
                break;
            case R.id.sort_hot:
                LogTool.LOG_D("----------------热门排序-----");
                break;
            case R.id.sort_price1:
                LogTool.LOG_D("----------------价格由低到高排序-----");
                break;
            case R.id.sort_price2:
                LogTool.LOG_D("----------------价格2排序-----");
                break;
        }

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


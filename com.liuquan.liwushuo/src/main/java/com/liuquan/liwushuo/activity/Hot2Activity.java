package com.liuquan.liwushuo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.bean.CommentInfo;
import com.liuquan.liwushuo.bean.Hot2Info;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class Hot2Activity extends AppCompatActivity implements View.OnClickListener{
    private String url;
    private String detail_html;
    @Bind(R.id.hot2_toolbar)
    //RelativeLayout mLayoutToolBar;
            Toolbar toolbar;
    @Bind(R.id.hot2_scoroll)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.hot2_rg)
    RadioGroup mRadioGroup;
    @Bind(R.id.hot2_rb_comment)
    RadioButton commentRadioButton;
    @Bind(R.id.hot2_rb_detail)
    RadioButton detailRadioButton;
    @Bind(R.id.hot2_banner)
    ConvenientBanner banner;
    @Bind(R.id.hot2_menu_back)
    ImageView backImageView;
    @Bind(R.id.hot2_menu_share)
    ImageView shareImageView;
    @Bind(R.id.hot2_title_tv)
    TextView titleTextView;
    @Bind(R.id.hot2_title_des)
    TextView desTextView;
    @Bind(R.id.hot2_title_price)
    TextView priceTextView;
    @Bind(R.id.hot2_viewpager)
    ViewPager mViewPager;
    private Context mContext;
    private List<View> views = new ArrayList<>();
    private String comment_url;
    private List<CommentInfo.DataEntity.CommentsEntity> comments;
    private int offset;
    private MyListViewAdapter listViewAdapter;
    private MyViewPagerAdapter myViewPagerAdapter;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot2);
        //初始化视图
        ButterKnife.bind(this);
        mContext = getApplication();
        // setSupportActionBar(mToolBar);
        //设置viewPager视图
        setUpViewPager();
        //初始化数据
        downLoadData();
        //设置监听
        setListenner();

        detailRadioButton.setChecked(true);
    }

    private void setUpViewPager() {
        webView = new WebView(mContext);
        views.add(webView);

        ListView listView = new ListView(mContext);
        //创建ListView的适配器
        listViewAdapter = new MyListViewAdapter();

        listView.setAdapter(listViewAdapter);
        views.add(listView);

        myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(myViewPagerAdapter);
    }

    private void setListenner() {
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 80) {
                    toolbar.setBackgroundColor(Color.RED);
                    toolbar.setAlpha(scrollY / 500f);
                } else {
                   toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
                }
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hot2_rb_detail:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.hot2_rb_comment:
                        mViewPager.setCurrentItem(1);
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        detailRadioButton.setChecked(true);
                        break;
                    case 1:
                        commentRadioButton.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        shareImageView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
    }

    private void downLoadData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        url = "http://api.liwushuo.com/v2/items/" + id;
        comment_url = "http://api.liwushuo.com/v2/items/" + id + "/comments?limit=20&offset=" + offset;
        getWebViewData();

        getCommentData();
    }

    private void getWebViewData() {
        OkHttpTools.okGet(url, Hot2Info.class, new IOkCallback<Hot2Info>() {
            @Override
            public void onSuccess(Hot2Info resulte) {
                LogTool.LOG_D("---------url------"+url);
                if (resulte.getData() != null) {
                    Hot2Info.DataEntity data = resulte.getData();
                    titleTextView.setText(data.getName());
                    desTextView.setText(data.getDescription());
                    priceTextView.setText("¥" + data.getPrice());
                    List<String> image_urls = data.getImage_urls();
                    setBanner(image_urls);
                    commentRadioButton.setText("评论" + "(" + data.getComments_count() + ")");
//                    webView.loadData(data.getDetail_html(), "text/html", "utf-8");
                        webView.loadDataWithBaseURL(null,data.getDetail_html(),"text/html","utf-8",null);
                } else {
                    Toast.makeText(Hot2Activity.this, "没有详情啦~~~~~~", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void getCommentData() {
        OkHttpTools.okGet(comment_url, CommentInfo.class, new IOkCallback<CommentInfo>() {


            @Override
            public void onSuccess(CommentInfo resulte) {
                if (resulte.getData() != null) {
                    comments = resulte.getData().getComments();
                    listViewAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(Hot2Activity.this, "没有详情啦~~~~~~", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }


    private void setBanner(List<String> image_urls) {
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, image_urls)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hot2_menu_back:
                finish();
                break;
            case R.id.hot2_menu_share:
                ShareSDK.initSDK(mContext);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//                oks.setTitle(getString(R.string.share));
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

                // 启动分享GUI
                oks.show(mContext);
                break;

        }
    }


    class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;


        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            //imageView.setImageResource(data);
            Picasso.with(context).load(data).into(imageView);
        }
    }

    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return comments == null ? 0 : comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments == null ? null : comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoulder viewHoulder = null;
            if (convertView != null) {
                viewHoulder = (ViewHoulder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hot2_comment, null);
                viewHoulder = new ViewHoulder(convertView);
                convertView.setTag(viewHoulder);
            }
            viewHoulder.nameTextView.setText(comments.get(position).getUser().getNickname());
            viewHoulder.desTextView.setText(comments.get(position).getContent());
            if (comments.get(position).getUser().getAvatar_url() != null&&comments.get(position).getUser().getAvatar_url().length()!=0) {
                Picasso.with(mContext).load(comments.get(position).getUser().getAvatar_url()).into(viewHoulder.userIconImageView);
            }else{
                viewHoulder.userIconImageView.setImageResource(R.drawable.abc2);
            }
            return convertView;
        }
    }

    class ViewHoulder {
        @Bind(R.id.hot2_comment_user_icon)
        ImageView userIconImageView;
        @Bind(R.id.hot2_comment_des_tv)
        TextView desTextView;
        @Bind(R.id.hot2_comment_username_tv)
        TextView nameTextView;

        public ViewHoulder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            if (views != null) {
                view = views.get(position);
                container.addView(view);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (views != null) {
                View view = views.get(position);
                container.removeView(view);
            }
        }
    }
}

package com.liuquan.liwushuo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.bean.ProductInfo;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.LogTool;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Category2Activity extends AppCompatActivity {
    @Bind(R.id.category2_listview)
    PullToRefreshListView mListView;
    @Bind(R.id.category2_back)
    ImageView backImageView;
    @Bind(R.id.category2_menu_title)
    TextView menuTextView;
    @Bind(R.id.category2_menu_sort)
    ImageView sortImageView;
    private List<ProductInfo.DataEntity.ItemsEntity> items= new ArrayList<>();
    private Context mcontext;
    private MyListViewAdapter myAdapter;
    private String menuTitle;
    private String url;
    private int id;
    private int offset;
    private String name;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);
        mcontext = getApplication();
        //初始视图
        ButterKnife.bind(this);
        //获取数据
        downLoadData();
        //设置视图
        setUpView();
        //设置监听
        setUpListenner();
        sortImageView.setVisibility(View.GONE);
    }
    private void setUpListenner() {
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListView.onRefreshComplete();

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogTool.LOG_D("-----------位置--------" + i);
                int i1 = i - 1;
                String title = items.get(i1).getTitle();
                String cover_image_url = items.get(i1).getCover_image_url();
                String content_url = items.get(i1).getContent_url();
                Intent intent = new Intent(mcontext, DetailActivity.class);
                intent.putExtra("url", content_url);
                intent.putExtra("image_url", cover_image_url);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sortImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //TODO
            }
        });

    }

    private void setUpView() {
        //创建适配器
        myAdapter = new MyListViewAdapter();
        mListView.setAdapter(myAdapter);

    }

    private void downLoadData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        menuTextView.setText(name);
        if (id==0){
            Toast.makeText(mcontext, "没有数据啦~~~~", Toast.LENGTH_SHORT).show();
            finish();
        }else{

           // url = "http://api.liwushuo.com/v2/collections/" + id + "/items?limit=20&offset=" + offset;
            url = "http://api.liwushuo.com/v2/channels/"+id+"/items?limit=20&gender=1&offset="+offset+"&generation=2&order_by=now";
            LogTool.LOG_D("------url---------------" + url);
            OkHttpTools.okGet(url + offset, ProductInfo.class, new IOkCallback<ProductInfo>() {



                @Override
                public void onSuccess(ProductInfo resulte) {
                    items.addAll(resulte.getData().getItems());
                    myAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    class MyListViewAdapter extends BaseAdapter {

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
            RecycleViewHoudle houdle = null;
            if (view != null) {
                houdle = (RecycleViewHoudle) view.getTag();
            } else {
                view = LayoutInflater.from(mcontext).inflate(R.layout.expendlistview_child_item, null);
                houdle = new RecycleViewHoudle(view);
                view.setTag(houdle);
            }
            String title = items.get(i).getTitle();
            String cover_image_url = items.get(i).getCover_image_url();
            int likes_count = items.get(i).getLikes_count();
            Picasso.with(mcontext).load(cover_image_url).into(houdle.recycleImageView);
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

}

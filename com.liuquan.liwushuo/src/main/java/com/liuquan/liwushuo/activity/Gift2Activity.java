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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.liuquan.liwushuo.R;
import com.liuquan.liwushuo.bean.Gift2Info;
import com.liuquan.liwushuo.http.IOkCallback;
import com.liuquan.liwushuo.http.OkHttpTools;
import com.liuquan.liwushuo.tools.DecimalFormatTool;
import com.liuquan.liwushuo.tools.LogTool;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Gift2Activity extends AppCompatActivity {
    @Bind(R.id.gift2_gruidview)
    PullToRefreshGridView mGruidView;
    @Bind(R.id.gift2_menu_back)
    ImageView backImageView;
    @Bind(R.id.gift2_menu_sort)
    ImageView sortImageView;
    @Bind(R.id.gift2_menu_title)
    TextView menuTextView;
    private int id;
    private String name;
    private Context mcontext;
    private String url;
    private int offset;
    private List<Gift2Info.DataEntity.ItemsEntity> items = new ArrayList<>();
    private MyGruidViewAdapter gruidViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift2);
        mcontext = getApplication();
        //初始视图
        ButterKnife.bind(this);
        //获取数据
        downLoadData();
        //设置视图
        setUpView();
        //设置监听
        setUpListenner();
    }

    private void setUpListenner() {
        mGruidView.setMode(PullToRefreshBase.Mode.BOTH);
        mGruidView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mGruidView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                offset += 20;
                getGift2InfoData();
                gruidViewAdapter.notifyDataSetChanged();
//                mGruidView.onRefreshComplete();

            }
        });
        mGruidView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mcontext, Hot2Activity.class);
                intent.putExtra("id", items.get(position).getId());
                startActivity(intent);
            }
        });
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpView() {
        gruidViewAdapter = new MyGruidViewAdapter();
        mGruidView.setMode(PullToRefreshGridView.Mode.BOTH);
        mGruidView.setAdapter(gruidViewAdapter);
    }

    private void downLoadData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        menuTextView.setText(name);
        if (id == 0) {
            Toast.makeText(mcontext, "没有数据啦~~~~", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            getGift2InfoData();
        }
    }

    private void getGift2InfoData() {
        url = "http://api.liwushuo.com/v2/item_subcategories/" + id + "/items?limit=20&offset=" + offset;
        LogTool.LOG_D("------url---------------" + url);
        OkHttpTools.okGet(url + offset, Gift2Info.class, new IOkCallback<Gift2Info>() {
            @Override
            public void onSuccess(Gift2Info resulte) {
                if (resulte.getData()==null){
                    gruidViewAdapter.notifyDataSetChanged();
                    Toast.makeText(Gift2Activity.this, "没有更多啦~~~~~~", Toast.LENGTH_SHORT).show();
                }else{
                items.addAll(resulte.getData().getItems());
                //设置视图
                // setUpView();
                    gruidViewAdapter.notifyDataSetChanged();
               // mGruidView.onRefreshComplete();
                }
            }
        });
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
                view = LayoutInflater.from(mcontext).inflate(R.layout.item_hot_recycleview, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Gift2Info.DataEntity.ItemsEntity itemsEntity = items.get(i);
            String imagePath = itemsEntity.getImage_urls().get(0);

            String name = itemsEntity.getName();
            String price = itemsEntity.getPrice();
            String likeCount = DecimalFormatTool.format((float) itemsEntity.getFavorites_count() / 1000);
            viewHolder.nameTextView.setText(name);
            viewHolder.likeTextView.setText(likeCount + "K");
            viewHolder.priceTextView.setText(price);
            Picasso.with(mcontext).load(imagePath).into(viewHolder.iconImageView);

            mGruidView.onRefreshComplete();
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
}
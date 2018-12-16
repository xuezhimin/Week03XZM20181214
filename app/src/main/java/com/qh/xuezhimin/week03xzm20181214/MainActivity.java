package com.qh.xuezhimin.week03xzm20181214;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qh.xuezhimin.week03xzm20181214.adapter.DataAdapter;
import com.qh.xuezhimin.week03xzm20181214.bean.Data;
import com.qh.xuezhimin.week03xzm20181214.bean.Result;
import com.qh.xuezhimin.week03xzm20181214.data.core.DataCall;
import com.qh.xuezhimin.week03xzm20181214.data.presenter.DataPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DataCall<List<Data>>, XRecyclerView.LoadingListener {

    private ImageView mImgBtnBack;
    /**
     * 请输入关键字
     */
    private EditText mEditTitle;
    /**
     * 搜索
     */
    private Button mBtnSearch;
    private ImageView mImgBtnList;
    private XRecyclerView mXRecyclerView;
    private DataPresenter mDataPresenter;
    private DataAdapter mDataAdapter;
    private String s;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isGrid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //实例化p层
        mDataPresenter = new DataPresenter(this);

        //适配器
        mDataAdapter = new DataAdapter(this);
        mXRecyclerView.setAdapter(mDataAdapter);

        //网格布局
        mGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        //线性布局
        mLinearLayoutManager = new LinearLayoutManager(this);
        //添加默认的布局
        mXRecyclerView.setLayoutManager(mLinearLayoutManager);

        //添加下拉和刷新的监听器
        mXRecyclerView.setLoadingListener(MainActivity.this);


    }

    //初始化控件
    private void initView() {
        mImgBtnBack = findViewById(R.id.img_btn_back);
        mImgBtnBack.setOnClickListener(this);
        mEditTitle = findViewById(R.id.edit_title);
        mBtnSearch = findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);
        mImgBtnList = findViewById(R.id.img_btn_list);
        mImgBtnList.setOnClickListener(this);
        mXRecyclerView = findViewById(R.id.x_recycler_view);
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_back:
                finish();
                break;
            //点击搜索
            case R.id.btn_search:
                //加载数据   刷新
                mXRecyclerView.refresh();
                break;
            //list  grid切换
            case R.id.img_btn_list:
                if (mXRecyclerView.getLayoutManager().equals(mGridLayoutManager)) {
                    mImgBtnList.setImageResource(R.drawable.list);
                    isGrid = false;
                    mDataAdapter.setViewType(DataAdapter.LINEAR_TYPE);
                    mXRecyclerView.setLayoutManager(mLinearLayoutManager);
                } else {
                    mImgBtnList.setImageResource(R.drawable.grid);
                    isGrid = true;
                    mDataAdapter.setViewType(DataAdapter.GRID_TYPE);
                    mXRecyclerView.setLayoutManager(mGridLayoutManager);
                }
                mDataAdapter.notifyDataSetChanged();
                break;
        }
    }

    //上拉刷新
    @Override
    public void onRefresh() {
        //传入关键字
        s = mEditTitle.getText().toString();
        mDataPresenter.requestData(true, s);
    }

    //下拉加载
    @Override
    public void onLoadMore() {
        //传入关键字
        s = mEditTitle.getText().toString();
        mDataPresenter.requestData(false, s);
    }


    //接口实现的成功方法
    @Override
    public void success(List<Data> data) {
        mXRecyclerView.refreshComplete();//结束刷新
        mXRecyclerView.loadMoreComplete();//结束加载更多
        if (mDataPresenter.isResresh()) {
            mDataAdapter.clearList();
        }
        mDataAdapter.addAll(data);
        mDataAdapter.notifyDataSetChanged();
    }

    //接口实现的失败方法
    @Override
    public void fail(Result result) {
        mXRecyclerView.refreshComplete();
        mXRecyclerView.loadMoreComplete();
        Toast.makeText(this, result.getCode() + "  " + result.getMsg(),
                Toast.LENGTH_SHORT).show();
    }

    //解决内存泄露
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataPresenter.unBindCall();
    }











    /*//点击条目跳转
    @Override
    public void onItemClick(Data data) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("url", data.getDetailUrl());
        startActivity(intent);
    }*/
}

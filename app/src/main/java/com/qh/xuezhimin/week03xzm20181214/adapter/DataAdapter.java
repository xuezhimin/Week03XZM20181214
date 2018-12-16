package com.qh.xuezhimin.week03xzm20181214.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qh.xuezhimin.week03xzm20181214.DetailsActivity;
import com.qh.xuezhimin.week03xzm20181214.R;
import com.qh.xuezhimin.week03xzm20181214.bean.Data;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    public final static int LINEAR_TYPE = 0;//线性
    public final static int GRID_TYPE = 1;//网格


    private List<Data> mList = new ArrayList<>();
    private Context context;

    public DataAdapter(Context context) {
        this.context = context;
    }


    private int viewType = LINEAR_TYPE;


    //设置item的视图类型
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (viewType == LINEAR_TYPE) {//通过第二个参数viewType判断选用的视图
            view = View.inflate(viewGroup.getContext(), R.layout.data_item, null);//加载item布局
        } else {
            view = View.inflate(viewGroup.getContext(), R.layout.data_item2, null);//加载item2布局
        }
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        String images = mList.get(i).getImages();//得到图片集
        String[] split = images.split("\\|");//得到一个图片
        if (split.length > 0) {
            //将https成http  进行联网显示
            String replace = split[0].replace("https", "http");
            Glide.with(context).load(replace).into(myViewHolder.imageView);//设置图片
        }
        myViewHolder.textView.setText(mList.get(i).getTitle());
        //增加点击事件
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "dongxi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("url", mList.get(i).getDetailUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int i) {
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 添加集合数据
     */
    public void addAll(List<Data> data) {
        if (data != null) {
            mList.addAll(data);
        }
    }

    /**
     * 清空数据
     */
    public void clearList() {
        mList.clear();
    }

    /**
     * 内部类
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.data_img);
            textView = itemView.findViewById(R.id.data_title);
        }
    }


    //条目点击进入详情页面 接口 回调


    //定义接口
    /*public interface OnItemClickListener {
        void onItemClick(Data data);
    }

    //方法名
    private OnItemClickListener onItemClickListener;

    //set方法      设置点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }*/


}

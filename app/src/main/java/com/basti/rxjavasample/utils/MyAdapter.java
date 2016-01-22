package com.basti.rxjavasample.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basti.rxjavasample.R;
import com.basti.rxjavasample.callback.OnItemClickListener;

import java.util.List;

/**
 * Created by SHIBW-PC on 2016/1/21.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder >{

    private List<String> mlist;
    private Context mCtx;
    private OnItemClickListener mListener;

    public MyAdapter(List<String> list,Context context){
        mlist = list;
        mCtx = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.item,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemname.setText(mlist.get(position));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemname;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemname = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        mListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}

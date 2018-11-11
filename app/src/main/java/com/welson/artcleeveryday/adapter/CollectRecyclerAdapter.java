package com.welson.artcleeveryday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.artcleeveryday.R;
import com.welson.artcleeveryday.activity.CollectActivity;
import com.welson.artcleeveryday.activity.MainActivity;
import com.welson.artcleeveryday.entity.MainData;

import java.util.ArrayList;

public class CollectRecyclerAdapter extends RecyclerView.Adapter<CollectRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MainData> dataArrayList;
    private CollectActivity activity;

    public CollectRecyclerAdapter(Context context, ArrayList<MainData> dataArrayList){
        this.context = context;
        activity = (CollectActivity)context;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public CollectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.collect_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectRecyclerAdapter.ViewHolder viewHolder, int i) {
        final int position = i;
        viewHolder.collectTitle.setText(dataArrayList.get(i).getTitle());
        viewHolder.collectAuthor.setText(dataArrayList.get(i).getAuthor());
        viewHolder.collectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.overridePendingTransition();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("curr_date",String.valueOf(dataArrayList.get(position).getDate().getCurr()));
                Log.d("dingyl","curr_date adapter : " + dataArrayList.get(position).getDate().getCurr());
                context.startActivity(intent);
                //activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView collectTitle;
        private TextView collectAuthor;
        private RelativeLayout collectItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            collectTitle = itemView.findViewById(R.id.collect_title);
            collectAuthor = itemView.findViewById(R.id.collect_author);
            collectItem = itemView.findViewById(R.id.collect_item);
        }
    }
}

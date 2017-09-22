package com.clock.sunyangyang.ss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sunyangyang on 17/9/20.
 */

public class WeekSelectActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<String> mList = new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private String[] weekDay = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    private LayoutInflater mInflater;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_select);
        mInflater = LayoutInflater.from(this);
        findViewById(R.id.txt_back).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                Intent intent = new Intent();
                Collections.sort(mList);
                intent.putStringArrayListExtra("result", mList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        Collections.sort(mList);
        intent.putStringArrayListExtra("result", mList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(mInflater.inflate(R.layout.item_week_select, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final String pos = String.valueOf(position);
            final MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.mTxtWeek.setText(weekDay[position]);
            if (mList.contains(pos)) {
                viewHolder.mImgSelect.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mImgSelect.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.mImgSelect.getVisibility() == View.VISIBLE) {
                        if (mList.contains(pos)) {
                            mList.remove(pos);
                        }
                        viewHolder.mImgSelect.setVisibility(View.GONE);
                    } else {
                        if (!mList.contains(pos)) {
                            mList.add(pos);
                        }
                        viewHolder.mImgSelect.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return weekDay.length;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtWeek;
        private ImageView mImgSelect;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTxtWeek = (TextView) itemView.findViewById(R.id.txt_week);
            mImgSelect = (ImageView) itemView.findViewById(R.id.img_select);
        }
    }
}

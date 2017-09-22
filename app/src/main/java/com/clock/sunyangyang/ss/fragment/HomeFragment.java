package com.clock.sunyangyang.ss.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.clock.sunyangyang.ss.AlarmActivity;
import com.clock.sunyangyang.ss.R;
import com.clock.sunyangyang.ss.adapter.AlarmListAdapter;
import com.clock.sunyangyang.ss.bean.AlarmBean;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sunyangyang on 17/9/15.
 */

public class HomeFragment extends Fragment implements AlarmListAdapter.StatusChange, View.OnClickListener {
    private final int REQUEST_ALARM = 0;
    private Activity mContext;
    private SwipeMenuRecyclerView mSwipeRecyclerView;
    private ImageView mImgAdd;
    private AlarmListAdapter mAdapter;
    List<AlarmBean> mList = new ArrayList<AlarmBean>();
    Random random = new Random();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mImgAdd = (ImageView) view.findViewById(R.id.img_add);
        for (int i = 0; i < 10; i++) {
            addRandomData(i);
        }
        mAdapter = new AlarmListAdapter(mContext, mList, this);
        mSwipeRecyclerView.setAdapter(mAdapter);
        mSwipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mSwipeRecyclerView.setSwipeItemClickListener(mItemClickListener);
        mSwipeRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

        mImgAdd.setOnClickListener(this);

        return view;
    }

    private void addRandomData(int id) {
        AlarmBean bean = new AlarmBean();
        bean.id = id;
        bean.hour = random.nextInt(24);
        bean.minute = random.nextInt(60);
        bean.mon = random.nextInt(2);
        bean.tue = random.nextInt(2);
        bean.wed = random.nextInt(2);
        bean.thu = random.nextInt(2);
        bean.fri = random.nextInt(2);
        bean.sat = random.nextInt(2);
        bean.sun = random.nextInt(2);
        bean.isTurnOn = random.nextBoolean();
        mList.add(bean);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.delete_width);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem closeItem = new SwipeMenuItem(getContext())
                    .setBackgroundColor(Color.WHITE)
                    .setImage(R.mipmap.delete)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(closeItem);
            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };

    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                mList.remove(adapterPosition);
                mAdapter.setData(mList);
                mAdapter.notifyItemRemoved(adapterPosition);
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * RecyclerView的Item点击监听。
     */
    private SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
//            Toast.makeText(getContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, AlarmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", mAdapter.getItemObject(position));
            intent.putExtra("alarm", bundle);
            startActivityForResult(intent, REQUEST_ALARM);
        }
    };

    @Override
    public void changed(AlarmBean bean) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).id == bean.id) {
                mList.get(i).isTurnOn = bean.isTurnOn;
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add:
                Intent intent = new Intent(mContext, AlarmActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("XXXXX", "resultCode = " + resultCode);
    }
}

package com.clock.sunyangyang.ss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.clock.sunyangyang.ss.R;
import com.clock.sunyangyang.ss.bean.AlarmBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyangyang on 17/9/15.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int mColor;
    private int mNormalColor;
    private List<AlarmBean> mList = new ArrayList<AlarmBean>();
    private List<String> mListCheck = new ArrayList<String>();
    private StatusChange mListener;
    public AlarmListAdapter(Context context, List<AlarmBean> list, StatusChange listener) {
        mContext = context;
        mListener = listener;
        mColor = 0xff1296db;
        mNormalColor = 0xff8a8a8a;
        if (list != null) {
            mList.addAll(list);
            mListCheck.clear();
            for (int i = 0; i < list.size(); i++) {
                if (mList.get(i).isTurnOn) {
                    mListCheck.add(mList.get(i).id + "");
                }
            }
        }
    }

    public void setData(List<AlarmBean> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            mListCheck.clear();
            for (int i = 0; i < list.size(); i++) {
                if (mList.get(i).isTurnOn) {
                    mListCheck.add(mList.get(i).id + "");
                }
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout_alarm, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        final AlarmBean bean = mList.get(position);
        if (bean == null) {
            return;
        }
        viewHolder.mTxtTime.setText((bean.hour >= 10 ? bean.hour : "0" + bean.hour) + ":" + (bean.minute >= 10 ? bean.minute : "0" + bean.minute));
        viewHolder.mTxtMon.setTextColor(bean.mon == 0 ? mNormalColor : mColor);
        viewHolder.mTxtTue.setTextColor(bean.tue == 0 ? mNormalColor : mColor);
        viewHolder.mTxtWed.setTextColor(bean.wed == 0 ? mNormalColor : mColor);
        viewHolder.mTxtThu.setTextColor(bean.thu == 0 ? mNormalColor : mColor);
        viewHolder.mTxtFri.setTextColor(bean.fri == 0 ? mNormalColor : mColor);
        viewHolder.mTxtSat.setTextColor(bean.sat == 0 ? mNormalColor : mColor);
        viewHolder.mTxtSun.setTextColor(bean.sun == 0 ? mNormalColor : mColor);
        if (mListCheck.contains(bean.id + "")) {
            viewHolder.mCheckBox.setChecked(true);
        } else {
            viewHolder.mCheckBox.setChecked(false);
        }

        viewHolder.mCheckBox.setTag(bean);
        viewHolder.mCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
//        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.e("XXXXX", "viewHolder.getAdapterPosition() = " + viewHolder.getAdapterPosition() + ", pos = " + pos);
////                int pos = viewHolder.getAdapterPosition();
////                if (isChecked) {
////                    if (!mListCheck.contains(bean.id + "")) {
////                        mListCheck.add(bean.id + "");
////                    }
////                } else {
////                    if (mListCheck.contains(bean.id + "")) {
////                        mListCheck.remove(bean.id + "");
////                    }
////                }
////                mList.get(viewHolder.getAdapterPosition()).isTurnOn = isChecked;
////                mListener.changed(mList.get(viewHolder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtTime;
        private TextView mTxtMon;
        private TextView mTxtTue;
        private TextView mTxtWed;
        private TextView mTxtThu;
        private TextView mTxtFri;
        private TextView mTxtSat;
        private TextView mTxtSun;
        private CheckBox mCheckBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTxtTime = (TextView) itemView.findViewById(R.id.txt_time);
            mTxtMon = (TextView) itemView.findViewById(R.id.txt_mon);
            mTxtTue = (TextView) itemView.findViewById(R.id.txt_tue);
            mTxtWed = (TextView) itemView.findViewById(R.id.txt_wed);
            mTxtThu = (TextView) itemView.findViewById(R.id.txt_thu);
            mTxtFri = (TextView) itemView.findViewById(R.id.txt_fri);
            mTxtSat = (TextView) itemView.findViewById(R.id.txt_sat);
            mTxtSun = (TextView) itemView.findViewById(R.id.txt_sun);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_turn_on);
        }
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getTag() != null && buttonView.getTag() instanceof AlarmBean) {
                AlarmBean bean = (AlarmBean) buttonView.getTag();
                if (isChecked) {
                    if (!mListCheck.contains(bean.id + "")) {
                        mListCheck.add(bean.id + "");
                    }
                } else {
                    if (mListCheck.contains(bean.id + "")) {
                        mListCheck.remove(bean.id + "");
                    }
                }
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).id == bean.id) {
                        mList.get(i).isTurnOn = isChecked;
                        mListener.changed(bean);
                        break;
                    }
                }
            }
//            int pos = (int) buttonView.getTag();
//            Log.e("XXXXX", "pos = " + pos + ", mList = " + mList.size());
        }
    };

    public AlarmBean getItemObject(int position) {
        return mList.get(position);
    }

    public interface StatusChange {
        void changed(AlarmBean bean);
    }
}

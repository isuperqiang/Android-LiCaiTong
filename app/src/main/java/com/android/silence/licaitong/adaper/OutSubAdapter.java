package com.android.silence.licaitong.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.silence.liccaitong.R;
import com.android.silence.licaitong.bean.TbOutAccount;

import java.util.List;

/**
 * Created by Silence on 2015/10/30 0030.
 */
public class OutSubAdapter extends BaseAdapter {

    private Context mContext;
    private List<TbOutAccount> mList;

    public OutSubAdapter(Context context, List<TbOutAccount> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.text_date);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.text_money);
            viewHolder.tvNote = (TextView) convertView.findViewById(R.id.text_note);
            viewHolder.tvPlace = (TextView) convertView.findViewById(R.id.text_place);
            viewHolder.tvSubject = (TextView) convertView.findViewById(R.id.text_subject);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TbOutAccount tbOutAccount = mList.get(position);
        viewHolder.tvDate.setText(tbOutAccount.getDate());
        viewHolder.tvPlace.setText(tbOutAccount.getPlace());
        float floatMoney = tbOutAccount.getMoney();
        int intMoney = (int) floatMoney;
        if (floatMoney != intMoney) {
            viewHolder.tvMoney.setText(String.valueOf(floatMoney));
        } else {
            viewHolder.tvMoney.setText(String.valueOf(intMoney));
        }
        viewHolder.tvNote.setText(tbOutAccount.getNote());
        viewHolder.tvSubject.setText(tbOutAccount.getSubject());
        return convertView;
    }

    class ViewHolder {
        TextView tvSubject;
        TextView tvDate;
        TextView tvNote;
        TextView tvMoney;
        TextView tvPlace;
    }
}

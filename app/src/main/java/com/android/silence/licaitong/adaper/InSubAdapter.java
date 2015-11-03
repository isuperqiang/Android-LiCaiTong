package com.android.silence.licaitong.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.silence.liccaitong.R;
import com.android.silence.licaitong.bean.TbInAccount;

import java.util.List;

/**
 * Created by Silence on 2015/10/30 0030.
 */
public class InSubAdapter extends BaseAdapter {

    private List<TbInAccount> mList;
    private Context mContext;

    public InSubAdapter(Context context, List<TbInAccount> list) {
        mList = list;
        mContext = context;
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
        TbInAccount tbInAccount = mList.get(position);
        viewHolder.tvPlace.setText(tbInAccount.getPlace());
        viewHolder.tvDate.setText(tbInAccount.getDate());
        viewHolder.tvNote.setText(tbInAccount.getNote());
        float floatMoney = tbInAccount.getMoney();
        int intMoney = (int) floatMoney;
        if (floatMoney != intMoney) {
            viewHolder.tvMoney.setText(String.valueOf(floatMoney));
        } else {
            viewHolder.tvMoney.setText(String.valueOf(intMoney));
        }
        viewHolder.tvSubject.setText(tbInAccount.getSubject());
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

package com.android.silence.licaitong.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Silence on 2015/10/27 0027.
 * 创建日期选择对话框的类
 */
public class DateUtils extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText mEditText;

    public DateUtils() {
    }

    public DateUtils(EditText editText) {
        mEditText = editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Calendar 是一个抽象类，是通过getInstance()来获得实例,设置成系统默认时间
        final Calendar c = Calendar.getInstance();
        //获取年，月，日
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        StringBuffer stringBuffer = new StringBuffer(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        mEditText.setText(stringBuffer);
    }
}

package com.android.silence.licaitong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.silence.licaitong.dao.OutAccountDAO;
import com.android.silence.licaitong.dao.OutSubjectDAO;
import com.android.silence.licaitong.utils.DateUtils;
import com.android.silence.liccaitong.R;
import com.android.silence.licaitong.bean.TbOutAccount;

import java.util.List;

public class AddOutAccount extends AppCompatActivity {

    private Spinner mSpinner;
    private EditText editDate;
    private EditText editPlace;
    private EditText editNote;
    private EditText editMoney;
    private TextView tvNewOut;
    private boolean flag;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_out_account);
        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        flag = intent.getBooleanExtra("isStored", false);
        if (flag) {
            tvNewOut.setText("当前支出");
            id = intent.getIntExtra("id", 0);
            OutAccountDAO outAccountDAO = new OutAccountDAO(this);
            TbOutAccount tbOutAccount = outAccountDAO.find(id);
            editDate.setText(tbOutAccount.getDate());
            editPlace.setText(tbOutAccount.getPlace());
            editNote.setText(tbOutAccount.getNote());
            float floatMoney = tbOutAccount.getMoney();
            int intMoney = (int) floatMoney;
            if (floatMoney != intMoney) {
                editMoney.setText(String.valueOf(floatMoney));
            } else {
                editMoney.setText(String.valueOf(intMoney));
            }
            mSpinner.setPrompt(tbOutAccount.getSubject());
        } else {
            tvNewOut.setText("新建支出");
        }
        List<String> list = getSpinnerList();
        ArrayAdapter<String> mAdapter;
        if (list != null) {
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        } else {
            String arr[] = getResources().getStringArray(R.array.spinner_list_out_ac);
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        }
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
    }

    /**
     * 保存按钮点击响应的事件
     *
     * @param view 按钮
     */
    public void outAccountSaveClick(View view) {
        String money = editMoney.getText().toString().trim();
        String date = editDate.getText().toString().trim();
        String place = editPlace.getText().toString().trim();
        String note = editNote.getText().toString().trim();
        String subject = (String) mSpinner.getSelectedItem();
        if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(subject)) {
            final OutAccountDAO outAccountDAO = new OutAccountDAO(this);
            final TbOutAccount tbOutAccount;
            float m;
            try {
                m = Float.parseFloat(money);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "请输入正确的金额", Toast.LENGTH_SHORT).show();
                return;
            }
            if (flag) {
                tbOutAccount = new TbOutAccount(id, date, place, m, subject, note);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        outAccountDAO.update(tbOutAccount);
                    }
                }).start();
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                tbOutAccount = new TbOutAccount(outAccountDAO.getMaxId() + 1, date, place,
                        m, subject, note);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        outAccountDAO.add(tbOutAccount);
                    }
                }).start();
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入金额和科目", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mSpinner = (Spinner) findViewById(R.id.spinner_out_ac);
        editDate = (EditText) findViewById(R.id.edit_date_out_ac);
        editMoney = (EditText) findViewById(R.id.edit_money_out_ac);
        editNote = (EditText) findViewById(R.id.edit_note_out_ac);
        editPlace = (EditText) findViewById(R.id.edit_place_out_ac);
        tvNewOut = (TextView) findViewById(R.id.text_new_out_acco);

    }

    public void clickSelect(View view) {
        DateUtils dateUtils = new DateUtils(editDate);
        dateUtils.show(AddOutAccount.this.getFragmentManager(), "OutAccountDatePicker");
    }

    public void back(View view) {
        finish();
    }

    /**
     * 从数据库获取支出的科目，用于为Spinner提供数据集
     *
     * @return 数据列表集合
     */
    private List<String> getSpinnerList() {
        OutSubjectDAO outSubjectDAO = new OutSubjectDAO(this);
        return outSubjectDAO.getAllSubject();
    }
}

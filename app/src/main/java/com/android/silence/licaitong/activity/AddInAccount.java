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

import com.android.silence.licaitong.bean.TbInAccount;
import com.android.silence.licaitong.dao.InAccountDAO;
import com.android.silence.licaitong.dao.InSubjectDAO;
import com.android.silence.licaitong.utils.DateUtils;
import com.android.silence.liccaitong.R;

import java.util.List;

public class AddInAccount extends AppCompatActivity {

    private Spinner mSpinner;
    private EditText editDate;
    private EditText editPlace;
    private EditText editNote;
    private EditText editMoney;
    private TextView tvNewIn;
    private boolean flag;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in_account);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        flag = intent.getBooleanExtra("isStored", false);
        if (flag) {
            tvNewIn.setText("当前收入");
            id = intent.getIntExtra("id", 0);
            InAccountDAO inAccountDAO = new InAccountDAO(this);
            TbInAccount tbInAccount = inAccountDAO.find(id);
            editDate.setText(tbInAccount.getDate());
            editPlace.setText(tbInAccount.getPlace());
            editNote.setText(tbInAccount.getNote());
            float floatMoney = tbInAccount.getMoney();
            int intMoney = (int) floatMoney;
            if (floatMoney != intMoney) {
                editMoney.setText(String.valueOf(floatMoney));
            } else {
                editMoney.setText(String.valueOf(intMoney));
            }
            mSpinner.setPrompt(tbInAccount.getSubject());
        } else {
            tvNewIn.setText("新建收入");
        }
        List<String> list = getSpinnerList();
        ArrayAdapter<String> mAdapter;
        if (list != null) {
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        } else {
            String arr[] = getResources().getStringArray(R.array.spinner_list_in_ac);
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        }
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mSpinner = (Spinner) findViewById(R.id.spinner_in_ac);
        editDate = (EditText) findViewById(R.id.edit_date_in_ac);
        editMoney = (EditText) findViewById(R.id.edit_money_in_ac);
        editNote = (EditText) findViewById(R.id.edit_note_in_ac);
        editPlace = (EditText) findViewById(R.id.edit_place_in_ac);
        tvNewIn = (TextView) findViewById(R.id.text_new_in_acco);

    }

    public void clickSelect(View view) {
        DateUtils dateUtils = new DateUtils(editDate);
        dateUtils.show(AddInAccount.this.getFragmentManager(), "InAccountDatePicker");
    }

    public void back(View view) {
        finish();
    }

    /**
     * 保存按钮点击响应的事件
     *
     * @param view 按钮
     */
    public void inAccountSaveClick(View view) {
        final String money = editMoney.getText().toString().trim();
        final String date = editDate.getText().toString().trim();
        final String place = editPlace.getText().toString().trim();
        final String note = editNote.getText().toString().trim();
        final String subject = (String) mSpinner.getSelectedItem();
        if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(subject)) {
            float m;
            try {
                m = Float.parseFloat(money);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(AddInAccount.this, "请输入正确的金额", Toast.LENGTH_SHORT).show();
                return;
            }
            final InAccountDAO inAccountDAO = new InAccountDAO(AddInAccount.this);
            final TbInAccount tbInAccount;
            if (flag) {
                tbInAccount = new TbInAccount(id, date, place, m, subject, note);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        inAccountDAO.update(tbInAccount);
                    }
                }).start();
                Toast.makeText(AddInAccount.this, "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                tbInAccount = new TbInAccount(inAccountDAO.getMaxId(), date, place, m, subject, note);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        inAccountDAO.add(tbInAccount);
                    }
                }).start();
                Toast.makeText(AddInAccount.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入金额和科目", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 从数据库获取收入的科目，用于为Spinner提供数据集
     *
     * @return 数据列表集合
     */
    private List<String> getSpinnerList() {
        InSubjectDAO inSubjectDAO = new InSubjectDAO(this);
        return inSubjectDAO.getAllSubject();
    }
}

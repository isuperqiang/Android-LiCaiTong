package com.android.silence.liccaitong.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.silence.liccaitong.R;
import com.android.silence.liccaitong.bean.TbUserInfo;
import com.android.silence.liccaitong.dao.UserInfoDAO;

public class UserInfoSetting extends AppCompatActivity {

    private EditText editName;
    private EditText editArea;
    private EditText editAge;
    private EditText editGender;
    private EditText editMail;
    private UserInfoDAO userInfoDAO;
    private SharedPreferences mSharedPreferences;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_setting);
        initView();
        userInfoDAO = new UserInfoDAO(this);
        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        isFirst = mSharedPreferences.getBoolean("isFirst", true);
        if (!isFirst) {
            new AsyncTask<Void, Void, TbUserInfo>() {
                @Override
                protected TbUserInfo doInBackground(Void... params) {
                    return userInfoDAO.find(userInfoDAO.getMaxId());
                }

                @Override
                protected void onPostExecute(TbUserInfo tbUserInfo) {
                    super.onPostExecute(tbUserInfo);
                    if (tbUserInfo != null) {
                        editName.setText(tbUserInfo.getName());
                        editGender.setText(tbUserInfo.getGender());
                        editArea.setText(tbUserInfo.getArea());
                        editAge.setText(String.valueOf(tbUserInfo.getAge()));
                        editMail.setText(tbUserInfo.getMail());
                    }
                }
            }.execute();
        }
    }

    private void initView() {
        editAge = (EditText) findViewById(R.id.edit_age);
        editArea = (EditText) findViewById(R.id.edit_area);
        editMail = (EditText) findViewById(R.id.edit_mail);
        editGender = (EditText) findViewById(R.id.edit_gender);
        editName = (EditText) findViewById(R.id.edit_name);
    }

    public void saveUserClick(View view) {
        String area = editArea.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String gender = editGender.getText().toString().trim();
        String mail = editMail.getText().toString().trim();
        if (!TextUtils.isEmpty(area) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(gender)
                && !TextUtils.isEmpty(mail)) {
            final TbUserInfo tbUserInfo = new TbUserInfo(userInfoDAO.getMaxId(), name, Integer.parseInt(age), area, mail, gender);
            if (isFirst) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userInfoDAO.add(tbUserInfo);
                    }
                }).start();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("isFirst", false);
                editor.apply();
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userInfoDAO.update(tbUserInfo);
                    }
                }).start();
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入信息", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        finish();
    }
}
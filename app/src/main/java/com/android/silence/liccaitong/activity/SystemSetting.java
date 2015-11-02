package com.android.silence.liccaitong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.silence.liccaitong.R;

public class SystemSetting extends AppCompatActivity implements OnClickListener {

    private TextView tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        initView();
    }

    public void back(View view) {
        finish();
    }

    private void initView() {
        TextView tvAbout = (TextView) findViewById(R.id.text_setting_about_us);
        LinearLayout llFeedback = (LinearLayout) findViewById(R.id.text_setting_feedback);
        LinearLayout llData = (LinearLayout) findViewById(R.id.text_setting_data_manage);
        LinearLayout llUserInfo = (LinearLayout) findViewById(R.id.text_setting_user_info);
        LinearLayout llSubjectManage = (LinearLayout) findViewById(R.id.text_setting_subject);
        TextView tvCheckUpdate = (TextView) findViewById(R.id.text_setting_check_update);
        tvDetails = (TextView) findViewById(R.id.about_details);
        llData.setOnClickListener(this);
        llSubjectManage.setOnClickListener(this);
        tvCheckUpdate.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        llUserInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_setting_about_us:
                tvDetails.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                tvDetails.startAnimation(animation);
                break;
            case R.id.text_setting_subject:
                startActivity(new Intent(this, SubjectManage.class));
                break;
            case R.id.text_setting_feedback:
                startActivity(new Intent(this, SendFeedback.class));
                break;
            case R.id.text_setting_data_manage:
                startActivity(new Intent(this, DataManage.class));
                break;
            case R.id.text_setting_user_info:
                startActivity(new Intent(this, UserInfoSetting.class));
                break;
            case R.id.text_setting_check_update:
                Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

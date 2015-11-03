package com.android.silence.licaitong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.silence.liccaitong.R;

public class SendFeedback extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mEditText = (EditText) findViewById(R.id.edit_feedback);
    }

    public void back(View view) {
        finish();
    }

    public void sendClick(View view) {
        String text = mEditText.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(this, "发送成功，感谢您的建议！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请输入反馈信息！", Toast.LENGTH_SHORT).show();
        }
    }
}

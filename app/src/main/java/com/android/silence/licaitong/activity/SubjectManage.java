package com.android.silence.licaitong.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.silence.licaitong.bean.TbOutSubject;
import com.android.silence.licaitong.dao.InSubjectDAO;
import com.android.silence.licaitong.dao.OutSubjectDAO;
import com.android.silence.liccaitong.R;
import com.android.silence.licaitong.bean.TbInSubject;

public class SubjectManage extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private InSubjectDAO mInSubjectDAO;
    private OutSubjectDAO mOutSubjectDAO;
    private boolean isInSubClick;
    private InSubjectFragment mInSubjectFragment;
    private OutSubjectFragment mOutSubjectFragment;
    private EditText mEditText;
    private Button inButton;
    private Button outButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_manage);
        mFragmentManager = getFragmentManager();
        initView();
        mInSubjectDAO = new InSubjectDAO(this);
        mOutSubjectDAO = new OutSubjectDAO(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        mInSubjectFragment = new InSubjectFragment(mInSubjectDAO.getAllSubject(), mInSubjectDAO);
        transaction.replace(R.id.subject_content, mInSubjectFragment);
        transaction.addToBackStack("inSubject");
        transaction.commit();
        isInSubClick = true;
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_add_sub);
        inButton = (Button) findViewById(R.id.btn_sub_in);
        outButton = (Button) findViewById(R.id.btn_sub_out);
        mImageView = (ImageView) findViewById(R.id.add_img);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bottom_layout);
                Animation fadeIn = AnimationUtils.loadAnimation(SubjectManage.this, R.anim.fade_in);
                Animation fadeOut = AnimationUtils.loadAnimation(SubjectManage.this, R.anim.fade_out);
                if (linearLayout.getVisibility() == View.VISIBLE) {
                    mImageView.startAnimation(fadeOut);
                    mImageView.setImageResource(R.mipmap.add_white);
                    mImageView.startAnimation(fadeIn);
                    Animation alphaAnimation = AnimationUtils.loadAnimation(SubjectManage.this, android.R.anim.fade_out);
                    linearLayout.startAnimation(alphaAnimation);
                    linearLayout.setVisibility(View.INVISIBLE);
                } else {
                    mImageView.startAnimation(fadeOut);
                    mImageView.setImageResource(R.mipmap.add_white_x);
                    mImageView.startAnimation(fadeIn);
                    Animation alphaAnimation = AnimationUtils.loadAnimation(SubjectManage.this, android.R.anim.fade_in);
                    linearLayout.startAnimation(alphaAnimation);
                    linearLayout.setVisibility(View.VISIBLE);
                    mEditText.requestFocus();
                }
            }
        });
    }

    public void subjectClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub_in:
                inButton.setBackgroundResource(R.drawable.btn_green_pressed);
                outButton.setBackgroundResource(R.drawable.btn_orange_normal);
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (mInSubjectFragment == null) {
                    mInSubjectFragment = new InSubjectFragment(mInSubjectDAO.getAllSubject(), mInSubjectDAO);
                }
                transaction.replace(R.id.subject_content, mInSubjectFragment);
                transaction.addToBackStack("inSubject");
                transaction.commit();
                isInSubClick = true;
                break;
            case R.id.btn_sub_out:
                inButton.setBackgroundResource(R.drawable.btn_green_normal);
                outButton.setBackgroundResource(R.drawable.btn_orange_pressed);
                FragmentTransaction transaction2 = mFragmentManager.beginTransaction();
                if (mOutSubjectFragment == null) {
                    mOutSubjectFragment = new OutSubjectFragment(mOutSubjectDAO.getAllSubject(), mOutSubjectDAO);

                }
                transaction2.replace(R.id.subject_content, mOutSubjectFragment);
                transaction2.addToBackStack("outSubject");
                transaction2.commit();
                isInSubClick = false;
                break;
            default:
                break;
        }
    }

    public void addSubClick(View view) {
        String subject = mEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(subject)) {
            if (isInSubClick) {
                mInSubjectFragment.addItem(subject);
                final TbInSubject tbInSubject = new TbInSubject(mInSubjectDAO.getMaxId(), subject);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mInSubjectDAO.add(tbInSubject);
                    }
                }).start();
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            } else {
                mOutSubjectFragment.addItem(subject);
                final TbOutSubject tbOutSubject = new TbOutSubject(mOutSubjectDAO.getMaxId(), subject);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mOutSubjectDAO.add(tbOutSubject);
                    }
                }).start();
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            }
            mEditText.setText("");
        } else {
            Toast.makeText(this, "请输入科目", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        finish();
    }

}

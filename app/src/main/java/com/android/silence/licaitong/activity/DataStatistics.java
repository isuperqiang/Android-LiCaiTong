package com.android.silence.licaitong.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.silence.licaitong.dao.InAccountDAO;
import com.android.silence.licaitong.dao.OutAccountDAO;
import com.android.silence.liccaitong.R;
import com.android.silence.licaitong.utils.PieChartUtils;

import org.achartengine.GraphicalView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStatistics extends AppCompatActivity {

    private Button btnIn;
    private Button btnOut;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private GraphicalView mGraphicalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistics);
        btnIn = (Button) findViewById(R.id.btn_data_in);
        btnOut = (Button) findViewById(R.id.btn_data_out);
        mTextView = (TextView) findViewById(R.id.text_sum);
        mLinearLayout = (LinearLayout) findViewById(R.id.chart_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initInAcco();
    }

    public void back(View view) {
        finish();
    }

    public void statisticClick(View view) {
        switch (view.getId()) {
            case R.id.btn_data_in:
                btnOut.setBackgroundResource(R.drawable.btn_orange_normal);
                btnIn.setBackgroundResource(R.drawable.btn_green_pressed);
                initInAcco();
                break;
            case R.id.btn_data_out:
                btnOut.setBackgroundResource(R.drawable.btn_orange_pressed);
                btnIn.setBackgroundResource(R.drawable.btn_green_normal);
                new AsyncTask<Void, Float, Map<String, Float>>() {
                    @Override
                    protected Map<String, Float> doInBackground(Void... params) {
                        OutAccountDAO outAccountDAO = new OutAccountDAO(DataStatistics.this);
                        List<String> list = outAccountDAO.getAllSubjects();
                        float allMoney = outAccountDAO.getAllMoney();
                        publishProgress(allMoney);
                        if (list != null) {
                            int outSubLen = list.size();
                            String[] subjects = new String[outSubLen];
                            float[] moneys = new float[outSubLen];
                            for (int i = 0; i < outSubLen; i++) {
                                subjects[i] = list.get(i);
                                moneys[i] = outAccountDAO.getMoneyOfEachSubject(subjects[i]);
                            }
                            return getData(subjects, moneys);
                        }
                        return null;
                    }

                    @Override
                    protected void onProgressUpdate(Float... values) {
                        super.onProgressUpdate(values);
                        mTextView.setText(new StringBuffer("支出共计 " + values[0] + " 元"));
                    }

                    @Override
                    protected void onPostExecute(Map<String, Float> stringFloatMap) {
                        super.onPostExecute(stringFloatMap);
                        if (stringFloatMap != null) {
                            PieChartUtils pieChartUtils = new PieChartUtils(stringFloatMap, DataStatistics.this, "支出");
                            mGraphicalView = pieChartUtils.getPieChartView();
                            pieChartUtils.onClick(true);
                            mLinearLayout.removeAllViews();
                            mLinearLayout.addView(mGraphicalView);
                        } else {
                            mTextView.setText("");
                            Toast.makeText(DataStatistics.this, "没有支出记录", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化收入表图
     */
    public void initInAcco() {
        new AsyncTask<Void, Float, Map<String, Float>>() {
            @Override
            protected Map<String, Float> doInBackground(Void... params) {
                InAccountDAO inAccountDAO = new InAccountDAO(DataStatistics.this);
                List<String> subjectsList = inAccountDAO.getAllSubjects();
                float allMoney = inAccountDAO.getAllMoney();
                publishProgress(allMoney);
                if (subjectsList != null) {
                    int inSubListLen = subjectsList.size();
                    String[] subjects = new String[inSubListLen];
                    float[] moneys = new float[inSubListLen];
                    for (int i = 0; i < inSubListLen; i++) {
                        subjects[i] = subjectsList.get(i);
                        moneys[i] = inAccountDAO.getMoneyOfEachSubject(subjects[i]);
                    }
                    return getData(subjects, moneys);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                super.onProgressUpdate(values);
                mTextView.setText(new StringBuffer("收入共计 " + values[0] + " 元"));
            }

            @Override
            protected void onPostExecute(Map<String, Float> stringFloatMap) {
                super.onPostExecute(stringFloatMap);
                if (stringFloatMap != null) {
                    PieChartUtils pieChartUtils = new PieChartUtils(stringFloatMap, DataStatistics.this, "收入");
                    mGraphicalView = pieChartUtils.getPieChartView();
                    pieChartUtils.onClick(true);
                    mLinearLayout.removeAllViews();
                    mLinearLayout.addView(mGraphicalView);
                } else {
                    mTextView.setText("");
                    Toast.makeText(DataStatistics.this, "没有收入记录", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public Map<String, Float> getData(String[] subjects, float[] moneys) {
        Map<String, Float> map = new HashMap<>();
        int len = subjects.length;
        for (int i = 0; i < len; i++) {
            map.put(subjects[i], moneys[i]);
        }
        return map;
    }
}

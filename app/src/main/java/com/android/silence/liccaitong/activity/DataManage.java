package com.android.silence.liccaitong.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.silence.liccaitong.R;
import com.android.silence.liccaitong.dao.InAccountDAO;
import com.android.silence.liccaitong.dao.InSubjectDAO;
import com.android.silence.liccaitong.dao.OutAccountDAO;
import com.android.silence.liccaitong.dao.OutSubjectDAO;

public class DataManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manage);
    }

    public void resetTable(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.text_data_del_in_acco:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        InAccountDAO inAccountDAO = new InAccountDAO(DataManage.this);
                        inAccountDAO.removeAll();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(DataManage.this, "重置成功", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                break;
            case R.id.text_data_del_out_acco:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        OutAccountDAO outAccountDAO = new OutAccountDAO(DataManage.this);
                        outAccountDAO.removeAll();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(DataManage.this, "重置成功", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                break;
            case R.id.text_data_del_in_sub:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        InSubjectDAO inSubjectDAO = new InSubjectDAO(DataManage.this);
                        inSubjectDAO.reset();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(DataManage.this, "重置成功", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                break;
            case R.id.text_data_del_out_sub:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        OutSubjectDAO outSubjectDAO = new OutSubjectDAO(DataManage.this);
                        outSubjectDAO.reset();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(DataManage.this, "重置成功", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}

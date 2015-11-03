package com.android.silence.licaitong.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.silence.licaitong.adaper.OutSubAdapter;
import com.android.silence.licaitong.dao.OutAccountDAO;
import com.android.silence.liccaitong.R;
import com.android.silence.licaitong.bean.TbOutAccount;

import java.util.List;

public class ShowOutAccount extends AppCompatActivity {

    private ListView mListView;
    private TextView tvSum;
    private OutSubAdapter mAdapter;
    private List<TbOutAccount> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_out_account);
        mListView = (ListView) findViewById(R.id.out_ac_list);
        tvSum = (TextView) findViewById(R.id.text_sum_out);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TbOutAccount tbOutAccount = list.get(position);
                goUpdate(tbOutAccount.getId());
            }
        });
        registerForContextMenu(mListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showInfo();
    }

    public void back(View view) {
        finish();
    }

    private void goUpdate(int id) {
        Intent intent = new Intent(ShowOutAccount.this, AddOutAccount.class);
        intent.putExtra("isStored", true);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void showInfo() {
        final OutAccountDAO outAccountDAO = new OutAccountDAO(this);
        new AsyncTask<Void, Integer, List<TbOutAccount>>() {
            @Override
            protected List<TbOutAccount> doInBackground(Void... params) {
                int count = outAccountDAO.getCount();
                publishProgress(count);
                list = outAccountDAO.findAll();
                return list;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                tvSum.setText(new StringBuffer("共有 " + values[0] + " 条记录"));
            }

            @Override
            protected void onPostExecute(List<TbOutAccount> list) {
                super.onPostExecute(list);
                if (list != null) {
                    mAdapter = new OutSubAdapter(ShowOutAccount.this, list);
                    mListView.setAdapter(mAdapter);
                } else {
                    tvSum.setText("");
                    Toast.makeText(ShowOutAccount.this, "没有支出记录", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 1, "修改");
        menu.add(0, 2, 2, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = menuInfo.position;
        TbOutAccount tbOutAccount = list.get(index);
        final int relId = tbOutAccount.getId();
        switch (item.getItemId()) {
            case 1:
                goUpdate(relId);
                break;
            case 2:
                final OutAccountDAO outAccountDAO = new OutAccountDAO(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        outAccountDAO.remove(relId);
                    }
                }).start();
                list.remove(index);
                mAdapter.notifyDataSetChanged();
                tvSum.setText(new StringBuffer("共有 " + list.size() + " 条记录"));
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
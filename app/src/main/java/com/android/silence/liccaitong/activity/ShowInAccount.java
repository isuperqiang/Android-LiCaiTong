package com.android.silence.liccaitong.activity;

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

import com.android.silence.liccaitong.R;
import com.android.silence.liccaitong.adaper.InSubAdapter;
import com.android.silence.liccaitong.bean.TbInAccount;
import com.android.silence.liccaitong.dao.InAccountDAO;

import java.util.List;

public class ShowInAccount extends AppCompatActivity {

    private ListView mListView;
    private TextView tvSum;
    private InSubAdapter mAdapter;
    private List<TbInAccount> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_in_account);
        mListView = (ListView) findViewById(R.id.in_ac_list);
        tvSum = (TextView) findViewById(R.id.text_sum_in);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TbInAccount tbInAccount = list.get(position);
                goUpdate(tbInAccount.getId());
            }
        });
        registerForContextMenu(mListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showInfo();
    }

    private void showInfo() {
        final InAccountDAO inAccountDAO = new InAccountDAO(this);
        new AsyncTask<Void, Integer, List<TbInAccount>>() {
            @Override
            protected List<TbInAccount> doInBackground(Void... params) {
                int count = inAccountDAO.getCount();
                publishProgress(count);
                list = inAccountDAO.findAll();
                return list;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                tvSum.setText(new StringBuffer("共有 " + values[0] + " 条记录"));
            }

            @Override
            protected void onPostExecute(List<TbInAccount> list) {
                super.onPostExecute(list);
                if (list != null) {
                    mAdapter = new InSubAdapter(ShowInAccount.this, list);
                    mListView.setAdapter(mAdapter);
                } else {
                    tvSum.setText("");
                    Toast.makeText(ShowInAccount.this, "没有收入记录", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 1, "修改");
        menu.add(0, 2, 2, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = menuInfo.position;
        TbInAccount tbInAccount = list.get(index);
        final int relId = tbInAccount.getId();
        switch (item.getItemId()) {
            case 1:
                goUpdate(relId);
                break;
            case 2:
                final InAccountDAO inAccountDAO = new InAccountDAO(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        inAccountDAO.remove(relId);
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

    private void goUpdate(int id) {
        Intent intent = new Intent(this, AddInAccount.class);
        intent.putExtra("isStored", true);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
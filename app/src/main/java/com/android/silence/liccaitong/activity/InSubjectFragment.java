package com.android.silence.liccaitong.activity;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.silence.liccaitong.dao.InSubjectDAO;

import java.util.List;

/**
 * Created by Silence on 2015/10/30 0030.
 */
public class InSubjectFragment extends ListFragment {

    private List<String> mList;
    private ArrayAdapter<String> mAdapter;
    private InSubjectDAO mInSubjectDAO;

    public InSubjectFragment(List<String> list, InSubjectDAO inSubjectDAO) {
        mList = list;
        mInSubjectDAO = inSubjectDAO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
        setListAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 1, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case 0:
                final String subject = mAdapter.getItem(index);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mInSubjectDAO.remove(subject);
                    }
                }).start();
                mList.remove(subject);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    public void addItem(String subject) {
        mList.add(subject);
        mAdapter.notifyDataSetChanged();
    }
}
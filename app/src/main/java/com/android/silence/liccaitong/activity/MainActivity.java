package com.android.silence.liccaitong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.silence.liccaitong.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String[] TITLES = new String[]{"新增支出", "新增收入", "我的支出", "我的收入",
            "数据统计", "系统设置"};
    private static final int[] IMAGE_ID = new int[]{R.mipmap.add_out_account, R.mipmap.add_in_account,
            R.mipmap.show_out_account, R.mipmap.show_in_account, R.mipmap.data_statistics,
            R.mipmap.system_setting};
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView mGridView = (GridView) findViewById(R.id.gridView);
        SimpleAdapter adapter = new SimpleAdapter(this, initList(), R.layout.grid_item, new String[]
                {"imgId", "titleId"}, new int[]{R.id.item_image, R.id.item_title});
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new GridItemListener());
    }

    private List<Map<String, Object>> initList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < IMAGE_ID.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imgId", IMAGE_ID[i]);
            map.put("titleId", TITLES[i]);
            list.add(map);
        }
        return list;
    }

    private class GridItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(MainActivity.this, AddOutAccount.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this, AddInAccount.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, ShowOutAccount.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, ShowInAccount.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(MainActivity.this, DataStatistics.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(MainActivity.this, SystemSetting.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
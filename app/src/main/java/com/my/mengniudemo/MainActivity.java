package com.my.mengniudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.my.mengniudemo.adapter.LeftListAdapter;
import com.my.mengniudemo.adapter.RightAdapter;
import com.my.mengniudemo.bean.CategoryBean;
import com.my.mengniudemo.bean.TestData;
import com.my.mengniudemo.view.PinnedHeaderListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private ListView lv_left;
    private LeftListAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private PinnedHeaderListView pinnedListView;
    private boolean isScroll = true;
    private List<CategoryBean> cateBeanList;
    int x = 0;
    int y = 0;
    int z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MenuTextAllCaps);  //主题必须要设置(必须要在设置布局之前)
        setContentView(R.layout.activity_main);

        initToolbar("选择产品");

        cateBeanList = TestData.setFalseData();
        initListView();
        initPinnedHeaderListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     //返回按钮
                finish();
                return true;
            case R.id.search_menu:      //搜索按钮
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_left:
                isScroll = false;
                //左侧的ListView选中的效果
                for (int i = 0; i < cateBeanList.size(); i++) {
                    CategoryBean cb = cateBeanList.get(i);
                    if (i == position) {
                        cb.setFlag(true);
                    } else {
                        cb.setFlag(false);
                    }
                }
                leftAdapter.setData(cateBeanList);

                //点击左侧的时候和右侧的ListView设置关联
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += rightAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);
                break;
        }
    }


    private void initListView() {
        lv_left = (ListView) findViewById(R.id.lv_left);
        leftAdapter = new LeftListAdapter(this, cateBeanList);
        lv_left.setAdapter(leftAdapter);
        lv_left.setOnItemClickListener(this);
    }

    private void initPinnedHeaderListView() {
        pinnedListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        rightAdapter = new RightAdapter(this, cateBeanList);
        pinnedListView.setAdapter(rightAdapter);
        pinnedListView.setOnScrollListener(this);
    }

    //******************************** setOnScrollListener开始 ***********************************
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            //当不滚动时
            case SCROLL_STATE_IDLE:
                //判断滚动到底部
                if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                    lv_left.setSelection(ListView.FOCUS_DOWN);
                }
                //判断滚动到顶部
                if (pinnedListView.getFirstVisiblePosition() == 0) {
                    lv_left.setSelection(0);
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isScroll) {
            for (int i = 0; i < cateBeanList.size(); i++) {
                if (i == rightAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                    cateBeanList.get(i).setFlag(true);
                    x = i;
                } else {
                    cateBeanList.get(i).setFlag(false);
                }
            }
            if (x != y) {
                leftAdapter.setData(cateBeanList);  //左侧ListView刷新显示
                y = x;
                //左侧ListView滚动到最后位置
                if (y == lv_left.getLastVisiblePosition()) {
                    lv_left.setSelection(z);
                }
                //左侧ListView滚动到第一个位置
                if (x == lv_left.getFirstVisiblePosition()) {
                    lv_left.setSelection(z);
                }

                if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                    lv_left.setSelection(ListView.FOCUS_DOWN);
                }
            }
        } else {
            isScroll = true;
        }
    }
    //******************************** setOnScrollListener结束 ***********************************

    /**
     * 创建右上角的搜索图标
     *
     * @param menu menu
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    /**
     * 设置Toolbar
     *
     * @param title 要显示的Title
     */
    private void initToolbar(String title) {
        setTitle(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

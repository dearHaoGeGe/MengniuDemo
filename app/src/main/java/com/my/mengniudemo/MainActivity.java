package com.my.mengniudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.my.mengniudemo.adapter.ClassifyAndPackageAdapter;
import com.my.mengniudemo.adapter.GirdDropDownAdapter;
import com.my.mengniudemo.adapter.LeftListAdapter;
import com.my.mengniudemo.adapter.RightAdapter;
import com.my.mengniudemo.bean.CategoryBean;
import com.my.mengniudemo.bean.ProductBean;
import com.my.mengniudemo.bean.TestData;
import com.my.mengniudemo.view.MyGridView;
import com.my.mengniudemo.view.PinnedHeaderListView;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnClickListener {

    private ListView lv_left;
    private LeftListAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private PinnedHeaderListView pinnedListView;
    private boolean isScroll = true;
    private List<CategoryBean> cateBeanList;
    int x = 0;
    int y = 0;
    int z = 0;

    //底部产品数量编辑
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private TextView tv_total_num;
    private TextView tv_total_money;

    //最上面的筛选条
    private DropDownMenu mDropDownMenu;
    private List<View> popupViews;
    private String headers[] = {"单品", "筛选"};
    private GirdDropDownAdapter listChooseAdapter;
    private String listChoose[] = {"单品列表", "套餐产品列表"};
    private ClassifyAndPackageAdapter classifyAdapter;
    private ClassifyAndPackageAdapter packageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MenuTextAllCaps);  //主题必须要设置(必须要在设置布局之前)
        setContentView(R.layout.activity_main);

        initToolbar("选择产品");
        addFalseData();
        initSizer();    //初始化顶部的筛选器

        //initListView();
        //initPinnedHeaderListView();
        //initBottomLayout();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                mDropDownMenu.closeMenu();
                break;

            case R.id.tv_check_photo:
                Toast.makeText(this, "查看照片", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_close_sheet:
                if (bottomSheetLayout.isSheetShowing()) {
                    bottomSheetLayout.dismissSheet();
                }
                break;

            case R.id.tv_add:
                break;

            case R.id.tv_minus:
                break;

            case R.id.btn_remove_product:
                break;

            case R.id.btn_confirm:
                break;
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
                leftAdapter.setData(cateBeanList, true);

                //点击左侧的时候和右侧的ListView设置关联
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += rightAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);
                break;

            case R.id.gv_classify:
                classifyAdapter.setIsSelected(position);
                break;

            case R.id.gv_packaging:
                packageAdapter.setIsSelected(position);
                break;

            default:
                position = position - 1;
                if (position > -1) {
                    listChooseAdapter.setCheckItem(position);
                    mDropDownMenu.setTabText(position == 0 ? "单品" : "套餐");
                    mDropDownMenu.closeMenu();
                }
                break;
        }
    }

    private void addFalseData() {
        cateBeanList = TestData.setFalseData();
    }

    /**
     * 初始化顶部的筛选器
     */
    private void initSizer() {
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);

        //选择单品或者套餐列表
        ListView lvChoose = new ListView(this);
        View header = getLayoutInflater().inflate(R.layout.item_list_drop_down, null);
        TextView tv = (TextView) header.findViewById(R.id.text);
        tv.setText("选择产品列表类型");
        lvChoose.addHeaderView(header);
        listChooseAdapter = new GirdDropDownAdapter(this, Arrays.asList(listChoose));
        lvChoose.setDividerHeight(0);
        lvChoose.setAdapter(listChooseAdapter);
        lvChoose.setOnItemClickListener(this);

        //选择业态和包装类型
        View view = getLayoutInflater().inflate(R.layout.choose_classify_package_layout, (ViewGroup) getWindow().getDecorView(), false);
        MyGridView gv_classify = (MyGridView) view.findViewById(R.id.gv_classify);
        classifyAdapter = new ClassifyAndPackageAdapter(this, TestData.getClassifyData());
        gv_classify.setAdapter(classifyAdapter);
        MyGridView gv_packaging = (MyGridView) view.findViewById(R.id.gv_packaging);
        packageAdapter = new ClassifyAndPackageAdapter(this, TestData.getPackagingData());
        gv_packaging.setAdapter(packageAdapter);
        TextView tv_ok = (TextView) view.findViewById(R.id.ok);
        tv_ok.setOnClickListener(this);
        gv_classify.setOnItemClickListener(this);
        gv_packaging.setOnItemClickListener(this);

        popupViews = new ArrayList<>();
        popupViews.add(lvChoose);
        popupViews.add(view);

        View contentView = getLayoutInflater().inflate(R.layout.activity_main_product, (ViewGroup) getWindow().getDecorView(), false);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        initListView(contentView);
        initPinnedHeaderListView(contentView);
        initBottomLayout(contentView);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    private void initListView(View view) {
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        leftAdapter = new LeftListAdapter(this, cateBeanList);
        lv_left.setAdapter(leftAdapter);
        lv_left.setOnItemClickListener(this);
    }

    private void initPinnedHeaderListView(View view) {
        pinnedListView = (PinnedHeaderListView) view.findViewById(R.id.pinnedListView);
        rightAdapter = new RightAdapter(this, cateBeanList, leftAdapter);
        pinnedListView.setAdapter(rightAdapter);
        pinnedListView.setOnScrollListener(this);
    }

    private void initBottomLayout(View view) {
        bottomSheetLayout = (BottomSheetLayout) view.findViewById(R.id.bottomSheetLayout);
        tv_total_num = (TextView) view.findViewById(R.id.tv_total_num);
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        tv_total_num.setText(String.valueOf("共 0 件"));
    }

    /**
     * 显示底部产品数量添加
     */
    public void showBottomSheet(int section, int position) {
        bottomSheet = createBottomSheetView(section, position);
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(bottomSheet);
        }
    }

    /**
     * 创建底部view
     *
     * @return view
     */
    private View createBottomSheetView(int section, int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_add_num,
                (ViewGroup) getWindow().getDecorView(), false);
        TextView tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);   //当前产品名称
        TextView tv_check_photo = (TextView) view.findViewById(R.id.tv_check_photo);     //查看照片
        TextView tv_stock_num = (TextView) view.findViewById(R.id.tv_stock_num);         //剩余数量
        TextView tv_close_sheet = (TextView) view.findViewById(R.id.tv_close_sheet);     //关闭
        TextView tv_minus = (TextView) view.findViewById(R.id.tv_minus);                 //“-”号
        TextView tv_add = (TextView) view.findViewById(R.id.tv_add);                     //“+”号
        EditText et_buy_num = (EditText) view.findViewById(R.id.et_buy_num);             //添加产品的数量
        EditText et_price = (EditText) view.findViewById(R.id.et_price);                 //产品单价
        EditText et_remark = (EditText) view.findViewById(R.id.et_remark);               //产品备注
        Button btn_remove_product = (Button) view.findViewById(R.id.btn_remove_product); //删除此产品
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);               //确认

        tv_check_photo.setOnClickListener(this);
        tv_close_sheet.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        btn_remove_product.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);

        ProductBean pb = cateBeanList.get(section).getList().get(position);
        tv_product_name.setText(pb.getProductName());
        tv_stock_num.setText("库存" + pb.getStockItem() + "件；" + pb.getStockPackage() + "包");
        return view;
    }

    /**
     * 刷新下面图标数量和价格
     *
     * @param cbList cbList
     */
    public void refreshShopCarNum(List<CategoryBean> cbList) {
        int carBuyNum = 0;
        float total_money = 0.0f;
        for (int i = 0; i < cbList.size(); i++) {
            carBuyNum += cbList.get(i).getBuyNum();

            List<ProductBean> pbList = cbList.get(i).getList();
            for (int j = 0; j < pbList.size(); j++) {
                if (pbList.get(j).getBuyNum() > 0) {
                    total_money += (pbList.get(j).getPrice()) * (pbList.get(j).getBuyNum());
                }
            }
        }
        if (carBuyNum > 0) {
            tv_total_num.setText(String.valueOf("共 " + carBuyNum + " 件"));
            tv_total_money.setText(String.valueOf("￥" + total_money));
        } else {
            tv_total_num.setText(String.valueOf("共 0 件"));
            tv_total_money.setText(String.valueOf("￥0.00"));
        }
    }

//    /**
//     * 清空购物车(把实体类中的所有BuyNum的数量设置为0)
//     */
//    private void clearEmptyCar() {
//        int cbListSize = cateBeanList.size();
//        for (int i = 0; i < cbListSize; i++) {
//            cateBeanList.get(i).setBuyNum(0);
//            List<ProductBean> pbList = cateBeanList.get(i).getList();
//            for (int j = 0; j < pbList.size(); j++) {
//                pbList.get(j).setBuyNum(0);
//            }
//        }
//        leftAdapter.setData(cateBeanList, true);
//        rightAdapter.setCateBeanList(cateBeanList, true);
//        bottomSheetLayout.dismissSheet();
//        refreshShopCarNum(cateBeanList);
//    }

    //******************************** 动画 开始 ***********************************

    /**
     * 设置加一动画
     *
     * @param v             View
     * @param startLocation startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        ViewGroup anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_total_num.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 创建动画层
     *
     * @return ViewGroup
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(ViewGroup parent, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
    //******************************** 动画 结束 ***********************************

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
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
                leftAdapter.setData(cateBeanList, true);  //左侧ListView刷新显示
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

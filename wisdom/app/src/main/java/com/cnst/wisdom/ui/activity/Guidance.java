package com.cnst.wisdom.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.adapter.GuidanceAdapter;
import com.cnst.wisdom.ui.widget.ExpandTabView;
import com.cnst.wisdom.ui.widget.MenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * <课程指导页面><br/>
 * <向用户提供近期推送课程，并可按需求查找><br/>
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class Guidance extends AppCompatActivity implements View.OnClickListener {

    private ExpandTabView expandTabView;
    private ListView lvResult;

    private MenuView menuSubject;
    private MenuView menuTerm;
    private MenuView menuCourse;

    private List<View> tabViewList = new ArrayList<View>();
    private List<String> tabTitleList = new ArrayList<String>();

    private PopupWindow searchBox;
    private View searchView;
    private ImageButton ibtn_search1;

    GuidanceAdapter adapter;
    private String[] subjectList = new String[]{"逻辑数学","艺术创想","情节英语",
            "阶梯阅读","安全礼仪","魔力猴区角","奥尔夫音乐"};
    private String[] termList = new String[]{"第一期","第二期","第三期","第四期",
            "第五期","第六期","第七期","第八期"};
    private String[] courseList = new String[]{"1.颜色配对","2.按规律排序",
            "3.归类","4.相同和不同","5.比较长短","6.按长短排序","7.比较大小","8.对应"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        ImageButton ibtn_back = (ImageButton) findViewById(R.id.head_back_action);
        ibtn_search1 = (ImageButton) findViewById(R.id.head_search_action);
        TextView tv_head = (TextView) findViewById(R.id.head_text);
        tv_head.setText("课前指导");
        ibtn_back.setVisibility(View.VISIBLE);
        ibtn_search1.setVisibility(View.VISIBLE);
        ibtn_back.setOnClickListener(this);
        ibtn_search1.setOnClickListener(this);
        expandTabView  = (ExpandTabView) findViewById(R.id.expand_tab);
        lvResult = (ListView) findViewById(R.id.listView);
        menuSubject = new MenuView(this, subjectList, R.layout.choose_item1);
        menuTerm = new MenuView(this, termList, R.layout.choose_item1);
        menuCourse = new MenuView(this, courseList, R.layout.choose_item2);
        adapter = new GuidanceAdapter(subjectList,this);

        searchView = View.inflate(this,R.layout.popup_guidance,null);
        ImageButton ibtn_search2 = (ImageButton) searchView.findViewById(R.id.ibtn_search);
        ibtn_search2.setOnClickListener(this);
    }

    private void initData(){
        tabViewList.add(menuSubject);
        tabViewList.add(menuTerm);
        tabViewList.add(menuCourse);
        tabTitleList.add("学科");
        tabTitleList.add("期数");
        tabTitleList.add("课名");

        //添加菜单栏
        expandTabView.inflateTab(tabTitleList, tabViewList);
        lvResult.setAdapter(adapter);
    }

    /**
     * 监听三个菜单栏的点击，进行对应的刷新操作
     */
    private void initListener(){
        menuSubject.setOnSelectListener(new MenuView.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(menuSubject,showText);
            }
        });
        menuTerm.setOnSelectListener(new MenuView.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(menuTerm,showText);
            }
        });
        menuCourse.setOnSelectListener(new MenuView.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(menuCourse,showText);
            }
        });
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Guidance.this,GuidanceDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_back_action:
                finish();
                break;
            case R.id.head_search_action:
                if(searchBox==null){
                    searchBox = new PopupWindow(searchView,360, ibtn_search1.getHeight());
                    searchBox.setBackgroundDrawable(new BitmapDrawable());
                    searchBox.setFocusable(true);
                    searchBox.setOutsideTouchable(true);
                    searchBox.setAnimationStyle(R.style.SearchWindowAnimation);
                }
                if(searchBox.isShowing())
                    searchBox.dismiss();
                else{
                    int[] location = new int[2];
                    ibtn_search1.getLocationOnScreen(location);
                    searchBox.showAtLocation(ibtn_search1, Gravity.NO_GRAVITY, location[0] - 250, location[1] + ibtn_search1.getHeight() / 2 - searchBox.getHeight() / 2);
                }
                break;
            case R.id.ibtn_search:
                if(searchBox!=null&&searchBox.isShowing()){
                    searchBox.dismiss();
                    Toast.makeText(this,"search useless",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 刷新操作，ListView加载筛选课程
     * @param view
     * @param showText
     */
    private void onRefresh(View view, String showText) {
        expandTabView.onPressBack();
        int position = getPositon(view);
        //判断是否改变了选项
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
            Toast.makeText(Guidance.this, showText, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取当前被点击的菜单栏的postion
     * @param view
     * @return
     */
    private int getPositon(View view) {
        for (int i = 0; i < tabViewList.size(); i++) {
            if (tabViewList.get(i) == view) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 回退键先收回展开的菜单
     */
    @Override
    public void onBackPressed() {
        if (!expandTabView.onPressBack()) {
            finish();
        }
    }
}

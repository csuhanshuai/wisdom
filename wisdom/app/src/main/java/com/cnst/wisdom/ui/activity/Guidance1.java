package com.cnst.wisdom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.Course;
import com.cnst.wisdom.model.bean.CourseBean;
import com.cnst.wisdom.model.bean.Subject;
import com.cnst.wisdom.model.bean.SubjectBean;
import com.cnst.wisdom.model.bean.Term;
import com.cnst.wisdom.model.bean.TermBean;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.ui.adapter.GuidanceAdapter;
import com.cnst.wisdom.ui.adapter.GuidancePopAdapter;
import com.cnst.wisdom.utills.DisplayUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Guidance1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {


    private List<Subject> subjectList;
    private List<Term> termList;
    private List<Course> courseList;

    private GuidanceAdapter adapter;

    /**
     * 课程列表
     */
    private ListView lvResult;

    private String subjectItem;
    private String termItem;
    private String courseItem;

    //当前选中学科，学期，课名id
    private String curSubjectId;
    private String curTermId;
    private String curCourseId;

    private PopupWindow searchBox;
    private View searchView;
    private ImageButton ibtn_search1;

    private Button selectedButton;
    private int selectedPosition;
    private List<Button> btnList = new ArrayList<Button>();

    private ListView lvSubject;
    private ListView lvTerm;
    private ListView lvCourse;

    private List<View> contentViewList = new ArrayList<View>();

    private PopupWindow popupWindow;

    private int displayWidth;
    private int displayHeight;
    private int tabButtonWidth;

    private VolleyManager volleyManager = VolleyManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance1);

        initView();
        initData();
        initDataFromServer();
    }

    private void initView(){

        displayHeight = DisplayUtils.getHeightPx(this);
        displayWidth = DisplayUtils.getWidthPx(this);

        View view = findViewById(R.id.ll_subject);
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        tabButtonWidth =view.getMeasuredWidth();

        initTabButton();
        initTabMenu();

        lvResult = (ListView) findViewById(R.id.listView);

        ImageButton ibtn_back = (ImageButton) findViewById(R.id.head_back_action);
        ibtn_back.setVisibility(View.VISIBLE);
        ibtn_back.setOnClickListener(this);

        TextView tv_head = (TextView) findViewById(R.id.head_text);
        tv_head.setText("课前指导");

        ibtn_search1 = (ImageButton) findViewById(R.id.head_search_action);
        ibtn_search1.setVisibility(View.VISIBLE);
        ibtn_search1.setOnClickListener(this);

        searchView = View.inflate(this,R.layout.popup_guidance,null);
        ImageButton ibtn_search2 = (ImageButton) searchView.findViewById(R.id.ibtn_search);
        ibtn_search2.setOnClickListener(this);
    }

    private void initData(){
        adapter = new GuidanceAdapter(null,this);
        lvResult.setAdapter(adapter);
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Guidance1.this, GuidanceDetailActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initTabButton(){
        Button btnSubject = (Button) findViewById(R.id.btn_subject);
        Button btnTerm = (Button) findViewById(R.id.btn_term);
        Button btnCourse = (Button) findViewById(R.id.btn_course);
        btnSubject.setTag(0);
        btnTerm.setTag(1);
        btnCourse.setTag(2);
        btnList.add(btnSubject);
        btnList.add(btnTerm);
        btnList.add(btnCourse);

        btnSubject.setOnClickListener(this);
        btnTerm.setOnClickListener(this);
        btnCourse.setOnClickListener(this);
    }

    /**
     * 初始化下拉列表
     */
    private void initTabMenu(){

        lvSubject = (ListView) LayoutInflater.from(this).inflate(R.layout.guidance_menu_list,null);
        //GuidancePopAdapter subjectAdapter = new GuidancePopAdapter(this,subjectList,R.layout.choose_item1);
        //lvSubject.setAdapter(subjectAdapter);
        lvSubject.setOnItemClickListener(this);
        RelativeLayout rlSubject = new RelativeLayout(this);
        RelativeLayout.LayoutParams paramsSubject = new RelativeLayout.LayoutParams(tabButtonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlSubject.setGravity(Gravity.LEFT);
        rlSubject.addView(lvSubject, paramsSubject);
        rlSubject.setBackgroundColor(getResources().getColor(R.color.popup_main_background));

        lvTerm = (ListView) LayoutInflater.from(this).inflate(R.layout.guidance_menu_list,null);
        //GuidancePopAdapter termAdapter = new GuidancePopAdapter(this,termList,R.layout.choose_item1);
        //lvTerm.setAdapter(termAdapter);
        lvTerm.setOnItemClickListener(this);
        RelativeLayout rlTerm = new RelativeLayout(this);
        RelativeLayout.LayoutParams paramsTerm = new RelativeLayout.LayoutParams(tabButtonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlTerm.setGravity(Gravity.CENTER_HORIZONTAL);
        rlTerm.addView(lvTerm, paramsTerm);
        rlTerm.setBackgroundColor(getResources().getColor(R.color.popup_main_background));

        lvCourse = (ListView) LayoutInflater.from(this).inflate(R.layout.guidance_menu_list,null);
        //GuidancePopAdapter courseAdapter = new GuidancePopAdapter(this,courseList,R.layout.choose_item2);
        //lvCourse.setAdapter(courseAdapter);
        lvCourse.setOnItemClickListener(this);
        RelativeLayout rlCourse = new RelativeLayout(this);
        RelativeLayout.LayoutParams paramsCourse = new RelativeLayout.LayoutParams(tabButtonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlCourse.setGravity(Gravity.RIGHT);
        rlCourse.addView(lvCourse, paramsCourse);
        rlCourse.setBackgroundColor(getResources().getColor(R.color.popup_main_background));

        contentViewList.add(rlSubject);
        contentViewList.add(rlTerm);
        contentViewList.add(rlCourse);

        for(View view :contentViewList){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
    }

    private void initDataFromServer(){
        getSubjectList();
    }

    /**
     * 获取学科列表
     */
    private void getSubjectList(){
        HashMap subjectPara = new HashMap();
        subjectPara.put("type", "subject");
        volleyManager.getString(Constants.getTeachCategorys, subjectPara, "subject", new NetResult() {
            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(Guidance1.this,"获取数据失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSucceed(Object response) {
                Gson gson = new Gson();
                SubjectBean subjectBean = null;
                subjectBean = gson.fromJson((String) response, SubjectBean.class);
                if (subjectBean != null) {
                    switch (subjectBean.getCode()) {
                        case Constants.STATUS_ARGUMENT_ERROR:
                            break;
                        case Constants.STATUS_DATA_NOTFOUND:
                            break;
                        case Constants.STATUS_ILLEGAL:
                            break;
                        case Constants.STATUS_SERVER_EXCEPTION:
                            break;
                        case Constants.STATUS_SUCCESS:
                            subjectList = subjectBean.getData();
                            if (subjectList != null) {
                                for (Subject subject : subjectList)
                                    Log.d("TAG", subject.toString());
                                //getTermList("6sd67fdgr3ybfs7t34uhg3");
                                getTermList(subjectList.get(0).getId());
                            }
                            break;
                        case Constants.STATUS_TIMEOUT:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 根据学科id获取期数列表
     * @param subjectId
     */
    private void getTermList(String subjectId){
        HashMap termPara = new HashMap();
        termPara.put("type","term");
        termPara.put("fk",subjectId);
        volleyManager.getString(Constants.getTeachCategorys, termPara, "term", new NetResult() {
            @Override
            public void onFailure(VolleyError error) {

            }

            @Override
            public void onSucceed(Object response) {
                Gson gson = new Gson();
                TermBean termBean = null;
                termBean = gson.fromJson((String) response,TermBean.class);
                if(termBean!=null){
                    switch (termBean.getCode()){
                        case Constants.STATUS_ARGUMENT_ERROR:
                            break;
                        case Constants.STATUS_DATA_NOTFOUND:
                            break;
                        case Constants.STATUS_ILLEGAL:
                            break;
                        case Constants.STATUS_SERVER_EXCEPTION:
                            break;
                        case Constants.STATUS_SUCCESS:
                            termList = termBean.getData();
                            if (termList != null) {
                                for (Term term : termList)
                                    Log.d("TAG", term.toString());
                                //getCourseList("4we565wetrhgf8fd");
                                getCourseList(termList.get(0).getId());
                            }
                            break;
                        case Constants.STATUS_TIMEOUT:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 根据期数id获取课程列表
     * @param termId
     */
    private void getCourseList(String termId){
        HashMap courseMap = new HashMap();
        courseMap.put("type","classname");
        courseMap.put("fk",termId);
        volleyManager.getString(Constants.getTeachCategorys, courseMap, "course", new NetResult() {
            @Override
            public void onFailure(VolleyError error) {

            }

            @Override
            public void onSucceed(Object response) {
                Gson gson = new Gson();
                CourseBean courseBean = null;
                courseBean = gson.fromJson((String) response, CourseBean.class);
                if (courseBean != null) {
                    switch (courseBean.getCode()) {
                        case Constants.STATUS_ARGUMENT_ERROR:
                            break;
                        case Constants.STATUS_DATA_NOTFOUND:
                            break;
                        case Constants.STATUS_ILLEGAL:
                            break;
                        case Constants.STATUS_SERVER_EXCEPTION:
                            break;
                        case Constants.STATUS_SUCCESS:
                            courseList = courseBean.getData();
                            if (courseList != null) {
                                for (Course course : courseList)
                                    Log.d("TAG", course.toString());
                            }
                            GuidancePopAdapter subjectAdapter = new GuidancePopAdapter(Guidance1.this,(List) subjectList,R.layout.choose_item1);
                            lvSubject.setAdapter(subjectAdapter);
                            GuidancePopAdapter termAdapter = new GuidancePopAdapter(Guidance1.this,(List) termList,R.layout.choose_item1);
                            lvTerm.setAdapter(termAdapter);
                            GuidancePopAdapter courseAdapter = new GuidancePopAdapter(Guidance1.this,(List) courseList,R.layout.choose_item2);
                            lvCourse.setAdapter(courseAdapter);
                            curSubjectId = subjectList.get(0).getId();
                            curTermId = termList.get(0).getId();
                            curCourseId = courseList.get(0).getId();
                            break;
                        case Constants.STATUS_TIMEOUT:
                            break;
                    }
                }
            }
        });
    }

    //6sd67fdgr3ybfs7t34uhg3
    //4we565wetrhgf8fd
    //778we8uyfwhj7823b


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_subject:
                showOrCloseWindow(v);
                break;
            case R.id.btn_term:
                showOrCloseWindow(v);
                break;
            case R.id.btn_course:
                showOrCloseWindow(v);
                break;
            case R.id.head_back_action:
                finish();
                break;
            case R.id.head_search_action:
//                if(searchBox==null){
//                    searchBox = new PopupWindow(searchView,DisplayUtils.dip2px(120,this), ibtn_search1.getHeight());
//                    searchBox.setBackgroundDrawable(new BitmapDrawable());
//                    searchBox.setFocusable(true);
//                    searchBox.setOutsideTouchable(true);
//                    searchBox.setAnimationStyle(R.style.SearchWindowAnimation);
//                }
//                if(searchBox.isShowing())
//                    searchBox.dismiss();
//                else{
//                    int[] location = new int[2];
//                    ibtn_search1.getLocationOnScreen(location);
//                    // ibtn_search1.getHeight() / 2 - searchBox.getHeight() / 2
//                    searchBox.showAtLocation(ibtn_search1, Gravity.NO_GRAVITY, location[0] +ibtn_search1.getWidth() - DisplayUtils.dip2px(120,this), location[1] );
//                }
                Intent intent = new Intent(this,GuidanceSearch.class);
                startActivity(intent);
                break;
            case R.id.ibtn_search:
                if(searchBox!=null&&searchBox.isShowing()){
                    searchBox.dismiss();
                    Toast.makeText(this, "search useless", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showOrCloseWindow(View v){
        Button btn = (Button) v;
        if(selectedButton!=null&&selectedButton!=btn){
            selectedButton.setSelected(false);
            selectedButton = btn;
            selectedButton.setSelected(true);
            selectedPosition = (int) selectedButton.getTag();
        }else if(selectedButton!=null&&selectedButton==btn){
            if(selectedButton.isSelected())
                selectedButton.setSelected(false);
            else
                selectedButton.setSelected(true);
            selectedPosition = (int) selectedButton.getTag();
        }else{
            selectedButton = btn;
            selectedPosition = (int) selectedButton.getTag();
            selectedButton.setSelected(true);
        }
        startAnim();
    }

    /**
     * 是否在关闭菜单栏后再次打开新的菜单栏
     */
    private boolean flag = false;

    private void startAnim(){
        if(popupWindow==null){
            popupWindow = new PopupWindow(contentViewList.get(selectedPosition),displayWidth,displayHeight);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(false);
            popupWindow.setOnDismissListener(this);
            //popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        }
        if(selectedButton.isSelected()){
            if(!popupWindow.isShowing()){
                showPopup(selectedPosition);
            }else{
                //如果有其他菜单栏展开，应先将其关闭，再将新选中的Button展开
                //popupWindow.setOnDismissListener(this);
                flag = true;
                popupWindow.dismiss();
            }
        }else{
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        }
    }

    private void showPopup(int selectedPosition){
        if (popupWindow.getContentView() != contentViewList.get(selectedPosition)) {
            popupWindow.setContentView(contentViewList.get(selectedPosition));
        }
        popupWindow.showAsDropDown(btnList.get(selectedPosition), btnList.get(selectedPosition).getLeft(), 0);
    }

    @Override
    public void onDismiss() {
        if(flag){
            showPopup(selectedPosition);
            flag = false;
        }else{
            selectedButton.setSelected(false);
        }
        //popupWindow.setOnDismissListener(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if(popSubject!=null&&popSubject.isShowing())
//        {
//            subjectItem = subjectList[position];
//            popSubject.dismiss();
//            Toast.makeText(this,subjectItem,Toast.LENGTH_SHORT).show();
//        }else if(popTerm!=null&&popTerm.isShowing())
//        {
//            termItem = termList[position];
//            popTerm.dismiss();
//            Toast.makeText(this,termItem,Toast.LENGTH_SHORT).show();
//        }else  if(popCourse!=null&&popCourse.isShowing())
//        {
//            courseItem = courseList[position];
//            popCourse.dismiss();
//            Toast.makeText(this,courseItem,Toast.LENGTH_SHORT).show();
//        }

        GuidancePopAdapter adapter = (GuidancePopAdapter) parent.getAdapter();
        //Toast.makeText(this,adapter.getItem(position)+"",Toast.LENGTH_SHORT).show();
        if(parent==lvSubject)
            Toast.makeText(this,"subject Item",Toast.LENGTH_SHORT).show();
        else if(parent==lvTerm)
            Toast.makeText(this,"term Item",Toast.LENGTH_SHORT).show();
        else if(parent==lvCourse)
            Toast.makeText(this,"course Item",Toast.LENGTH_SHORT).show();
        adapter.setSlectedPos(position);
        adapter.notifyDataSetChanged();
        popupWindow.dismiss();
        refreshData();
        adapter.notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    private void refreshData(){

    }

    @Override
    public void onBackPressed() {
        if(popupWindow!=null&&popupWindow.isShowing())
            popupWindow.dismiss();
        else
            super.onBackPressed();
    }

}

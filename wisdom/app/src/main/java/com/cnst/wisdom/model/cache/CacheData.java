package com.cnst.wisdom.model.cache;

import android.content.Context;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.Clazz;
import com.cnst.wisdom.model.bean.Course;
import com.cnst.wisdom.model.bean.Subject;
import com.cnst.wisdom.model.bean.TeachPlanBean;
import com.cnst.wisdom.model.bean.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据缓存类
 * 多个地方需要用到的数据存储的地方
 *
 * @author taoyuan.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class CacheData
{
    public static List<TeachPlanBean> sCourseList = new ArrayList<>();//缓存的课程对象
    public static List<Clazz> sClassList = new ArrayList<>();//缓存的班级种类
    public static List<Term> sPeriodsList = new ArrayList<>();//缓存的期数种类
    public static List<Subject> sSubjectList = new ArrayList<>();//缓存的学科种类

    public static void initSubject(Context context)
    {
        List<String> courseNameList = Arrays.asList(context.getResources().
                getStringArray(R.array.course_names));
        String[] clazzArray = context.getResources().getStringArray(R.array.course_clazz);
        sClassList.clear();
        for(int i = 0; i<clazzArray.length; i++)
        {
            Clazz clazz = new Clazz();
            clazz.setName(clazzArray[i]);
            clazz.setId(clazzArray[i].hashCode()+"");
            sClassList.add(clazz);
        }
        sPeriodsList.clear();
        String[] termArray = context.getResources().getStringArray(R.array.course_periods);
        for(int i = 0; i<termArray.length; i++)
        {
            Term term = new Term();
            term.setName(termArray[i]);
            term.setId(termArray[i].hashCode()+"");
            sPeriodsList.add(term);
        }
        String[] courseArray = context.getResources().getStringArray(R.array.course_subject);
        sSubjectList.clear();
        for(int i = 0; i<courseArray.length; i++)
        {
            Subject subject = new Subject();
            subject.setName(courseArray[i]);
            subject.setId(courseArray[i].hashCode()+"");
            sSubjectList.add(subject);
        }
        sCourseList = new ArrayList<>();
        //是否完成状态：0-未完成；1-已完成
        for(int i = 0; i<5; i++)
        {
            if(i%sClassList.size() != 0 && i%sSubjectList.size() != 0 && i%sPeriodsList.size() != 0)
            {
                Course course = new Course();
                course.setName(courseNameList.get(i%courseNameList.size()));
                sCourseList.add(new TeachPlanBean(sClassList.get(i), sSubjectList.get(i), sPeriodsList.get(i), course,
                        "2016-1-"+i+1,
                        "2016-2-"+i+2, 0));
            }
        }
        for(int i = 0; i<5; i++)
        {
            if(i%sClassList.size() != 0 && i%sSubjectList.size() != 0 && i%sPeriodsList.size() != 0)
            {
                Course course = new Course();
                course.setName(courseNameList.get(i%courseNameList.size()));
                sCourseList.add(new TeachPlanBean(sClassList.get(i), sSubjectList.get(i), sPeriodsList.get(i),
                        course, "2016-1-"+1+i, "2016-2-"+2+i, 1));
            }
        }
    }
}

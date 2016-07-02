package com.cnst.wisdom.model.bean;

import java.io.Serializable;

/**
 * 教学计划
 * <功能详细描述>
 *
 * @author pengjingnag.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class TeachPlanBean implements Serializable, Comparable<TeachPlanBean>
{
    private String id;
    private Clazz mClazz;
    private Subject mSubject;
    private Term mTerm;
    private Course mCourse;
    private String beginTime;
    private String endTime;
    private int state; //是否完成状态：0-未完成；1-已完成

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Clazz getClazz()
    {
        return mClazz;
    }

    public void setClazz(Clazz clazz)
    {
        mClazz = clazz;
    }

    public Subject getSubject()
    {
        return mSubject;
    }

    public void setSubject(Subject subject)
    {
        mSubject = subject;
    }

    public Term getTerm()
    {
        return mTerm;
    }

    public void setTerm(Term term)
    {
        mTerm = term;
    }

    public Course getCourse()
    {
        return mCourse;
    }

    public void setCourse(Course course)
    {
        mCourse = course;
    }

    public String getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        return "TeachPlan{"+
                "id='"+id+'\''+
                ", mClazz="+mClazz+
                ", mSubject="+mSubject+
                ", mTerm="+mTerm+
                ", mCourse="+mCourse+
                ", beginTime='"+beginTime+'\''+
                ", endTime='"+endTime+'\''+
                '}';
    }

    public TeachPlanBean()
    {
    }

    public TeachPlanBean(Clazz clazz, Subject subject, Term term, Course course, String beginTime, String endTime, int state)
    {
        mClazz = clazz;
        mSubject = subject;
        mTerm = term;
        mCourse = course;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.state = state;
    }

    @Override
    public int compareTo(TeachPlanBean another)
    {
        return this.state - another.state;
    }
}
